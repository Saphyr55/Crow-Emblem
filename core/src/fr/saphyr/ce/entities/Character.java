package fr.saphyr.ce.entities;

import fr.saphyr.ce.world.WorldPos;
import fr.saphyr.ce.world.area.MoveAreaAttribute;

public class Character extends Entity implements ICharacter {

    private float hitPoint;
    private float attackDamage;

    public Character(WorldPos worldPos, int[] tileNotExplorable, MoveAreaAttribute moveAreaAttribute) {
        super(worldPos, tileNotExplorable, moveAreaAttribute);
    }

    @Override
    public float getHitPoint() {
        return hitPoint;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public void setHisPoint(float hisPoint) {
        this.hitPoint = hisPoint;
    }

    @Override
    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }

}
