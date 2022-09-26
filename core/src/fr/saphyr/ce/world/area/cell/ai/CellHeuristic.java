package fr.saphyr.ce.world.area.cell.ai;

import com.badlogic.gdx.ai.pfa.Heuristic;
import fr.saphyr.ce.world.area.cell.ICell;

public class CellHeuristic<C extends ICell> implements Heuristic<C> {

    @Override
    public float estimate(C node, C endNode) {
        return endNode.getRelativePos().dst(node.getRelativePos());
    }
}
