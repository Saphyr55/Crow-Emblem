package fr.saphyr.ce.entities.players;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Player;
import fr.saphyr.ce.area.MoveAreas;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.worlds.World;

public final class LordPlayer extends Player {

    private Animation<TextureRegion> currentAnimation;
    private final Animation<TextureRegion> animationIdleLeft;
    private final Animation<TextureRegion> animationIdleUp;
    private final Animation<TextureRegion> animationIdleBottom;
    private final Animation<TextureRegion> animationIdleRight;
    private final Animation<TextureRegion> animationAttack;
    private final TextureRegion[][] frames;
    private TextureRegion currentFrame;


    public LordPlayer(World world, Vector2 pos, int[]tileNotExplorable) {
        super(world, pos, tileNotExplorable);
        final float frameDurationMove = 100 / 1000f;
        texture = Textures.get("textures/entities/blade_lord/Blade Lord (F) Brave Lyn Bow {StreetHero}-walk.png");
        frames = splitTexture(1, 15);
        setMoveArea(MoveAreas.DEFAULT_MOVE_ZONE_9);
        animationIdleLeft = new Animation<>(frameDurationMove, frames[0][0], frames[1][0], frames[2][0], frames[3][0]);
        animationIdleRight = new Animation<>(frameDurationMove, frames[0][0], frames[1][0], frames[2][0], frames[3][0]);
        animationAttack = new Animation<>(frameDurationMove, frames[12][0], frames[13][0], frames[14][0]);
        animationIdleBottom = new Animation<>(frameDurationMove, frames[4][0], frames[5][0], frames[6][0], frames[7][0]);
        animationIdleUp = new Animation<>(frameDurationMove, frames[8][0], frames[9][0], frames[10][0], frames[11][0]);
        currentAnimation = animationIdleBottom;
        currentFrame = frames[1][0];
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
        renderer.draw(currentFrame, pos.x - 0.15f, pos.y, 1.5f, 1.5f);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (direction == Direction.BOTTOM)
            currentAnimation = animationIdleBottom;
        if (direction == Direction.UP)
            currentAnimation = animationIdleUp;
        if (direction == Direction.LEFT)
            currentAnimation = animationIdleLeft;
        if (direction == Direction.RIGHT)
            currentAnimation = animationIdleRight;

        if (currentAnimation == animationIdleRight && !isMoved)
            currentFrame = frames[1][0];

        if (currentAnimation == animationIdleLeft && !isMoved)
            currentFrame = frames[1][0];

        if (isMoved)
            currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        if (currentAnimation == animationIdleRight && !isMoved)
            currentFrame = frames[1][0];
        else if (currentAnimation == animationIdleLeft && !isMoved)
            currentFrame = frames[1][0];

        if (currentAnimation == animationIdleRight && !currentFrame.isFlipX())
            currentFrame.flip(true, false);
        else if (currentAnimation == animationIdleLeft && currentFrame.isFlipX())
            currentFrame.flip(true, false);



    }

}
