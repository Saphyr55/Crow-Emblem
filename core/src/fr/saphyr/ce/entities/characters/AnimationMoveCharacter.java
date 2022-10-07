package fr.saphyr.ce.entities.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.core.Pos;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.EntityState;
import fr.saphyr.ce.entities.IEntity;

public class AnimationMoveCharacter implements CEObject {

    public float correctionPosX = -.25f;
    public float correctionPosY = 0;
    private int countCol;
    private int countRow;
    private float frameDuration;
    private Character character;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> currentAnimation;
    private Animation<TextureRegion> animationIdleLeft;
    private Animation<TextureRegion> animationIdleUp;
    private Animation<TextureRegion> animationIdleBottom;
    private Animation<TextureRegion> animationIdleRight;
    private TextureRegion[][] frames;

    public AnimationMoveCharacter(Character character, int countCol, int countRow, float frameDuration) {
        this.character = character;
        this.countCol = countCol;
        this.countRow = countRow;
        this.frameDuration = frameDuration;
        this.frames = splitTexture(character.getTexture(), countCol, countRow);
    }

    private static TextureRegion[][] splitTexture(Texture texture, int col, int row) {
        return TextureRegion.split(texture, texture.getWidth() / col, texture.getHeight() / row);
    }

    @Override
    public void update(float dt) {
        if (character.getDirection() == Direction.BOTTOM)
            currentAnimation = animationIdleBottom;
        if (character.getDirection() == Direction.TOP)
            currentAnimation = animationIdleUp;
        if (character.getDirection() == Direction.LEFT)
            currentAnimation = animationIdleLeft;
        if (character.getDirection() == Direction.RIGHT)
            currentAnimation = animationIdleRight;

        if (currentAnimation == animationIdleRight && character.getState() == EntityState.FINISH)
            currentFrame = frames[1][0];

        if (currentAnimation == animationIdleLeft && character.getState() == EntityState.FINISH)
            currentFrame = frames[1][0];

        if (character.getState() == EntityState.MOVED)
            currentFrame = currentAnimation.getKeyFrame(character.getStateTime(), true);

        if (currentAnimation == animationIdleRight && character.getState() == EntityState.FINISH)
            currentFrame = frames[1][0];
        else if (currentAnimation == animationIdleLeft && character.getState() == EntityState.FINISH)
            currentFrame = frames[1][0];

        if (currentAnimation == animationIdleRight && !currentFrame.isFlipX())
            currentFrame.flip(true, false);
        else if (currentAnimation == animationIdleLeft && currentFrame.isFlipX())
            currentFrame.flip(true, false);

    }

    @Override
    public void render(Renderer renderer) {
        renderer.draw(currentFrame, character.getPos().x + correctionPosX, character.getPos().y + correctionPosY, 1.5f, 1.5f);
    }

    public float getCorrectionPosX() {
        return correctionPosX;
    }

    public void setCorrectionPosX(float correctionPosX) {
        this.correctionPosX = correctionPosX;
    }

    public float getCorrectionPosY() {
        return correctionPosY;
    }

    public void setCorrectionPosY(float correctionPosY) {
        this.correctionPosY = correctionPosY;
    }

    public int getCountCol() {
        return countCol;
    }

    public void setCountCol(int countCol) {
        this.countCol = countCol;
    }

    public int getCountRow() {
        return countRow;
    }

    public void setCountRow(int countRow) {
        this.countRow = countRow;
    }

    public float getFrameDuration() {
        return frameDuration;
    }

    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
    }

    public IEntity getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(Pos pos) {
        this.currentFrame = frames[pos.x()][pos.y()];
    }

    public Animation<TextureRegion> getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation<TextureRegion> currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public Animation<TextureRegion> getAnimationIdleLeft() {
        return animationIdleLeft;
    }

    public void setAnimationIdleLeft(TextureRegion... animationsIdleLeft) {
        this.animationIdleLeft = new Animation<>(frameDuration, animationsIdleLeft);
    }

    public Animation<TextureRegion> getAnimationIdleUp() {
        return animationIdleUp;
    }

    public void setAnimationIdleUp(TextureRegion... animationsIdleUp) {
        this.animationIdleUp = new Animation<>(frameDuration, animationsIdleUp);
    }

    public Animation<TextureRegion> getAnimationIdleBottom() {
        return animationIdleBottom;
    }

    public void setAnimationIdleBottom(TextureRegion... animationIdleBottom) {
        this.animationIdleBottom = new Animation<>(frameDuration, animationIdleBottom);
    }

    public Animation<TextureRegion> getAnimationIdleRight() {
        return animationIdleRight;
    }

    public void setAnimationIdleRight(TextureRegion... animationsIdleRight) {
        this.animationIdleRight = new Animation<>(frameDuration, animationsIdleRight);
    }

    public TextureRegion[][] getFrames() {
        return frames;
    }

    public void setFrames(TextureRegion[][] frames) {
        this.frames = frames;
    }


    public static class Builder {

        private final AnimationMoveCharacter animationMoveCharacter;

        private Builder(Character character, int countCol, int countRow, float frameDuration) {
            this.animationMoveCharacter = new AnimationMoveCharacter(character, countCol, countRow, frameDuration);
        }

        public static Builder of(Character character, int countCol, int countRow, float frameDuration) {
            return new Builder(character, countCol,countRow, frameDuration);
        }

        public AnimationMoveCharacter build() {
            return animationMoveCharacter;
        }

        private TextureRegion[] getTexturesRegionsFromPos(Pos... positions) {
            final TextureRegion[] textureRegions = new TextureRegion[positions.length];
            int index = 0;
            for (final var pos : positions) {
                textureRegions[index] = animationMoveCharacter.frames[pos.x()][pos.y()];
                index++;
            }
            return textureRegions;
        }

        private Animation<TextureRegion> getAnimationFromPos(Pos... positions) {
            return new Animation<>(animationMoveCharacter.frameDuration, getTexturesRegionsFromPos(positions));
        }

        public Builder withAnimationIdleLeft(Pos... positions) {
            animationMoveCharacter.animationIdleLeft = getAnimationFromPos(positions);
            return this;
        }

        public Builder withAnimationIdleUp(Pos... positions) {
            animationMoveCharacter.animationIdleUp = getAnimationFromPos(positions);
            return this;
        }

        public Builder withAnimationIdleBottom(Pos... positions) {
            animationMoveCharacter.animationIdleBottom = getAnimationFromPos(positions);
            return this;
        }


        public Builder withAnimationIdleRight(Pos... positions) {
            animationMoveCharacter.animationIdleRight = getAnimationFromPos(positions);
            return this;
        }

    }

}
