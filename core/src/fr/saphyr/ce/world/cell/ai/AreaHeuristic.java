package fr.saphyr.ce.world.cell.ai;

import com.badlogic.gdx.ai.pfa.Heuristic;
import fr.saphyr.ce.world.cell.MoveCell;

public class AreaHeuristic implements Heuristic<MoveCell> {

    @Override
    public float estimate(MoveCell node, MoveCell endNode) {
        return endNode.getRelativePos().dst(node.getRelativePos());
    }
}
