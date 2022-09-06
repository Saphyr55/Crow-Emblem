package fr.saphyr.ce.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import fr.saphyr.ce.CEFiles;
import fr.saphyr.ce.maps.Map;
import fr.saphyr.ce.maps.Maps;

public class Resources {

    private static final AssetManager handle = new AssetManager();

    public static void load() {
        load("textures", Texture.class);
        load("sounds", Sound.class);
        Maps.load("maps");
        handle.finishLoading();
    }

    public static <T> T get(String name, Class<T> tClass) {
        return handle.get(name, tClass);
    }

    private static <T> void load(String moduleFolder, Class<T> tClass) {
        CEFiles.foundInternal(moduleFolder).forEach(s -> {
            handle.load(s, tClass);
            Logger.info("Loader : " + s);
        });
    }

    public static void dispose() {
        handle.dispose();
    }

}
