package fr.saphyr.ce.entities;

import com.badlogic.gdx.graphics.Texture;

public interface ICharacter {

    Texture getTexture();

    float getHitPoint();

    float getAttackDamage();

    void setHisPoint(float hisPoint);

    void setAttackDamage(float attackDamage);

}
