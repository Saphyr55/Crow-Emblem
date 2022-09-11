package fr.saphyr.ce.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.worlds.World;
import fr.saphyr.ce.worlds.WorldPos;

public abstract class Enemy extends Entity {

    public Enemy(WorldPos worldPos, int[] tileNotExplorable) {
        super(worldPos, tileNotExplorable);
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
