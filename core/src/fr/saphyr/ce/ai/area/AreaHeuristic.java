package fr.saphyr.ce.ai.area;

import com.badlogic.gdx.ai.pfa.Heuristic;
import fr.saphyr.ce.area.Area;
import fr.saphyr.ce.core.Logger;

public class AreaHeuristic implements Heuristic<Area> {

    @Override
    public float estimate(Area node, Area endNode) {
        return endNode.getRelativePos().dst(node.getRelativePos());
    }
}
