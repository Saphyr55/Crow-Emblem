package fr.saphyr.ce.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import fr.saphyr.ce.area.Area;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.utils.CEMath;
import fr.saphyr.ce.worlds.World;

import java.util.Objects;

public abstract class Player extends Entity {

    protected float velocityMove;

    public Player(World world, Vector2 pos, int[]tileNotExplorable) {
        super(world, pos, tileNotExplorable);
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


