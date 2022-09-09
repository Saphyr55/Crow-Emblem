package fr.saphyr.ce.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.worlds.World;

public abstract class Enemy extends Entity {

    public Enemy(World world, Vector3 pos, int[] tileNotExplorable) {
        super(world, pos, tileNotExplorable);
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
