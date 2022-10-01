package fr.saphyr.ce.entities;

import com.badlogic.gdx.Input;
import fr.saphyr.ce.world.area.cell.ICell;
import fr.saphyr.ce.world.area.cell.MoveCell;
import fr.saphyr.ce.world.area.MoveAreaAttribute;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.world.WorldPos;

import java.util.Optional;

public abstract class Player extends Entity {

    private static Player playerSelected = null;
    public static boolean hasPlayerSelected = false;
    protected float velocityMove = 4;
    private float velocityMoveDeltaTime;

    public Player(WorldPos worldPos, int[]tileNotExplorable, MoveAreaAttribute moveAreaAttribute) {
        super(worldPos, tileNotExplorable, moveAreaAttribute);
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        updateTraceCell(dt);
        setVelocityMoveDeltaTime(dt);
        updateMove();
        updatePlayerSelected();
    }

    private void updateTraceCell(float dt) {
        moveArea.getCellAt(getWorld().getFollowCamera().getPos()).ifPresent(cell -> {
            if (cell.getContentEntity().isEmpty())
                traceCell.updateEndArea(cell);
        });
        traceCell.update(dt);
    }

    private void updatePlayerSelected() {
        if(isPressedOnFrame(Input.Keys.ENTER, worldPos)) {
            if (hasPlayerSelected) {
                hasPlayerSelected = false;
                playerSelected.isSelected = false;
                playerSelected = null;
            }
            else {
                hasPlayerSelected = true;
                playerSelected = this;
                isSelected = true;
            }
        }
    }

    private void updateMove() {
        if (getCellPressed().isEmpty()) getCellWherePressed().ifPresent(cell -> {
            if (cell.getContentEntity().isEmpty()) cellPressed = cell;
        });
        getCellPressed().ifPresent(this::movePlayer);
    }

    private void movePlayer(MoveCell cell) {
        if (cell.isAccessible() && cell.isExplorable()) move(velocityMoveDeltaTime);
        else
            cellPressed = null;
    }

    private void setVelocityMoveDeltaTime(float dt) {
        velocityMoveDeltaTime = velocityMove * dt;
    }

    public static Player getPlayerSelected() {
        return playerSelected;
    }

    public static boolean hasEntitySelected() {
        return hasPlayerSelected;
    }
}


