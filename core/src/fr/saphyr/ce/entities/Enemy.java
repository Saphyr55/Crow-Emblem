package fr.saphyr.ce.entities;

import fr.saphyr.ce.world.area.MoveAreaAttribute;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.world.WorldPos;

public abstract class Enemy extends Entity {

    public Enemy(WorldPos worldPos, int[] tileNotExplorable, MoveAreaAttribute moveAreaAttribute) {
        super(worldPos, tileNotExplorable, moveAreaAttribute);
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }
}
