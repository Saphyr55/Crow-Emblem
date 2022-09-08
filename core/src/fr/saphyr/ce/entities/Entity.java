package fr.saphyr.ce.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.CEObject;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.graphics.MoveArea;
import fr.saphyr.ce.worlds.World;

import java.util.Arrays;
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

    protected boolean isClickOnCurrentFrame(int key) {
        return Gdx.input.isButtonJustPressed(key) &&
                (int) world.getMousePos().x == getPos().x &&
                (int) world.getMousePos().y == getPos().y;
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
