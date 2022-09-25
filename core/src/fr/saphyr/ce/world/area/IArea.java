package fr.saphyr.ce.world.area;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.graphic.Textures;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface IArea {

    Vector3 getPos();

    void setPos(Vector3 pos);

    void setPos(float x, float y);

    Texture getTexture();

    void setTexture(Texture texture);

    boolean isExplorable();

    void setExplorable(boolean explorable);

    boolean isAccessible();

    void setAccessible(boolean accessible);

    int getIndex();

    void setIndex(int index);

    MoveArea getMoveArea();

    Vector3 getRelativePos();

    void setRelativePos(Vector3 relativePos);

    Optional<IEntity> getContentEntity();

    default Optional<IArea> getAreaOnDirection(Direction direction) {
        return switch (direction) {
            case UP -> getAreaFromVector(new Vector3(getPos().x, getPos().y + 1, 0));
            case RIGHT -> getAreaFromVector(new Vector3(getPos().x + 1, getPos().y, 0));
            case LEFT -> getAreaFromVector(new Vector3(getPos().x - 1, getPos().y, 0));
            case BOTTOM -> getAreaFromVector(new Vector3(getPos().x, getPos().y - 1, 0));
            case TOP_LEFT -> getAreaFromVector(new Vector3(getPos().x - 1, getPos().y + 1, 0));
            case TOP_RIGHT -> getAreaFromVector(new Vector3(getPos().x + 1, getPos().y + 1, 0));
            case BOTTOM_LEFT -> getAreaFromVector(new Vector3(getPos().x - 1, getPos().y - 1, 0));
            case BOTTOM_RIGHT -> getAreaFromVector(new Vector3(getPos().x + 1, getPos().y - 1, 0));
        };
    }

    private Optional<IArea> getAreaFromVector(Vector3 vector) {
        final var optional = new AtomicReference<Optional<IArea>>(Optional.empty());
        for (int i = 0; i < getMoveArea().getHandle().size; i++) {
            for (int j = 0; j < getMoveArea().getHandle().get(i).size; j++) {
                getMoveArea().getHandle().get(i).get(j).ifPresent(area -> {
                    if (vector.equals(area.getPos()))
                        optional.set(Optional.of(area));
                });
            }
        }
        return optional.get();
    }
    
    default Array<Optional<IArea>> getAroundArea() {
        final Array<Optional<IArea>> areas = new Array<>();
        areas.add(
                getAreaOnDirection(Direction.UP),
                getAreaOnDirection(Direction.BOTTOM),
                getAreaOnDirection(Direction.LEFT),
                getAreaOnDirection(Direction.RIGHT)
        );
        return areas;
    }


}
