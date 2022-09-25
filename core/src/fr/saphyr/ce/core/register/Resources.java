package fr.saphyr.ce.core.register;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.CEFiles;
import fr.saphyr.ce.world.area.MoveAreaAttribute;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.loader.MoveAreaAttributeLoader;
import fr.saphyr.ce.graphic.Textures;
import fr.saphyr.ce.graphic.Fonts;
import fr.saphyr.ce.world.map.Maps;

public final class Resources {

    public static final AssetManager MANAGER = new AssetManager();

    public static void load() {
        MANAGER.setLoader(MoveAreaAttribute.class, new MoveAreaAttributeLoader(new InternalFileHandleResolver()));
        Textures.load("textures");
        Fonts.load("fonts");
        Maps.load("maps");
        MoveAreaAttributeLoader.load("data/areas");
    }

    public static <T> T get(String name, Class<T> tClass) {
        return MANAGER.get(name, tClass);
    }

    public static <T> Array<T> getAll(Class<T> tClass) {
        Array<T> list = new Array<>();
        return MANAGER.getAll(tClass, list);
    }

    public static <T> void load(String moduleFolder, Class<T> tClass, AssetLoaderParameters<T> parameter) {
        CEFiles.foundInternal(moduleFolder).forEach(s -> {
            if (!MANAGER.contains(moduleFolder, tClass)) {
                MANAGER.load(s, tClass, parameter);
                Logger.info("Loader : " + s);
            }
        });
    }

    public static <T> void load(String moduleFolder, Class<T> tClass) {
        load(moduleFolder, tClass, null);
    }


    public static AssetManager getManager() {
        return MANAGER;
    }

    public static void dispose() {
        MANAGER.dispose();
    }

}
