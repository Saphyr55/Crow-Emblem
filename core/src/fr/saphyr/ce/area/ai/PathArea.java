package fr.saphyr.ce.area.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;
import fr.saphyr.ce.area.Area;

public class PathArea implements Connection<Area> {

    private Area start, end;
    private float cost;

    public PathArea(Area start, Area end) {
        this.start = start;
        this.end = end;
        this.cost = start.getPos().dst(end.getPos());
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
