package fr.saphyr.ce.entities.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.entities.ICharacter;
import fr.saphyr.ce.world.WorldPos;
import fr.saphyr.ce.world.area.MoveAreaAttribute;

public abstract class Character extends Entity implements ICharacter {

    protected float hitPoint;
    protected float attackDamage;
    protected AnimationMoveCharacter animationMove;
    protected Texture texture;

    protected Character(WorldPos worldPos, int[] tileNotExplorable, MoveAreaAttribute moveAreaAttribute) {
        super(worldPos, tileNotExplorable, moveAreaAttribute);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        animationMove.update(dt);
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
        animationMove.render(renderer);
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
