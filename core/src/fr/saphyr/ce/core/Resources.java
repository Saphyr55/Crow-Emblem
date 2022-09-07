package fr.saphyr.ce.core;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.maps.Maps;

import java.util.ArrayList;
import java.util.List;

public final class Resources {



    private static final AssetManager handle = new AssetManager();
    static {

    }

    public static void load() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        handle.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        handle.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = "fonts/CinzelDecorative-Black.ttf";
        mySmallFont.fontParameters.size = 10;

        Textures.load("textures");
        //load("fonts", BitmapFont.class);
        handle.load("fonts/CinzelDecorative-Black.ttf", BitmapFont.class, mySmallFont);
        load("sounds", Sound.class);
        Maps.load("maps");
        while (!handle.update()) { }
        handle.finishLoading();
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
            handle.load(s, tClass, parameter);
            Logger.info("Loader : " + s);
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
