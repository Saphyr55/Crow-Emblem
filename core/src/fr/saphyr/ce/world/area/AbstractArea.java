package fr.saphyr.ce.world.area;

import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.area.cell.ICell;

import java.util.Optional;

public abstract class AbstractArea<C extends ICell> implements IArea<C> {

    protected final Array<Array<Optional<C>>> handle = new Array<>();
    protected IWorld world;

    protected AbstractArea(IWorld world) {
        this.world = world;
    }

    @Override
    public Array<Array<Optional<C>>> getHandle() {
        return handle;
    }

    @Override
    public IWorld getWorld() {
        return world;
    }


}
