package fr.saphyr.ce.graphics;

import com.badlogic.gdx.graphics.Texture;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Resources;

public final class Textures {

    public static void load(String moduleName) {
        Resources.load(moduleName, Texture.class);
    }

    public static Texture get(String id) {
        try {
            return Resources.get(id, Texture.class);
        }catch (Exception e) {
            Logger.error("Impossible to found the resource " + id);
            throw new RuntimeException(e);
        }
    }

}
