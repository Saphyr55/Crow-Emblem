package fr.saphyr.ce.area;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.graphics.Textures;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Area {

    private int index;
    private final MoveArea moveArea;
    private Vector3 pos;
    private Vector3 relativePos;
    private Texture texture;
    private boolean isExplorable;
    private boolean isAccessible;
    public static final Texture explorableAreaTexture = Textures.get("textures/blue_move_zone.png");
    public static final Texture notExplorableAreaTexture = Textures.get("textures/red_move_zone.png");

    public Area(Vector3 pos, Vector3 relativePos, MoveArea moveArea) {
        this.texture = explorableAreaTexture;
        this.isExplorable = true;
        this.pos = pos;
        this.isAccessible = false;
        this.moveArea = moveArea;
        this.relativePos = relativePos;
    }

    public void setAreaEntityAccessible(final Entity entity) {
        if (getPos().equals(entity.getWorldPos().getPos()))
            setAccessible(true);
    }

    public Optional<Area> getAreaOnDirection(Direction direction) {
        return switch (direction) {
            case UP -> getAreaFromVector(new Vector3(pos.x, pos.y + 1, 0));
            case RIGHT -> getAreaFromVector(new Vector3(pos.x + 1, pos.y, 0));
            case LEFT -> getAreaFromVector(new Vector3(pos.x - 1, pos.y, 0));
            case BOTTOM -> getAreaFromVector(new Vector3(pos.x, pos.y - 1, 0));
            case TOP_LEFT -> getAreaFromVector(new Vector3(pos.x - 1, pos.y + 1, 0));
            case TOP_RIGHT -> getAreaFromVector(new Vector3(pos.x + 1, pos.y + 1, 0));
            case BOTTOM_LEFT -> getAreaFromVector(new Vector3(pos.x - 1, pos.y - 1, 0));
            case BOTTOM_RIGHT -> getAreaFromVector(new Vector3(pos.x + 1, pos.y - 1, 0));
        };
    }

    private Optional<Area> getAreaFromVector(Vector3 vector) {
        final var optional = new AtomicReference<Optional<Area>>(Optional.empty());
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
                getAreaOnDirection(Direction.UP),
                getAreaOnDirection(Direction.BOTTOM),
                getAreaOnDirection(Direction.LEFT),
                getAreaOnDirection(Direction.RIGHT)
        );
        return areas;
    }

    public Vector3 getPos() {
        return pos;
    }

    public void setPos(Vector3 pos) {
        this.pos = pos;
    }

    public void setPos(float x, float y) {
        this.pos = new Vector3(x, y, 0);
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public MoveArea getMoveArea() {
        return moveArea;
    }

    public Vector3 getRelativePos() {
        return relativePos;
    }

    public void setRelativePos(Vector3 relativePos) {
        this.relativePos = relativePos;
    }

    @Override
    public String toString() {
        return "\nIsAccessible="+isAccessible+"\n"+
                "IsExplorable="+isExplorable +"\n"+
                "Pos="+pos + "\n"+
                "RPos="+relativePos+"\n";
    }
}
