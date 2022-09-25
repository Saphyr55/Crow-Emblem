package fr.saphyr.ce.world.area;

import com.badlogic.gdx.utils.Array;

public final class MoveAreaAttribute {

    private Array<Area.AreaAttribute> areaAttributes;
    private final int[][] pattern;

    public MoveAreaAttribute(Array<Area.AreaAttribute> areaAttributes, int[][] pattern) {
        this.areaAttributes = areaAttributes;
        this.pattern = pattern;
    }

    public Array<Area.AreaAttribute> areaAttributes() {
        return areaAttributes;
    }

    public int[][] pattern() {
        return pattern;
    }

    public void setAreaAttributes(Array<Area.AreaAttribute> areaAttributes) {
        this.areaAttributes = areaAttributes;
    }
}
