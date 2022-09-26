package fr.saphyr.ce.world.area;

import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.cell.ICell;

import java.util.Optional;

public abstract class AbstractArea<T extends ICell> implements IArea<T> {

    protected final Array<Array<Optional<T>>> zone;
    protected final IWorld world;

    protected AbstractArea(IWorld world) {
        this.world = world;
        zone = new Array<>();
    }
    
    @Override
    public Array<Array<Optional<T>>> getHandle() {
        return zone;
    }

    @Override
    public IWorld getWorld() {
        return world;
    }

    
}
