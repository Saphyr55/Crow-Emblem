package fr.saphyr.ce.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.area.Area;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.area.MoveArea;
import fr.saphyr.ce.area.MoveAreas;
import fr.saphyr.ce.worlds.World;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public abstract class Entity implements CEObject, Movable {

    protected Texture texture;
    protected MoveArea moveArea;
    protected Array<TiledMapTile> tilesNotExplorable;
    protected Vector2 pos;
    protected final World world;
    protected float stateTime;

    protected static Entity entitySelected;
    protected static boolean hasEntitySelected = false;
    protected boolean isSelected;
    protected Area areaClicked;
    protected boolean isMoved;

    public Entity(World world, Vector2 pos, int[]tileNotExplorable) {
        this.world = world;
        this.pos = pos;
        this.stateTime = 0f;
        this.tilesNotExplorable = new Array<>();
        this.isSelected = false;
        this.isMoved = false;
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

    protected boolean isClickOnFrame(int key, float posX, float posY) {
        return Gdx.input.isButtonJustPressed(key) &&
                (int) world.getMousePos().x == (int) posX &&
                (int) world.getMousePos().y == (int) posY;
    }

    protected boolean isClickOnCurrentFrame(int key) {
        return isClickOnFrame(key, getPos().x, getPos().y);
    }

    protected Area getAreaClickFromMoveArea() {
        AtomicReference<Area> posClicked = new AtomicReference<>(null);
        moveArea.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
            if (isClickOnFrame(Input.Buttons.LEFT, area.getPos().x, area.getPos().y)) posClicked.set(area);
        })));
        return posClicked.get();
    }

    protected void selectOnClick(int key, boolean accept, Runnable ifRunnable, Runnable elseRunnable) {
        Consumer<Boolean> consumer = aBoolean -> {
            if (isClickOnCurrentFrame(key)) {
                if (aBoolean) ifRunnable.run();
                else elseRunnable.run();
            }
        };
        consumer.accept(accept);
    }

    protected void setMoveArea(int[][] moveAreaId) {
        moveArea = MoveAreas.parse(moveAreaId, this);
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
        selectOnClick(Input.Buttons.LEFT, isSelected, () -> {
            isSelected = false;
            },
                () -> isSelected = true);
    }

    protected void translate(float velocityX, float velocityY) {
        pos.add(velocityX, velocityY);
    }

    @Override
    public void move(float velocity) {
        if (areaClicked.getPos().x < pos.x) {
            translate(-velocity, 0);
            whenMoveLeft();
        }
        if (areaClicked.getPos().x > pos.x){
            translate(velocity, 0);
            whenMoveRight();
        }
        if (areaClicked.getPos().y < pos.y) {
            translate(0, -velocity);
            whenMoveBottom();
        }
        if (areaClicked.getPos().y > pos.y){
            translate(0, velocity);
            whenMoveUp();
        }
        stopMove();
    }

    private void stopMove() {
        if (Math.round(areaClicked.getPos().x) == Math.round(pos.x) &&
                Math.round(areaClicked.getPos().y) == Math.round(pos.y)) {
            pos.set(new Vector2(areaClicked.getPos()));
            setMoveArea(MoveAreas.DEFAULT_MOVE_ZONE_9);
            isMoved = false;
            areaClicked = null;
        }
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

    public static void setEntitySelected(Entity entitySelected) {
        Entity.entitySelected = entitySelected;
    }

    public static boolean isHasEntitySelected() {
        return hasEntitySelected;
    }

    public static void setHasEntitySelected(boolean hasEntitySelected) {
        Entity.hasEntitySelected = hasEntitySelected;
    }

}
