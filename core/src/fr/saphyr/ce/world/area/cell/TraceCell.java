package fr.saphyr.ce.world.area.cell;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Updatable;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.world.area.MoveArea;

import java.util.concurrent.atomic.AtomicBoolean;

import static fr.saphyr.ce.entities.Entity.EPSILON;

public class TraceCell implements Updatable {

    private final Array<MoveCell> trace;
    private MoveCell endCell;
    private MoveCell nextCell;
    private MoveArea moveArea;
    private IEntity entity;
    private int index = 0;
    private MoveCell cellUpdate;
    private boolean isFinish = false;

    public TraceCell(MoveArea moveArea) {
        this.trace = new Array<>();
        this.moveArea = moveArea;
        this.entity = moveArea.getEntity();
    }

    @Override
    public void update(float dt) {
        if (moveArea.isOpen() && !entity.isMoved()) {
            this.endCell = getCellUpdate();
            if (endCell != null) {
                reset();
                moveArea.getAreaGraph().findPath(
                        moveArea.getCellWithMainEntity(), endCell).forEach(this::add);
            }
        }
    }

    public void init() {
        if (!entity.isMoved()) {
            isFinish = true;
            index = 0;
            entity.setMoved(true);
            if (trace.size > 1) nextCell = trace.get(++index);
        }
    }

    public void next() {
        final Vector3 pos = entity.getWorldPos().getPos();
        if (!hasNext()) isFinish = true;
        if (areaClickedAlmostEqualWith(pos)) stop();
        else if (nextCell.almostEqualArea(pos, EPSILON)) {
            pos.set(nextCell.getPos());
            ++index;
            if (index < trace.size)
                nextCell = trace.get(index);
        }
    }

    public void stop() {
        if (entity.isMoved()) {
            entity.setMoved(false);
            reset();
        }
        entity.setMoveArea(entity.getMoveAreaAttribute());
        moveArea = entity.getMoveArea();
    }

    public void reset() {
        trace.forEach(area -> area.setTexture(AbstractCell.BLUE_AREA_TEXTURE));
        trace.clear();
        entity.getCellPressed().ifPresent(area -> entity.getWorldPos().getPos().set(area.getPos()));
        entity.setCellPressed(null);
        nextCell = null;
    }

    private boolean areaClickedAlmostEqualWith(Vector3 pos) {
        final AtomicBoolean atReturn = new AtomicBoolean(false);
        entity.getCellPressed().ifPresent(area -> atReturn.set(area.almostEqualArea(pos, EPSILON)));
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
        return moveArea;
    }

    public void setMoveArea(MoveArea moveZoneArea) {
        this.moveArea = moveZoneArea;
    }

    public MoveCell getCellUpdate() {
        return cellUpdate;
    }

    public void updateEndCell(MoveCell cell) {
        this.cellUpdate = cell;
    }

    public boolean isFinish() {
        return isFinish;
    }
}

