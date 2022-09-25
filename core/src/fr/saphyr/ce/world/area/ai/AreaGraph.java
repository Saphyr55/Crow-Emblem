package fr.saphyr.ce.world.area.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import fr.saphyr.ce.world.area.Area;
import fr.saphyr.ce.world.area.IArea;

public class AreaGraph implements IndexedGraph<IArea> {

    private final AreaHeuristic areaHeuristic;
    private final Array<IArea> areas;
    private final Array<PathArea> pathAreas;
    private final ObjectMap<IArea, Array<Connection<IArea>>> pathsMap;
    private static int lastNodeIndex = 0;

    public AreaGraph() {
        this.areaHeuristic = new AreaHeuristic();
        this.areas = new Array<>();
        this.pathAreas = new Array<>();
        this.pathsMap = new ObjectMap<>();
    }

    public GraphPath<IArea> findPath(IArea start, IArea end) {
        GraphPath<IArea> areaPath = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this)
                .searchNodePath(start, end, areaHeuristic, areaPath);
        return areaPath;
    }

    public void connectAreas(IArea start, IArea end){
        PathArea pathArea = new PathArea(start, end);
        if(!pathsMap.containsKey(start)){
            pathsMap.put(start, new Array<>());
        }
        pathsMap.get(start).add(pathArea);
        pathAreas.add(pathArea);
    }

    public void addArea(IArea area){
        area.setIndex(lastNodeIndex);
        lastNodeIndex++;
        areas.add(area);
    }


    @Override
    public int getIndex(IArea node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return lastNodeIndex;
    }

    @Override
    public Array<Connection<IArea>> getConnections(IArea fromNode) {
        if(pathsMap.containsKey(fromNode))
            return pathsMap.get(fromNode);
        return new Array<>();
    }
}