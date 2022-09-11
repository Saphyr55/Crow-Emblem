package fr.saphyr.ce.worlds;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class WorldPos {

    private Vector3 pos;
    private World world;

    public WorldPos(World world, Vector2 pos) {
        this.pos = new Vector3(pos, 0);
        this.world = world;
    }
    
    public Vector3 getPos() {
        return pos;
    }

    public void setPos(Vector3 pos) {
        this.pos = pos;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setPos(float x, float y) {
        setPos(new Vector3(x, y, 0));
    }

}
