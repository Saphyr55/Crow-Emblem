package fr.saphyr.ce.world.area.ai;

import com.badlogic.gdx.ai.pfa.Heuristic;
import fr.saphyr.ce.world.area.Area;
import fr.saphyr.ce.world.area.IArea;

public class AreaHeuristic implements Heuristic<IArea> {

    @Override
    public float estimate(IArea node, IArea endNode) {
        return endNode.getRelativePos().dst(node.getRelativePos());
    }
}
