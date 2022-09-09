package fr.saphyr.ce.area.ai;

import com.badlogic.gdx.ai.pfa.Heuristic;
import fr.saphyr.ce.area.Area;

public class AreaHeuristic implements Heuristic<Area> {
    @Override
    public float estimate(Area node, Area endNode) {
        return node.getPos().dst(endNode.getPos());
    }
}
