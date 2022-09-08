package fr.saphyr.ce.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.CEObject;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.graphics.MoveArea;
import fr.saphyr.ce.graphics.MoveAreas;
import fr.saphyr.ce.worlds.World;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public abstract class Entity implements CEObject {

    protected Texture texture;
    protected MoveArea moveArea;
    protected Array<TiledMapTile> tilesNotExplorable;
    protected Vector3 pos;
    protected World world;

    public Entity(Texture texture) {
        this.texture = texture;
        this.tilesNotExplorable = new Array<>();
    }

    public void setTilesNotExplorableById(int[] tilesSolidId) {
        Arrays.stream(tilesSolidId).forEach(this::addTileById);
    }

    private void addTileById(int id) {
        var tile = world.getMap().getHandle().getTileSets().getTile(id);
        if (tile != null) tilesNotExplorable.add(tile);
    }

    protected boolean isClickOnFrame(int key, float posX, float posY) {
        return Gdx.input.isButtonJustPressed(key) &&
                (int) world.getMousePos().x == posX &&
                (int) world.getMousePos().y == posY;
    }

    protected boolean isClickOnCurrentFrame(int key) {
        return isClickOnFrame(key, getPos().x, getPos().y);
    }

    protected Vector2 getPosClickFromMoveArea() {
        AtomicReference<Vector2> posClicked = new AtomicReference<>(null);
        moveArea.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
            if (isClickOnFrame(Input.Buttons.LEFT, area.getPos().x, area.getPos().y) && area.isExplorable())
                posClicked.set(area.getPos());
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

    public abstract void render(Renderer renderer);

    public abstract void update(final float dt);

    public Texture getTexture() {
        return texture;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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

    public Vector3 getPos() {
        return pos;
    }

    public void setPos(Vector3 pos) {
        this.pos = pos;
    }




}
