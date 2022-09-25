package fr.saphyr.ce.world.area.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import fr.saphyr.ce.world.area.Area;
import fr.saphyr.ce.world.area.IArea;

public class PathArea implements Connection<IArea> {

    private final IArea start;
    private final IArea end;
    private final float cost;

    public PathArea(IArea start, IArea end) {
        this.start = start;
        this.end = end;
        this.cost = end.getRelativePos().dst(start.getRelativePos());
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public IArea getFromNode() {
        return start;
    }

    @Override
    public IArea getToNode() {
        return end;
    }
}
