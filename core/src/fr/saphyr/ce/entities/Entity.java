package fr.saphyr.ce.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.area.Area;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.area.MoveArea;
import fr.saphyr.ce.area.MoveAreas;
import fr.saphyr.ce.utils.CEMath;
import fr.saphyr.ce.worlds.World;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Entity implements CEObject, Selectable {

    protected Texture texture;
    protected MoveArea moveArea;
    protected Array<TiledMapTile> tilesNotExplorable;
    protected Vector2 pos;
    protected final World world;
    protected float stateTime;
    protected boolean isSelected;
    protected int index = 0;
    protected Area areaClicked;
    protected boolean isMoved = false;
    protected Area futureArea = null;
    protected GraphPath<Area> graphPathArea;
    protected Direction direction;
    private final float epsilon = 0.09f;

    public Entity(World world, Vector2 pos, int[]tileNotExplorable) {
        this.world = world;
        this.pos = pos;
        this.stateTime = 0f;
        this.tilesNotExplorable = new Array<>();
        this.isSelected = false;
        setTilesNotExplorableById(tileNotExplorable);
    }

    protected TextureRegion[][] splitTexture(int col, int row) {
        return TextureRegion.split(texture, texture.getWidth() / col, texture.getHeight() / row);
    }

    public void setTilesNotExplorableById(int[] tilesSolidId) {
        Arrays.stream(tilesSolidId).forEach(this::addTileById);
    }

    private void addTileById(int id) {
        TiledMapTile tile = world.getMap().getHandle().getTileSets().getTile(id);
        if (tile != null) tilesNotExplorable.add(tile);
    }

    @Override
    public Area getAreaSelect() {
        AtomicReference<Area> posClicked = new AtomicReference<>(null);
        if (moveArea.isOpen() && !isMoved) {
            moveArea.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
                if (isClickOnFrame(Input.Buttons.LEFT, moveArea.getEntity().getWorld(), area.getPos()))
                    posClicked.set(area);
            })));
        }
        return posClicked.get();
    }

    public void setMoveArea(int[][] moveAreaId) {
        moveArea = MoveAreas.parse(moveAreaId, this);
    }

    private boolean almostEqualFutureAreaWith(Vector2 pos) {
        return  CEMath.almostEqual(futureArea.getPos().x, pos.x, epsilon) &&
                CEMath.almostEqual(futureArea.getPos().y, pos.y, epsilon);
    }

    private boolean almostEqualAreaClickedWith(Vector2 pos) {
        return  CEMath.almostEqual(areaClicked.getPos().x, pos.x, epsilon) &&
                CEMath.almostEqual(areaClicked.getPos().y, pos.y, epsilon);
    }

    private void stop() {
        if (isMoved) {
            getPos().set(areaClicked.getPos());
            areaClicked = null;
            futureArea = null;
            isMoved = false;
            setMoveArea(MoveAreas.DEFAULT_MOVE_ZONE_9);
        }
    }

    private void translate(float velocityX, float velocityY) {
        getPos().add(velocityX, velocityY);
    }

    private void moveUp(float velocity) {
        if (futureArea.getPos().y > getPos().y) {
            direction = Direction.UP;
            translate(0, velocity);
        }
    }

    private void moveLeft(float velocity) {
        if (futureArea.getPos().x < getPos().x) {
            direction = Direction.LEFT;
            translate(-velocity, 0);
        }
    }

    private void moveBottom(float velocity) {
        if (futureArea.getPos().y < getPos().y) {
            direction = Direction.BOTTOM;
            translate(0, -velocity);
        }
    }

    private void moveRight(float velocity) {
        if (futureArea.getPos().x > getPos().x) {
            direction = Direction.RIGHT;
            translate(velocity, 0);
        }
    }

    public void move(float velocity) {
        if (!isMoved) {
            isMoved = true;
            index = 0;
            graphPathArea = getMoveArea().getAreaGraph()
                    .findPath(getMoveArea().getAreaWithEntity(), areaClicked);
            if (graphPathArea.getCount() > 1) futureArea = graphPathArea.get(++index);
        }
        if (futureArea != null) {
            moveUp(velocity);
            moveBottom(velocity);
            moveLeft(velocity);
            moveRight(velocity);
            if (almostEqualAreaClickedWith(pos)) stop();
            else if (almostEqualFutureAreaWith(pos)) {
                pos.set(futureArea.getPos());
                ++index;
                if (index < graphPathArea.getCount())
                    futureArea = graphPathArea.get(index);
            }
        }
        else stop();
    }

    @Override
    public void render(Renderer renderer) {
        if (isSelected) {
            moveArea.draw(renderer);
            moveArea.setOpen(true);
        }
        else
            moveArea.setOpen(false);
    };

    @Override
    public void update(final float dt) {
        stateTime += dt;
        selectOnClick(Input.Buttons.LEFT, world, pos, isSelected,
                () -> isSelected = false,
                () -> isSelected = true
        );
    }

    public Texture getTexture() {
        return texture;
    }

    public World getWorld() {
        return world;
    }

    public MoveArea getMoveZone() {
        return moveArea;
    }

    public void setMoveZone(MoveArea moveArea) {
        this.moveArea = moveArea;
    }

    public Array<TiledMapTile> getTilesNotExplorable() {
        return tilesNotExplorable;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public MoveArea getMoveArea() {
        return moveArea;
    }

    public void setMoveArea(MoveArea moveArea) {
        this.moveArea = moveArea;
    }

    public static Entity getEntitySelected() {
        return entitySelected;
    }

    public static boolean isHasEntitySelected() {
        return hasEntitySelected;
    }


}
