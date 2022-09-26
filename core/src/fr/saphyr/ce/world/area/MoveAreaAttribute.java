package fr.saphyr.ce.world.area;

import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.world.cell.AbstractCell;

public final class MoveAreaAttribute {

    private Array<AbstractCell.MoveAreaAttribute> areaAttributes;
    private final int[][] pattern;

    public MoveAreaAttribute(Array<AbstractCell.MoveAreaAttribute> areaAttributes, int[][] pattern) {
        this.areaAttributes = areaAttributes;
        this.pattern = pattern;
    }

    public Array<AbstractCell.MoveAreaAttribute> areaAttributes() {
        return areaAttributes;
    }

    public int[][] pattern() {
        return pattern;
    }

    public void setAreaAttributes(Array<AbstractCell.MoveAreaAttribute> areaAttributes) {
        this.areaAttributes = areaAttributes;
    }
}
