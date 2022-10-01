package fr.saphyr.ce.world.area.cell;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.graphic.Textures;
import fr.saphyr.ce.world.area.IArea;

import java.util.Optional;

public abstract class AbstractCell<C extends ICell> implements ICell {

    protected int index;
    protected Vector3 pos;
    protected Vector3 relativePos;
    protected IEntity contentEntity;
    protected IArea<C> area;

    public static final Texture BLUE_AREA_TEXTURE = Textures.get("textures/areas/blue_area.png");
    public static final Texture RED_AREA_TEXTURE = Textures.get("textures/areas/red_area.png");
    public static final Texture GREEN_AREA_TEXTURE = Textures.get("textures/areas/green_area.png");
    public static final Texture YELLOW_AREA_TEXTURE = Textures.get("textures/areas/yellow_area.png");

    protected AbstractCell(Vector3 pos, Vector3 relativePos, IArea<C> area) {
        this.pos = pos;
        this.relativePos = relativePos;
        this.contentEntity = null;
        this.area = area;
        setContentEntity();
    }

    protected AbstractCell(Vector3 pos, IArea<C> area) {
        this.pos = pos;
        this.relativePos = pos;
        this.contentEntity = null;
        this.area = area;
        setContentEntity();
    }

    protected void setContentEntity() {
        for (int i = 0; i < getArea().getWorld().getEntities().size ; i++) {
            final IEntity entity = getArea().getWorld().getEntities().get(i);
            if (entity.getPos().equals(pos)) contentEntity = entity;
        }
    }

    @Override
    public IArea<C> getArea() {
        return area;
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

    public Vector3 getRelativePos() {
        return relativePos;
    }

    public void setRelativePos(Vector3 relativePos) {
        this.relativePos = new Vector3(relativePos);
    }

    @Override
    public void setRelativePos(float x, float y) {
        this.relativePos = new Vector3(x, y, 0);
    }

    public Optional<IEntity> getContentEntity() {
        if (contentEntity != null) return Optional.of(contentEntity);
        return Optional.empty();
    }

    public record MoveAreaAttribute(int key, String textureFilepath, boolean isExplorable) { }
}
