package fr.saphyr.ce.entities.enemies;

import fr.saphyr.ce.core.Pos;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Enemy;
import fr.saphyr.ce.entities.characters.AnimationMoveCharacter;
import fr.saphyr.ce.graphic.Textures;
import fr.saphyr.ce.world.WorldPos;
import fr.saphyr.ce.entities.area.MoveAreaAttribute;

public final class Eirika extends Enemy {

    public Eirika(WorldPos worldPos, int[] tileNotExplorable, MoveAreaAttribute moveAreaAttribute) {
        super(worldPos, tileNotExplorable, moveAreaAttribute);
        texture = Textures.get("textures/entities/eirika_sword/Great Lord (F) Eirika Sword {IS}-walk.png");
        animationMove = AnimationMoveCharacter.Builder.of(this, 1, 15, 100 / 1000f)
                .withAnimationIdleUp(Pos.of(8, 0), Pos.of(9, 0), Pos.of(10, 0), Pos.of(11, 0))
                .withAnimationIdleBottom(Pos.of(4, 0), Pos.of(5, 0), Pos.of(6, 0), Pos.of(7, 0))
                .withAnimationIdleRight(Pos.of(0, 0),Pos.of(1, 0), Pos.of(2, 0), Pos.of(3, 0))
                .withAnimationIdleLeft(Pos.of(0, 0),Pos.of(1, 0), Pos.of(2, 0), Pos.of(3, 0))
                .build();
        animationMove.setCurrentAnimation(animationMove.getAnimationIdleBottom());
        animationMove.setCurrentFrame(Pos.of(1, 0));
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
