package fr.saphyr.ce.entities;

import com.badlogic.gdx.Input;
import fr.saphyr.ce.area.Area;
import fr.saphyr.ce.area.MoveAreaAttribute;
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
        moveArea.getAreaWithPos(getWorld().getMouseWorldPos().getPos())
                .ifPresent(area -> traceArea.updateEndArea(() -> area));
        traceArea.update(dt);
        setVelocityMoveDeltaTime(dt);
        updateMove(dt);
    }

    private void updateMove(final float dt) {
        if (getAreaClicked().isEmpty()) getAreaSelect().ifPresent(this::setAreaClicked);
        getAreaClicked().ifPresent(this::movePlayer);
    }

    private void movePlayer(Area area) {
        if (area.isAccessible() && area.isExplorable()) move(velocityMoveDeltaTime);
        else setAreaClicked(null);
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


