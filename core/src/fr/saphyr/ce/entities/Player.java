package fr.saphyr.ce.entities;

import com.badlogic.gdx.Input;
import fr.saphyr.ce.entities.characters.Character;
import fr.saphyr.ce.world.area.MoveAreaAttribute;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.world.WorldPos;

public abstract class Player extends Character {

    private static Player playerSelected = null;
    public static boolean hasPlayerSelected = false;
    protected final float velocityMove = 4;
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
        setVelocityMoveDeltaTime(dt);
        updateTraceCell();
        updateMove();
        movePlayer();
        updatePlayerSelected();
    }

    private void updateTraceCell() {
        if (moveArea.isOpen()) {
            moveArea.getCellAt(getWorld().getFollowCamera().getPos()).ifPresent(cell ->
                    getWorld().getWorldArea().getCellAt(cell.getPos()).ifPresent(worldCell -> {
                                if (worldCell.getContentEntity().isEmpty()) traceCell.updateEndCell(cell);
                    })
            );
        }
    }

    private void updatePlayerSelected() {
        if((state == EntityState.WAIT || state == EntityState.FINISH) && isPressedOnFrame(Input.Keys.ENTER, worldPos)) {
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
        if (state == EntityState.WAIT && getMoveCellPressed().isEmpty())
            getCellWhenPressedBy(Input.Keys.ENTER).ifPresent(worldCell -> {
            if (worldCell.getContentEntity().isEmpty()) {
                moveArea.getCellAt(worldCell.getPos()).ifPresent(worldCell1 -> moveCellPressed = worldCell1);
            }
        });
    }

    private void movePlayer() {
        getMoveCellPressed().ifPresent(cell -> {
            if (cell.isAccessible() && cell.isExplorable())
                move(velocityMoveDeltaTime);
            else
                moveCellPressed = null;
        });
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


