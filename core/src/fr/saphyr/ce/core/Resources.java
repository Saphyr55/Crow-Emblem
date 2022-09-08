package fr.saphyr.ce.core;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.graphics.fonts.Fonts;
import fr.saphyr.ce.maps.Maps;

public final class Resources {



    private static final AssetManager handle = new AssetManager();
    static {

    }

    public static void load() {
        Textures.load("textures");
        Fonts.load("fonts");
        Maps.load("maps");
    }

    public static <T> T get(String name, Class<T> tClass) {
        return handle.get(name, tClass);
    }

    public static <T> Array<T> getAll(Class<T> tClass) {
        Array<T> list = new Array<>();
        return handle.getAll(tClass, list);
    }

    public static <T> void load(String moduleFolder, Class<T> tClass, AssetLoaderParameters<T> parameter) {
        CEFiles.foundInternal(moduleFolder).forEach(s -> {
            if (!handle.contains(moduleFolder, tClass)) {
                handle.load(s, tClass, parameter);
                Logger.info("Loader : " + s);
            }
        });
    }

    public static <T> void load(String moduleFolder, Class<T> tClass) {
        load(moduleFolder, tClass, null);
    }


    public static AssetManager getHandle() {
        return handle;
    }

    public static void dispose() {
        handle.dispose();
    }

}
