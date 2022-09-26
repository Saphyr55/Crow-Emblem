package fr.saphyr.ce.world.cell.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import fr.saphyr.ce.world.cell.MoveCell;

public class PathMoveArea implements Connection<MoveCell> {

    private final MoveCell start;
    private final MoveCell end;
    private final float cost;

    public PathMoveArea(MoveCell start, MoveCell end) {
        this.start = start;
        this.end = end;
        this.cost = end.getRelativePos().dst(start.getRelativePos());
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public MoveCell getFromNode() {
        return start;
    }

    @Override
    public MoveCell getToNode() {
        return end;
    }
}
