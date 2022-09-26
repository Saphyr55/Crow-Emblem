package fr.saphyr.ce.world.cell;

import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.world.IWorld;

public class WorldCell {

    private final Array<Array<ICell>> handle;
    private final IWorld world;

    public WorldCell(IWorld world) {
        this.world = world;
        this.handle = new Array<>();
    }

    public Array<Array<ICell>> getHandle() {
        return handle;
    }
}
