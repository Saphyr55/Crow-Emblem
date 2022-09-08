package fr.saphyr.ce.core;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.graphics.fonts.Fonts;
import fr.saphyr.ce.maps.Maps;

public final class Resources {

    public static final AssetManager manager = new AssetManager();

    public static void load() {
        Textures.load("textures");
        Fonts.load("fonts");
        Maps.load("maps");
    }

    public static <T> T get(String name, Class<T> tClass) {
        return manager.get(name, tClass);
    }

    public static <T> Array<T> getAll(Class<T> tClass) {
        Array<T> list = new Array<>();
        return manager.getAll(tClass, list);
    }

    public static <T> void load(String moduleFolder, Class<T> tClass, AssetLoaderParameters<T> parameter) {
        CEFiles.foundInternal(moduleFolder).forEach(s -> {
            if (!manager.contains(moduleFolder, tClass)) {
                manager.load(s, tClass, parameter);
                Logger.info("Loader : " + s);
            }
        });
    }

    public static <T> void load(String moduleFolder, Class<T> tClass) {
        load(moduleFolder, tClass, null);
    }


    public static AssetManager getManager() {
        return manager;
    }

    public static void dispose() {
        manager.dispose();
    }

}
