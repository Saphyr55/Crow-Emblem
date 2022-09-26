package fr.saphyr.ce.world.area.cell.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import fr.saphyr.ce.world.area.cell.ICell;

public class CellGraph<C extends ICell> implements IndexedGraph<C> {

    private final CellHeuristic<C> cellHeuristic;
    private final Array<C> cells;
    private final Array<PathCell<C>> pathCells;
    private final ObjectMap<C, Array<Connection<C>>> pathsMap;
    private static int lastNodeIndex = 0;

    public CellGraph() {
        this.cellHeuristic = new CellHeuristic<>();
        this.cells = new Array<>();
        this.pathCells = new Array<>();
        this.pathsMap = new ObjectMap<>();
    }

    public GraphPath<C> findPath(C start, C end) {
        final GraphPath<C> cellDefaultGraphPath = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this)
                .searchNodePath(start, end, cellHeuristic, cellDefaultGraphPath);
        return cellDefaultGraphPath;
    }

    public void connectAreas(C start, C end){
        PathCell<C> pathCell = new PathCell<>(start, end);
        if(!pathsMap.containsKey(start)){
            pathsMap.put(start, new Array<>());
        }
        pathsMap.get(start).add(pathCell);
        pathCells.add(pathCell);
    }

    public void addArea(C cell){
        cell.setIndex(lastNodeIndex);
        lastNodeIndex++;
        cells.add(cell);
    }

    @Override
    public int getIndex(C node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return lastNodeIndex;
    }

    @Override
    public Array<Connection<C>> getConnections(C fromNode) {
        if(pathsMap.containsKey(fromNode)) return pathsMap.get(fromNode);
        return new Array<>();
    }

}