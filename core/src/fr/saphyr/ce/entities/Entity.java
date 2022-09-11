package fr.saphyr.ce.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
import fr.saphyr.ce.worlds.WorldPos;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Entity implements CEObject, Selectable {

    public static Entity entitySelected = null;
    public static boolean hasEntitySelected = false;

    protected Texture texture;
    protected MoveArea moveArea;
    protected Array<TiledMapTile> tilesNotExplorable;
    protected final WorldPos worldPos;
    protected float stateTime;
    protected boolean isSelected;
    protected int index = 0;
    protected Area areaClicked;
    protected boolean isMoved = false;
    protected Area futureArea = null;
    protected GraphPath<Area> graphPathArea;
    protected Direction direction;
    private final float epsilon = 0.09f;

    public Entity(WorldPos worldPos, int[] tileNotExplorable) {
        this.worldPos = worldPos;
        this.stateTime = 0f;
        this.tilesNotExplorable = new Array<>();
        setTilesNotExplorableById(tileNotExplorable);
        this.isSelected = false;
    }

    protected TextureRegion[][] splitTexture(int col, int row) {
        return TextureRegion.split(texture, texture.getWidth() / col, texture.getHeight() / row);
    }

    public void setTilesNotExplorableById(int[] tilesSolidId) {
        Arrays.stream(tilesSolidId).forEach(this::addTileById);
    }

    private void addTileById(int id) {
        TiledMapTile tile = worldPos.getWorld().getMap().getHandle().getTileSets().getTile(id);
        if (tile != null) tilesNotExplorable.add(tile);
    }

    @Override
    public Area getAreaSelect() {
        AtomicReference<Area> posClicked = new AtomicReference<>(null);
        if (moveArea.isOpen() && !isMoved) {
            moveArea.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
                if (isClickOnFrame(Input.Buttons.LEFT, moveArea.getEntity().getWorldPos().getWorld(), area.getPos().x, area.getPos().y)){
                    posClicked.set(area);
                }
            })));
        }
        return posClicked.get();
    }

    public void setMoveArea(int[][] moveAreaId) {
        moveArea = MoveAreas.parse(moveAreaId, this);
    }

    private boolean futureAreaAlmostEqualWith(Vector3 pos) {
        return  CEMath.almostEqual(futureArea.getPos().x, pos.x, epsilon) &&
                CEMath.almostEqual(futureArea.getPos().y, pos.y, epsilon);
    }

    private boolean areaClickedAlmostEqualWith(Vector3 pos) {
        return  CEMath.almostEqual(areaClicked.getPos().x, pos.x, epsilon) &&
                CEMath.almostEqual(areaClicked.getPos().y, pos.y, epsilon);
    }

    private void stop() {
        if (isMoved) {
            worldPos.getPos().set(areaClicked.getPos());
            areaClicked = null;
            futureArea = null;
            isMoved = false;
            setMoveArea(MoveAreas.DEFAULT_MOVE_ZONE_9);
        }
    }

    private void translate(float velocityX, float velocityY) {
         worldPos.getPos().add(velocityX, velocityY, 0);
    }

    private void moveUp(float velocity) {
        if (futureArea.getPos().y > worldPos.getPos().y) {
            direction = Direction.UP;
            translate(0, velocity);
        }
    }

    private void moveLeft(float velocity) {
        if (futureArea.getPos().x < worldPos.getPos().x) {
            direction = Direction.LEFT;
            translate(-velocity, 0);
        }
    }

    private void moveBottom(float velocity) {
        if (futureArea.getPos().y < worldPos.getPos().y) {
            direction = Direction.BOTTOM;
            translate(0, -velocity);
        }
    }

    private void moveRight(float velocity) {
        if (futureArea.getPos().x > worldPos.getPos().x) {
            direction = Direction.RIGHT;
            translate(velocity, 0);
        }
    }

    public void move(float velocity) {
        if (!isMoved) {
            isMoved = true;
            index = 0;
            graphPathArea = getMoveArea().getAreaGraph().findPath(getMoveArea().getAreaWithEntity(), areaClicked);
            if (graphPathArea.getCount() > 1) futureArea = graphPathArea.get(++index);
        }
        if (futureArea != null) {
            moveUp(velocity);
            moveBottom(velocity);
            moveLeft(velocity);
            moveRight(velocity);
            if (areaClickedAlmostEqualWith(worldPos.getPos())) stop();
            else if (futureAreaAlmostEqualWith(worldPos.getPos())) {
                worldPos.getPos().set(futureArea.getPos());
                ++index;
                if (index < graphPathArea.getCount())
                    futureArea = graphPathArea.get(index);
            }
        }
        else stop();
    }

    @Override
    public void render(Renderer renderer) {
        moveArea.setOpen(isSelected);
        if (moveArea.isOpen()){
            moveArea.draw(renderer);
        }
    };

    @Override
    public void update(final float dt) {
        stateTime += dt;
        selectOnClick(Input.Buttons.LEFT, worldPos, hasEntitySelected, () -> {
            entitySelected.isSelected = false;
            hasEntitySelected = false;
            entitySelected = this;
        }, () -> {
            hasEntitySelected = true;
            entitySelected = this;
            entitySelected.isSelected = true;
        });
    }

    public Texture getTexture() {
        return texture;
    }

    public WorldPos getWorldPos() {
        return worldPos;
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
