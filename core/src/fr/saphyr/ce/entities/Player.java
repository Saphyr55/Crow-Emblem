package fr.saphyr.ce.entities;

import fr.saphyr.ce.area.Area;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.worlds.WorldPos;

import java.util.Optional;

public abstract class Player extends Entity {

    protected float velocityMove;
    private float velocityMoveDeltaTime;

    public Player(WorldPos worldPos, int[]tileNotExplorable, int[][] moveAreaInt ) {
        super(worldPos, tileNotExplorable, moveAreaInt);
        velocityMove = 4;
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
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


}


