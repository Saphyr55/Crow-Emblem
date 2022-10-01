package fr.saphyr.ce.world.area.cell;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Updatable;
import fr.saphyr.ce.world.area.MoveArea;

import java.util.concurrent.atomic.AtomicBoolean;

import static fr.saphyr.ce.entities.Entity.EPSILON;

public class TraceCell implements Updatable {

    private final Array<MoveCell> trace;
    private MoveCell endCell;
    private MoveCell nextCell;
    private MoveArea moveArea;
    private int index = 0;
    private MoveCell cellUpdate;

    public TraceCell(MoveArea moveArea) {
        this.trace = new Array<>();
        this.moveArea = moveArea;
    }

    @Override
    public void update(float dt) {
        if (getMoveArea().isOpen() && !getMoveArea().getEntity().isMoved()) {
            this.endCell = getCellUpdate();
            if (endCell != null) {
                reset();
                getMoveArea().getAreaGraph().findPath(
                        getMoveArea().getCellWithMainEntity(), endCell).forEach(this::add);
            }
        }
    }

    public void init() {
        index = 0;
        getMoveArea().getEntity().setMoved(true);
        if (trace.size > 1) nextCell = trace.get(++index);
    }

    public void next() {
        if (areaClickedAlmostEqualWith(getMoveArea().getEntity().getWorldPos().getPos())) stop();
        else if (nextCell.almostEqualArea(getMoveArea().getEntity().getWorldPos().getPos(), EPSILON)) {
            getMoveArea().getEntity().getWorldPos().getPos().set(nextCell.getPos());
            ++index;
            if (index < trace.size)
                nextCell = trace.get(index);
        }
    }

    public void stop() {
        final var entity = getMoveArea().getEntity();
        if (entity.isMoved()) {
            entity.setMoved(false);
            reset();
            entity.setMoveArea(entity.getMoveAreaAttribute());
            setMoveArea(moveArea);
        }
    }

    public void reset() {
        trace.forEach(area -> area.setTexture(AbstractCell.BLUE_AREA_TEXTURE));
        trace.clear();
        final var entity = getMoveArea().getEntity();
        entity.getCellPressed().ifPresent(area -> entity.getWorldPos().getPos().set(area.getPos()));
        entity.setCellPressed(null);
        nextCell = null;
    }

    private boolean areaClickedAlmostEqualWith(Vector3 pos) {
        final AtomicBoolean atReturn = new AtomicBoolean(false);
        getMoveArea().getEntity().getCellPressed().ifPresent(area -> atReturn.set(area.almostEqualArea(pos, EPSILON)));
        return atReturn.get();
    }

    public MoveCell getNext() {
        return nextCell;
    }

    public boolean hasNext() {
        return nextCell != null;
    }

        private void add(MoveCell cell) {
        cell.setTexture(AbstractCell.GREEN_AREA_TEXTURE);
        trace.add(cell);
    }

    public Array<MoveCell> getTrace() {
        return trace;
    }

    public void setEndCell(MoveCell endCell) {
        this.endCell = endCell;
    }

    public MoveArea getMoveArea() {
        return moveArea.getEntity().getMoveArea();
    }

    public void setMoveArea(MoveArea moveZoneArea) {
        this.moveArea = moveZoneArea;
    }

    public MoveCell getCellUpdate() {
        return cellUpdate;
    }

    public void updateEndArea(MoveCell cell) {
        this.cellUpdate = cell;
    }
}

