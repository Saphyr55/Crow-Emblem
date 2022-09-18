package fr.saphyr.ce.entities;

import fr.saphyr.ce.area.MoveAreaAttribute;
import fr.saphyr.ce.worlds.WorldPos;

public abstract class Character extends Entity {

    public Character(WorldPos worldPos, int[] tileNotExplorable, MoveAreaAttribute moveAreaAttribute) {
        super(worldPos, tileNotExplorable, moveAreaAttribute);
    }

}
