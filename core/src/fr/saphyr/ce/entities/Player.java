package fr.saphyr.ce.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.area.Area;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.worlds.World;

public abstract class Player extends Entity {


    public Player(World world, Vector2 pos, int[]tileNotExplorable) {
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
            GraphPath<Area> graphPathArea =
                    moveArea.getAreaGraph().findPath(moveArea.getAreaWithEntity(), areaClicked);
            // graphPathArea.forEach(Logger::debug);
            move(velocity);
            // areaClicked = null;
        }
    }



}


