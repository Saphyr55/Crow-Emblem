package fr.saphyr.ce.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ArrayMap;

public final class Textures {

    private static final ArrayMap<String, Texture> textures = new ArrayMap<>();

    public static Texture get(String fileName) {
        if (!textures.containsKey(fileName))
            textures.put(fileName, new Texture(fileName));
        return textures.get(fileName);
    }

}
