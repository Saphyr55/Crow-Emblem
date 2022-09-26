package fr.saphyr.ce.world.area;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.area.cell.ICell;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface IArea<T extends ICell> {

    Array<Array<Optional<T>>> getHandle();

    IWorld getWorld();

    default Optional<T> getCellAt(final int x, final int y) {
        final var optional = new AtomicReference<Optional<T>>();
        optional.set(Optional.empty());
        for (int i = 0; i < getHandle().size; i++) {
            for (int j = 0; j < getHandle().get(i).size; j++) {
                final Optional<T> finalOptional = getHandle().get(i).get(j);
                finalOptional.ifPresent(area -> {
                    if(area.getPos().x == x && area.getPos().y == y){
                        optional.set(finalOptional);
                    }
                });
            }
        }
        return optional.get();
    }

    default Optional<T> getCellAt(final Vector3 pos) {
        return getCellAt((int) pos.x, (int) pos.y);
    }
}
