package fr.saphyr.ce.ai.area;

import com.badlogic.gdx.ai.pfa.Connection;
import fr.saphyr.ce.area.Area;

public class PathArea implements Connection<Area> {

    private final Area start;
    private final Area end;
    private final float cost;

    public PathArea(Area start, Area end) {
        this.start = start;
        this.end = end;
        this.cost = end.getRelativePos().dst(start.getRelativePos());
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public Area getFromNode() {
        return start;
    }

    @Override
    public Area getToNode() {
        return end;
    }
}
