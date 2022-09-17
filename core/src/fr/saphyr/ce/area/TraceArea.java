package fr.saphyr.ce.area;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Updatable;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.utils.CEMath;

import java.util.concurrent.atomic.AtomicBoolean;

import static fr.saphyr.ce.entities.Entity.EPSILON;

public class TraceArea implements Updatable {

    private static final Texture TEXTURE_GREEN_MOVE_AREA = Textures.get("textures/green_move_zone.png");
    private static final Texture TEXTURE_BLUE_MOVE_AREA = Textures.get("textures/blue_move_zone.png");
    private final Array<Area> trace;
    private Area endArea;
    private Area nextArea;
    private MoveArea moveArea;
    private int index = 0;

    public TraceArea(MoveArea moveArea) {
        this.trace = new Array<>();
        this.moveArea = moveArea;
    }

    @Override
    public void update(float dt) {
        if (getMoveArea().isOpen() && !getMoveArea().getEntity().isMoved()) {
            getMoveArea().getAreaWithPos(getMoveArea().getEntity().getWorld().getMouseWorldPos().getPos()).ifPresent(this::setEndArea);
            if (endArea != null) {
                reset();
                getMoveArea().getAreaGraph().findPath(getMoveArea().getAreaWithMainEntity(), endArea).forEach(this::add);
            }
        }
    }

    public void init() {
        index = 0;
        getMoveArea().getEntity().setMoved(true);
        if (trace.size > 1) nextArea = trace.get(++index);
    }

    public void next() {
        if (areaClickedAlmostEqualWith(getMoveArea().getEntity().getWorldPos().getPos())) stop();
        else if (almostEqualArea(nextArea, getMoveArea().getEntity().getWorldPos().getPos())) {
            getMoveArea().getEntity().getWorldPos().getPos().set(nextArea.getPos());
            ++index;
            if (index < trace.size)
                nextArea = trace.get(index);
        }
    }

    public void stop() {
        final var entity = getMoveArea().getEntity();
        if (entity.isMoved()) {
            entity.setMoved(false);
            reset();
            entity.setMoveArea(entity.getMoveAreaInt());
            setMoveArea(moveArea);
        }
    }

    public void reset() {
        trace.forEach(area -> area.setTexture(TEXTURE_BLUE_MOVE_AREA));
        trace.clear();
        final var entity = getMoveArea().getEntity();
        entity.getAreaClicked().ifPresent(area -> entity.getWorldPos().getPos().set(area.getPos()));
        entity.setAreaClicked(null);
        nextArea = null;
    }

    public boolean areaClickedAlmostEqualWith(Vector3 pos) {
        final AtomicBoolean atReturn = new AtomicBoolean(false);
        getMoveArea().getEntity().getAreaClicked().ifPresent(area -> atReturn.set(almostEqualArea(area, pos)));
        return atReturn.get();
    }

    private boolean almostEqualArea(Area area, Vector3 pos) {
        return CEMath.almostEqual(area.getPos().x, pos.x, EPSILON) && CEMath.almostEqual(area.getPos().y, pos.y, EPSILON);
    }

    public Area getNext() {
        return nextArea;
    }

    public boolean hasNext() {
        return nextArea != null;
    }

    private void add(Area area) {
        area.setTexture(TEXTURE_GREEN_MOVE_AREA);
        trace.add(area);
    }

    public Array<Area> getTrace() {
        return trace;
    }

    private void setEndArea(Area endArea) {
        this.endArea = endArea;
    }

    public MoveArea getMoveArea() {
        return moveArea.getEntity().getMoveArea();
    }

    public void setMoveArea(MoveArea moveArea) {
        this.moveArea = moveArea;
    }
}

