package fr.saphyr.ce.entities;

import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.worlds.WorldPos;

public abstract class Player extends Entity {

    protected float velocityMove;

    public Player(WorldPos worldPos, int[]tileNotExplorable) {
        super(worldPos, tileNotExplorable);
        velocityMove = 4;
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        updateMove(dt);
    }

    private void updateMove(final float dt) {
        if (areaClicked == null)
            areaClicked = getAreaSelect();
        if (areaClicked != null && areaClicked.isAccessible() && areaClicked.isExplorable())
            move(velocityMove * dt);
        else
            areaClicked = null;
    }


}


