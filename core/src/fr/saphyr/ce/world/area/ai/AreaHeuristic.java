package fr.saphyr.ce.world.area.ai;

import com.badlogic.gdx.ai.pfa.Heuristic;
import fr.saphyr.ce.world.area.Area;

public class AreaHeuristic implements Heuristic<Area> {

    @Override
    public float estimate(Area node, Area endNode) {
        return endNode.getRelativePos().dst(node.getRelativePos());
    }
}
