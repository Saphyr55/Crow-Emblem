package fr.saphyr.ce.world.area.cell.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import fr.saphyr.ce.world.area.cell.ICell;

public class PathCellConnection<C extends ICell> implements Connection<C> {

    private final C start;
    private final C end;
    private final float cost;

    public PathCellConnection(C start, C end) {
        this.start = start;
        this.end = end;
        this.cost = end.getRelativePos().dst(start.getRelativePos());
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public C getFromNode() {
        return start;
    }

    @Override
    public C getToNode() {
        return end;
    }
}
