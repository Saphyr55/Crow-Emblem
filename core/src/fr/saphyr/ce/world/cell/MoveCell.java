package fr.saphyr.ce.world.cell;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.world.area.IArea;

public class MoveCell extends AbstractCell {

    private Vector3 relativePos;
    private Texture texture;
    private boolean isExplorable;
    private boolean isAccessible;

    public MoveCell(Vector3 pos, Vector3 relativePos, IArea zone) {
        super(pos, zone);
        this.relativePos = relativePos;
    }

    public void setAreaEntityAccessible(final IEntity entity) {
        if (pos.equals(entity.getWorldPos().getPos())) setAccessible(true);
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

    public Vector3 getRelativePos() {
        return relativePos;
    }

    public void setRelativePos(Vector3 relativePos) {
        this.relativePos = relativePos;
    }

}
