package fr.saphyr.ce.graphic;

import com.badlogic.gdx.graphics.Texture;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.register.Resources;

public final class Textures {

    public static void load(String module) {
        Resources.load(module, Texture.class);
    }

    public static Texture getNotLoaded(String name) {
        return new Texture(name);
    }

    public static Texture get(String id) {
        try {
            return Resources.get(id, Texture.class);
        }catch (Exception e) {
            Logger.error("Impossible to found the resource " + id, e);
        }
        return null;
    }

}
