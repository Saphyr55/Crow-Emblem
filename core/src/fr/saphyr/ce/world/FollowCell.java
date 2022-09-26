package fr.saphyr.ce.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.world.area.cell.ICell;
import fr.saphyr.ce.world.area.cell.WorldCell;

import static fr.saphyr.ce.world.area.cell.AbstractCell.YELLOW_AREA_TEXTURE;

public class FollowCell implements CEObject {

    private final Camera camera;
    private final IWorld world;
    private WorldCell currentWorldCell;
    private WorldCell futureWorldCell;
    private final Vector3 pos;
    private boolean isMoved;
    private Direction direction;
    public final float TRANSLATE_VELOCITY = 10f;

    public FollowCell(IWorld world) {
        this.camera = world.getCamera();
        this.world = world;
        this.pos = camera.getMiddlePos();
        world.getWorldArea().getCellAt(pos).ifPresent(this::setCurrentWorldCell);
    }

    @Override
    public void update(float dt) {
        move(dt);
        currentWorldCell.getCellOnDirection(direction).ifPresent(this::setFutureWorldCell);
        //pos.set((int)camera.position.x, (int) camera.position.y, 0);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.draw(YELLOW_AREA_TEXTURE, pos.x, pos.y, 1, 1);
    }

    public Vector3 getPos() {
        return pos;
    }

    private void stopMoved() {
    }

    private void move(final float dt) {
        final float velocity = TRANSLATE_VELOCITY * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            direction = Direction.LEFT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            direction = Direction.RIGHT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            direction = Direction.BOTTOM;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            direction = Direction.TOP;
        }
    }

    public void setCurrentWorldCell(ICell currentWorldCell) {
        this.currentWorldCell = (WorldCell) currentWorldCell;
    }

    public WorldCell getCurrentWorldCell() {
        return currentWorldCell;
    }

    public WorldCell getFutureWorldCell() {
        return futureWorldCell;
    }

    public void setFutureWorldCell(ICell futureWorldCell) {
        this.futureWorldCell = (WorldCell) futureWorldCell;
    }

    public Camera getCamera() {
        return camera;
    }
}
