package fr.saphyr.ce.world.cell.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import fr.saphyr.ce.world.cell.ICell;
import fr.saphyr.ce.world.cell.MoveCell;

public class AreaGraph implements IndexedGraph<MoveCell> {

    private final AreaHeuristic areaHeuristic;
    private final Array<MoveCell> areas;
    private final Array<PathMoveArea> pathAreas;
    private final ObjectMap<MoveCell, Array<Connection<MoveCell>>> pathsMap;
    private static int lastNodeIndex = 0;

    public AreaGraph() {
        this.areaHeuristic = new AreaHeuristic();
        this.areas = new Array<>();
        this.pathAreas = new Array<>();
        this.pathsMap = new ObjectMap<>();
    }

    public GraphPath<MoveCell> findPath(MoveCell start, MoveCell end) {
        GraphPath<MoveCell> areaPath = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this)
                .searchNodePath(start, end, areaHeuristic, areaPath);
        return areaPath;
    }

    public void connectAreas(MoveCell start, MoveCell end){
        PathMoveArea pathMoveArea = new PathMoveArea(start, end);
        if(!pathsMap.containsKey(start)){
            pathsMap.put(start, new Array<>());
        }
        pathsMap.get(start).add(pathMoveArea);
        pathAreas.add(pathMoveArea);
    }

    public void addArea(ICell area){
        final MoveCell moveArea = (MoveCell) area;
        moveArea.setIndex(lastNodeIndex);
        lastNodeIndex++;
        areas.add(moveArea);
    }

    @Override
    public int getIndex(MoveCell node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return lastNodeIndex;
    }

    @Override
    public Array<Connection<MoveCell>> getConnections(MoveCell fromNode) {
        if(pathsMap.containsKey(fromNode))
            return pathsMap.get(fromNode);
        return new Array<>();
    }

}