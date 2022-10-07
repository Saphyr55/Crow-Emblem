package fr.saphyr.ce.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.world.area.cell.ICell;
import fr.saphyr.ce.world.area.cell.WorldCell;

import static fr.saphyr.ce.world.area.cell.AbstractCell.YELLOW_CELL_TEXTURE;

public class FollowCamera implements CEObject {

    private final IWorld world;
    private final Texture yellowTexture;
    private WorldCell currentWorldCell;
    private WorldCell futureWorldCell;
    private final Vector3 pos;
    private boolean isMoved;
    private Direction direction;
    public final float TRANSLATE_VELOCITY = .17f;

    public FollowCamera(IWorld world) {
        this.world = world;
        this.yellowTexture = YELLOW_CELL_TEXTURE;
        this.pos = new Vector3(world.getCamera().position);
        this.isMoved = false;
        world.getWorldArea().getCellAt(pos).ifPresent(this::setCurrentWorldCell);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.draw(yellowTexture, pos.x, pos.y, 1, 1);
    }

    @Override
    public void update(float dt) {
        if(!isMoved)
            updateDirection();
        move();
        stopMoved();
        updateCamera();
    }

    private void move() {
        currentWorldCell.getCellOnDirection(direction).ifPresent(this::setFutureWorldCell);
        if (futureWorldCell != null && isMoved) {
            switch (direction) {
                case BOTTOM -> moveBottom();
                case RIGHT -> moveRight();
                case LEFT -> moveLeft();
                case TOP -> moveTop();
                case BOTTOM_LEFT -> {
                    moveBottom();
                    moveLeft();
                }
                case TOP_RIGHT -> {
                    moveTop();
                    moveRight();
                }
                case TOP_LEFT -> {
                    moveTop();
                    moveLeft();
                }
                case BOTTOM_RIGHT -> {
                    moveBottom();
                    moveRight();
                }
            }
        }
    }

    private void moveTop() {
        if (futureWorldCell.getPos().y > currentWorldCell.getPos().y) {
            pos.add(0, TRANSLATE_VELOCITY, 0);
        }
    }

    private void moveLeft() {
        if (futureWorldCell.getPos().x < currentWorldCell.getPos().x) {
            pos.add(-TRANSLATE_VELOCITY, 0, 0);
        }
    }

    private void moveBottom() {
        if (futureWorldCell.getPos().y < currentWorldCell.getPos().y) {
            pos.add(0, -TRANSLATE_VELOCITY, 0);
        }
    }

    private void moveRight() {
        if (futureWorldCell.getPos().x > currentWorldCell.getPos().x) {
            pos.add(TRANSLATE_VELOCITY, 0, 0);
        }
    }

    public Vector3 getPos() {
        return pos;
    }

    private void updateCamera() {
        final int width = world.getMap().getWidth();
        final int height = world.getMap().getHeight();
        if (pos.x > width / 4f - TRANSLATE_VELOCITY && pos.x < width - width / 4.f + TRANSLATE_VELOCITY)
            world.getCamera().position.x = pos.x;
        if (pos.y > height / 4.f - 2.6f && pos.y < height - height / 4.f + 2.6f)
            world.getCamera().position.y = pos.y;
    }


    private void stopMoved() {
        if (futureWorldCell==null) isMoved = false;
        if (isMoved && futureWorldCell.almostEqualArea(pos, 0.1f)) {
            pos.set(futureWorldCell.getPos());
            isMoved = false;
            direction = null;
            currentWorldCell = futureWorldCell;
            futureWorldCell = null;
        }
    }

    private void updateDirection() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            direction = Direction.RIGHT;
            isMoved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            direction = Direction.LEFT;
            isMoved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            direction = Direction.BOTTOM;
            isMoved = true;
            if (Gdx.input.isKeyPressed(Input.Keys.A))
                direction = Direction.BOTTOM_LEFT;
            if (Gdx.input.isKeyPressed(Input.Keys.D))
                direction = Direction.BOTTOM_RIGHT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            direction = Direction.TOP;
            isMoved = true;
            if (Gdx.input.isKeyPressed(Input.Keys.A))
                direction = Direction.TOP_LEFT;
            if (Gdx.input.isKeyPressed(Input.Keys.D))
                direction = Direction.TOP_RIGHT;
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

}
