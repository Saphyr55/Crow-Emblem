package fr.saphyr.ce.entities.area.cell;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.world.area.IArea;
import fr.saphyr.ce.world.area.cell.AbstractCell;

public class AttackCell extends AbstractCell<AttackCell> implements CEObject {

    private Texture texture;

    public AttackCell(Vector3 pos, Vector3 relativePos, IArea<AttackCell> area) { super(pos, relativePos, area); }


    @Override
    public void update(float dt) {

    }

    @Override
    public void render(Renderer renderer) {
        renderer.draw(texture, pos.x, pos.y, 1, 1);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }


}
