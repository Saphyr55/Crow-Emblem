package fr.saphyr.ce.entities;

import fr.saphyr.ce.worlds.WorldPos;

public abstract class Character extends Entity {

    public Character(WorldPos worldPos, int[] tileNotExplorable, int[][] moveAreaInt) {
        super(worldPos, tileNotExplorable, moveAreaInt);
    }

}
