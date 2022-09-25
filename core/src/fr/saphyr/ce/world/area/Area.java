package fr.saphyr.ce.world.area;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.graphic.Textures;

import java.util.Optional;

public class Area implements IArea {

    private int index;
    private final MoveArea moveArea;
    private Vector3 pos;
    private Vector3 relativePos;
    private Texture texture;
    private boolean isExplorable;
    private boolean isAccessible;
    private IEntity contentEntity;

    public static final Texture BLUE_AREA_TEXTURE = Textures.get("textures/areas/blue_area.png");
    public static final Texture RED_AREA_TEXTURE = Textures.get("textures/areas/red_area.png");
    public static final Texture GREEN_AREA_TEXTURE = Textures.get("textures/areas/green_area.png");
    public static final Texture YELLOW_AREA_TEXTURE = Textures.get("textures/areas/yellow_area.png");

    public Area(Vector3 pos, Vector3 relativePos, MoveArea moveArea) {
        this.pos = pos;
        this.moveArea = moveArea;
        this.relativePos = relativePos;
        this.contentEntity = null;
        setContentEntity();
    }

    public void setContentEntity() {
        for (int i = 0; i < moveArea.getEntity().getWorld().getEntities().size ; i++) {
            final IEntity entity = moveArea.getEntity().getWorld().getEntities().get(i);
            if (entity.getPos().equals(pos)) contentEntity = entity;
        }
    }

    public void setAreaEntityAccessible(final IEntity entity) {
        if (this.pos.equals(entity.getWorldPos().getPos())) setAccessible(true);
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

    public Optional<IEntity> getContentEntity() {
        if (contentEntity != null) return Optional.of(contentEntity);
        return Optional.empty();
    }

    public record AreaAttribute(int key, String textureFilepath, boolean isExplorable) { }
}
