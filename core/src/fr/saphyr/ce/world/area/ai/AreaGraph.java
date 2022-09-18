package fr.saphyr.ce.world.area.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import fr.saphyr.ce.world.area.Area;

public class AreaGraph implements IndexedGraph<Area> {

    private final AreaHeuristic areaHeuristic;
    private final Array<Area> areas;
    private final Array<PathArea> pathAreas;
    private final ObjectMap<Area, Array<Connection<Area>>> pathsMap;
    private static int lastNodeIndex = 0;

    public AreaGraph() {
        this.areaHeuristic = new AreaHeuristic();
        this.areas = new Array<>();
        this.pathAreas = new Array<>();
        this.pathsMap = new ObjectMap<>();
    }

    public GraphPath<Area> findPath(Area start, Area end) {
        GraphPath<Area> areaPath = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this)
                .searchNodePath(start, end, areaHeuristic, areaPath);
        return areaPath;
    }

    public void connectAreas(Area start, Area end){
        PathArea pathArea = new PathArea(start, end);
        if(!pathsMap.containsKey(start)){
            pathsMap.put(start, new Array<>());
        }
        pathsMap.get(start).add(pathArea);
        pathAreas.add(pathArea);
    }

    public void addArea(Area area){
        area.setIndex(lastNodeIndex);
        lastNodeIndex++;
        areas.add(area);
    }


    @Override
    public int getIndex(Area node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return lastNodeIndex;
    }

    @Override
    public Array<Connection<Area>> getConnections(Area fromNode) {
        if(pathsMap.containsKey(fromNode))
            return pathsMap.get(fromNode);
        return new Array<>();
    }
}