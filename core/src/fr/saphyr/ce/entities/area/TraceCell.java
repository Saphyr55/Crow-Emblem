package fr.saphyr.ce.entities.area;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Updatable;
import fr.saphyr.ce.entities.EntityState;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.entities.area.cell.MoveCell;
import fr.saphyr.ce.world.area.cell.AbstractCell;

import java.util.concurrent.atomic.AtomicBoolean;

import static fr.saphyr.ce.entities.Entity.EPSILON;

public class TraceCell implements Updatable {

    private final Array<MoveCell> trace;
    private MoveCell endCell;
    private MoveCell nextCell;
    private MoveArea moveArea;
    private final IEntity entity;
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
        if (entity.getState() == EntityState.WAIT) {
            this.endCell = getCellUpdate();
            if (endCell != null) {
                reset();
                moveArea.getAreaGraph()
                        .findPath(moveArea.getCellWithMainEntity(), endCell)
                        .forEach(this::add);
            }
        }
    }

    public void init() {
        index = 0;
        isFinish = false;
        entity.setState(EntityState.MOVED);
        if (trace.size > 1) {
            nextCell = trace.get(++index);
        }
    }

    public void next() {
        final Vector3 pos = entity.getWorldPos().getPos();
        if (nextCell.almostEqualArea(pos, EPSILON)) {
            pos.set(nextCell.getPos());
            ++index;
            if (index < trace.size) nextCell = trace.get(index);
            else nextCell = null;
        }
    }

    public void stop() {
        reset();
        entity.setAttackArea(AttackAreas.getDefault(entity));
        entity.getAttackArea().setEntities();
        entity.setMoveArea(entity.getMoveAreaAttribute());
        moveArea = entity.getMoveArea();
    }

    private void reset() {
        trace.forEach(area -> area.setTexture(AbstractCell.BLUE_CELL_TEXTURE));
        trace.clear();
        nextCell = null;
        entity.getMoveCellPressed().ifPresent(area -> entity.getWorldPos().getPos().set(area.getPos()));
        entity.setMoveCellPressed(null);
    }

    public MoveCell getNext() {
        return nextCell;
    }

    public boolean hasNext() {
        return nextCell != null;
    }

    private void add(MoveCell cell) {
        cell.setTexture(AbstractCell.GREEN_CELL_TEXTURE);
        trace.add(cell);
    }

    public Array<MoveCell> getTrace() {
        return trace;
    }

    public MoveCell getEndCell() {
        return endCell;
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

