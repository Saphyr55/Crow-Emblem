package fr.saphyr.ce.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Enemy;
import fr.saphyr.ce.area.MoveAreas;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.worlds.World;

public class Slime extends Enemy {

    private final Animation<TextureRegion> animationIdle;
    private final Animation<TextureRegion> animationDeath;

    public Slime(World world, Vector2 worldPos, int[] idNotExplorable) {
        super(world, worldPos, idNotExplorable);
        texture = Textures.get("textures/slime/slime_spritesheet.png");
        TextureRegion[][] slimeFrames = splitTexture(3, 3);

        setMoveArea(MoveAreas.DEFAULT_MOVE_ZONE_9);
        animationIdle = new Animation<>(500 / 1000f, slimeFrames[0]);
        animationDeath = new Animation<>(80 / 1000f, slimeFrames[1]);
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
        TextureRegion slimeCurrentFrame = animationIdle.getKeyFrame(stateTime, true);
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
