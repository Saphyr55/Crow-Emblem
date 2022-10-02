package fr.saphyr.ce.scene;

import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.characters.Character;

public final class CombatScene extends Scene {

    private final Character assailant;
    private final Character defender;

    public CombatScene(Character assailant, Character defender) {
        this.assailant = assailant;
        this.defender = defender;
        Logger.debug("Combat scene setup");
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(Renderer renderer) {

    }
}
