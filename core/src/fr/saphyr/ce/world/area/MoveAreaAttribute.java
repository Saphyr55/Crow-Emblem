package fr.saphyr.ce.world.area;

import com.badlogic.gdx.utils.Array;

public final class MoveAreaAttribute {

    private Array<AreaAttribute> areaAttributes;
    private final int[][] pattern;

    public MoveAreaAttribute(Array<AreaAttribute> areaAttributes, int[][] pattern) {
        this.areaAttributes = areaAttributes;
        this.pattern = pattern;
    }

    public Array<AreaAttribute> areaAttributes() {
        return areaAttributes;
    }

    public int[][] pattern() {
        return pattern;
    }

    public void setAreaAttributes(Array<AreaAttribute> areaAttributes) {
        this.areaAttributes = areaAttributes;
    }
}
