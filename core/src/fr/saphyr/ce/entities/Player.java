package fr.saphyr.ce.entities;

import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.worlds.World;

public abstract class Player extends Entity {


    public Player(World world, Vector3 pos, int[]tileNotExplorable) {
        super(world, pos, tileNotExplorable);
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
            areaClicked = getAreaClickFromMoveArea();

        if (areaClicked != null && areaClicked.isAccessible() &&
                areaClicked.isExplorable() && moveArea.isOpen()) {
            final float velocity = dt * 4;
            isMoved = true;
            move(velocity);
        }
    }



}


