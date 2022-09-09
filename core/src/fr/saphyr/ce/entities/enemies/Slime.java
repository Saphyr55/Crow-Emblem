package fr.saphyr.ce.entities.enemies;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Enemy;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.graphics.MoveArea;
import fr.saphyr.ce.graphics.MoveAreas;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.worlds.World;

public class Slime extends Enemy {

    private final Animation<TextureRegion> animationIdle;
    private final Animation<TextureRegion> animationDeath;
    private boolean slimeSelected;
    private float slimeStateTime;

    public Slime(World world, Vector3 worldPos, int[] idNotExplorable) {
        super(world, worldPos, idNotExplorable);
        texture = Textures.get("textures/slime/slime_spritesheet.png");
        slimeStateTime = 0;
        slimeSelected = false;
        TextureRegion[][] slimeFrames = splitTexture(3, 3);

        setMoveArea(MoveAreas.DEFAULT_MOVE_ZONE_9);
        animationIdle = new Animation<>(500 / 1000f, slimeFrames[0]);
        animationDeath = new Animation<>(80 / 1000f, slimeFrames[1]);
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
        TextureRegion slimeCurrentFrame = animationIdle.getKeyFrame(slimeStateTime, true);
        renderer.draw(slimeCurrentFrame, pos.x, pos.y, 1, 1);
    }

    @Override
    public void update(final float dt) {
        super.update(dt);
    }

    @Override
    public void whenMoveRight() {

    }

    @Override
    public void whenMoveUp() {

    }

    @Override
    public void whenMoveBottom() {

    }

    @Override
    public void whenMoveLeft() {

    }

}
