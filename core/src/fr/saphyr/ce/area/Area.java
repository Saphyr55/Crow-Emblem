package fr.saphyr.ce.area;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.graphics.Textures;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Area {

    private int index;
    private final MoveArea moveArea;
    private Vector2 pos;
    private Texture texture;
    private boolean isExplorable;
    private boolean isAccessible;
    public static final Texture explorableAreaTexture = Textures.get("textures/blue_move_zone.png");
    public static final Texture notExplorableAreaTexture = Textures.get("textures/red_move_zone.png");

    public Area(Vector2 pos, MoveArea moveArea) {
        this.texture = explorableAreaTexture;
        this.isExplorable = true;
        this.pos = pos;
        this.isAccessible = false;
        this.moveArea = moveArea;
    }

    public void setAreaEntityAccessible(final Entity entity) {
        if (getPos().equals(new Vector2(entity.getPos().x, entity.getPos().y))) {
            setAccessible(true);
        }
    }

    public Optional<Area> getArea(Direction direction) {
        return switch (direction) {
            case UP -> getAreaFromVector(new Vector2(pos.x, pos.y + 1));
            case RIGHT -> getAreaFromVector(new Vector2(pos.x + 1, pos.y));
            case LEFT -> getAreaFromVector(new Vector2(pos.x - 1, pos.y));
            case BOTTOM -> getAreaFromVector(new Vector2(pos.x, pos.y - 1));
            case TOP_LEFT -> getAreaFromVector(new Vector2(pos.x - 1, pos.y + 1));
            case TOP_RIGHT -> getAreaFromVector(new Vector2(pos.x + 1, pos.y + 1));
            case BOTTOM_LEFT -> getAreaFromVector(new Vector2(pos.x - 1, pos.y - 1));
            case BOTTOM_RIGHT -> getAreaFromVector(new Vector2(pos.x + 1, pos.y - 1));
        };
    }

    private Optional<Area> getAreaFromVector(Vector2 vector) {
        final AtomicReference<Optional<Area>> optional = new AtomicReference<>(Optional.empty());
        for (int i = 0; i < moveArea.size; i++) {
            for (int j = 0; j < moveArea.get(i).size; j++) {
                moveArea.get(i).get(j).ifPresent(area -> {
                    if (vector.equals(area.pos))
                        optional.set(Optional.of(area));
                });
            }
        }
        return optional.get();
    }

    public Array<Optional<Area>> getAroundArea() {
        final Array<Optional<Area>> areas = new Array<>();
        areas.add(
                getArea(Direction.UP),
                getArea(Direction.BOTTOM),
                getArea(Direction.LEFT),
                getArea(Direction.RIGHT)
        );
        return areas;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public void setPos(float x, float y) {
        this.pos = new Vector2(x, y);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean isExplorable() {
        return isExplorable;
    }

    public void setExplorable(boolean explorable) {
        isExplorable = explorable;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }

    // TODO: At change !!!
    public boolean isMovable() {
        return getTexture() != null && (isAccessible() || !isExplorable());
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public MoveArea getMoveArea() {
        return moveArea;
    }

    @Override
    public String toString() {
        return "IsAccessible="+isAccessible+ " IsExplorable="+isExplorable +" Pos="+pos;
    }
}
