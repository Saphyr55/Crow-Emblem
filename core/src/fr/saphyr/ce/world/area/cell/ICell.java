package fr.saphyr.ce.world.area.cell;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.utils.CEMath;
import fr.saphyr.ce.world.area.IArea;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface ICell {

    Vector3 getPos();

    void setPos(Vector3 pos);

    void setPos(float x, float y);

    Vector3 getRelativePos();

    void setRelativePos(Vector3 pos);

    void setRelativePos(float x, float y);

    int getIndex();

    void setIndex(int index);

    IArea<? extends ICell> getArea();

    Optional<IEntity> getContentEntity();

    default Optional<ICell> getCellOnDirection(Direction direction) {
        if (direction == null) return Optional.empty();
        return switch (direction) {
            case TOP -> getCellFromVector(new Vector3(getPos().x, getPos().y + 1, 0));
            case RIGHT -> getCellFromVector(new Vector3(getPos().x + 1, getPos().y, 0));
            case LEFT -> getCellFromVector(new Vector3(getPos().x - 1, getPos().y, 0));
            case BOTTOM -> getCellFromVector(new Vector3(getPos().x, getPos().y - 1, 0));
            case TOP_LEFT -> getCellFromVector(new Vector3(getPos().x - 1, getPos().y + 1, 0));
            case TOP_RIGHT -> getCellFromVector(new Vector3(getPos().x + 1, getPos().y + 1, 0));
            case BOTTOM_LEFT -> getCellFromVector(new Vector3(getPos().x - 1, getPos().y - 1, 0));
            case BOTTOM_RIGHT -> getCellFromVector(new Vector3(getPos().x + 1, getPos().y - 1, 0));
        };
    }

    private Optional<ICell> getCellFromVector(Vector3 vector) {
        final var optional = new AtomicReference<Optional<ICell>>(Optional.empty());
        for (int i = 0; i < getArea().getHandle().size; i++) {
            for (int j = 0; j < getArea().getHandle().get(i).size; j++) {
                getArea().getHandle().get(i).get(j).ifPresent(area -> {
                    if (vector.equals(area.getPos()))
                        optional.set(Optional.of(area));
                });
            }
        }
        return optional.get();
    }

    default Array<Optional<ICell>> getAroundCell() {
        final Array<Optional<ICell>> areas = new Array<>();
        areas.add(
                getCellOnDirection(Direction.TOP),
                getCellOnDirection(Direction.BOTTOM),
                getCellOnDirection(Direction.LEFT),
                getCellOnDirection(Direction.RIGHT)
        );
        return areas;
    }

    default boolean almostEqualArea(Vector3 pos, float epsilon) {
        return CEMath.almostEqual(getPos().x, pos.x, epsilon) && CEMath.almostEqual(getPos().y, pos.y, epsilon);
    }
    
}
