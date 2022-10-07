package fr.saphyr.ce.entities;

import com.badlogic.gdx.graphics.Texture;
import fr.saphyr.ce.entities.characters.AnimationMoveCharacter;

public interface ICharacter {

    Texture getTexture();

    AnimationMoveCharacter getAnimationMoveCharacter();

    float getHitPoint();

    float getAttackDamage();

    void setHisPoint(float hisPoint);

    void setAttackDamage(float attackDamage);

}
