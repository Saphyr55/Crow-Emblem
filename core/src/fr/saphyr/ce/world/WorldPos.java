package fr.saphyr.ce.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class WorldPos {

    private Vector3 pos;
    private IWorld world;

    public WorldPos(IWorld world, Vector2 pos) {
        this.pos = new Vector3(pos, 0);
        this.world = world;
    }

    public WorldPos(IWorld world, Vector3 pos) {
        this.pos = new Vector3(pos);
        this.world = world;
    }

    public static WorldPos of(IWorld world, Vector3 pos) {
        return new WorldPos(world, pos);
    }

    public static WorldPos of(IWorld world, Vector2 pos) {
        return new WorldPos(world, pos);
    }

    public static WorldPos of(WorldPos worldPos) {
        return new WorldPos(worldPos.world, worldPos.getPos());
    }

    public Vector3 getPos() {
        return pos;
    }

    public void setPos(Vector3 pos) {
        this.pos = pos;
    }

    public IWorld getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setPos(float x, float y) {
        setPos(new Vector3(x, y, 0));
    }

}
