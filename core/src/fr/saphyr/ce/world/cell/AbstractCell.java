package fr.saphyr.ce.world.cell;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.graphic.Textures;
import fr.saphyr.ce.world.area.IArea;

import java.util.Optional;

public abstract class AbstractCell implements ICell {

    protected int index;
    protected Vector3 pos;
    protected IEntity contentEntity;
    protected IArea zone;

    public static final Texture BLUE_AREA_TEXTURE = Textures.get("textures/areas/blue_area.png");
    public static final Texture RED_AREA_TEXTURE = Textures.get("textures/areas/red_area.png");
    public static final Texture GREEN_AREA_TEXTURE = Textures.get("textures/areas/green_area.png");
    public static final Texture YELLOW_AREA_TEXTURE = Textures.get("textures/areas/yellow_area.png");

    protected AbstractCell(Vector3 pos, IArea zone) {
        this.pos = pos;
        this.contentEntity = null;
        this.zone = zone;
        setContentEntity();
    }

    protected void setContentEntity() {
        for (int i = 0; i < getZone().getWorld().getEntities().size ; i++) {
            final IEntity entity = getZone().getWorld().getEntities().get(i);
            if (entity.getPos().equals(pos)) contentEntity = entity;
        }
    }

    @Override
    public IArea getZone() {
        return zone;
    }

    @Override
    public Vector3 getPos() {
        return pos;
    }

    @Override
    public void setPos(Vector3 pos) {
        this.pos = pos;
    }

    @Override
    public void setPos(float x, float y) {
        this.pos = new Vector3(x, y, 0);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    public Optional<IEntity> getContentEntity() {
        if (contentEntity != null) return Optional.of(contentEntity);
        return Optional.empty();
    }

    public record MoveAreaAttribute(int key, String textureFilepath, boolean isExplorable) { }
}
