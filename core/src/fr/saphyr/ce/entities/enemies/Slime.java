package fr.saphyr.ce.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.saphyr.ce.area.MoveAreaAttribute;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Enemy;
import fr.saphyr.ce.graphic.Textures;
import fr.saphyr.ce.world.WorldPos;

public class Slime extends Enemy {

    private final Animation<TextureRegion> animationIdle;
    private final Animation<TextureRegion> animationDeath;
    
    public Slime(WorldPos worldPos, int[] idNotExplorable, MoveAreaAttribute moveAreaAttribute ) {
        super(worldPos, idNotExplorable, moveAreaAttribute);
        texture = Textures.get("textures/slime/slime_spritesheet.png");
        TextureRegion[][] slimeFrames = splitTexture(3, 3);
        animationIdle = new Animation<>(500 / 1000f, slimeFrames[0]);
        animationDeath = new Animation<>(80 / 1000f, slimeFrames[1]);
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
        TextureRegion slimeCurrentFrame = animationIdle.getKeyFrame(stateTime, true);
        renderer.draw(slimeCurrentFrame, worldPos.getPos().x, worldPos.getPos().y, 1, 1);
    }

    @Override
    public void update(final float dt) {
        super.update(dt);
    }









}
