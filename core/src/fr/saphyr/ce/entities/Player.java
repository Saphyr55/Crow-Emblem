package fr.saphyr.ce.entities;

import com.badlogic.gdx.Input;
import fr.saphyr.ce.world.area.cell.ICell;
import fr.saphyr.ce.world.area.cell.MoveCell;
import fr.saphyr.ce.world.area.MoveAreaAttribute;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.world.WorldPos;

public abstract class Player extends Entity {

    public static Player playerSelected = null;
    public static boolean hasPlayerSelected = false;
    protected float velocityMove;
    private float velocityMoveDeltaTime;

    public Player(WorldPos worldPos, int[]tileNotExplorable, MoveAreaAttribute moveAreaAttribute) {
        super(worldPos, tileNotExplorable, moveAreaAttribute);
        velocityMove = 4;
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        moveArea.getCellAt(getWorld().getMouseWorldPos().getPos()).ifPresent(area -> {
            if (area.getContentEntity().isEmpty())
                traceCell.updateEndArea(area);
        });
        traceCell.update(dt);
        setVelocityMoveDeltaTime(dt);
        updateMove(dt);
        updatePlayerSelected();
    }

    private void updatePlayerSelected() {
        if(isClickOnFrame(Input.Buttons.LEFT, worldPos)) {
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

    private void updateMove(final float dt) {
        if (getCellClicked().isEmpty()) getAreaSelect().ifPresent(area -> {
            if (area.getContentEntity().isEmpty())
                this.setCellClicked((MoveCell) area);
        });
        getCellClicked().ifPresent(this::movePlayer);
    }

    private void movePlayer(ICell area) {
        final MoveCell moveArea = (MoveCell) area;
        if (moveArea.isAccessible() && moveArea.isExplorable()) move(velocityMoveDeltaTime);
        else setCellClicked(null);
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


