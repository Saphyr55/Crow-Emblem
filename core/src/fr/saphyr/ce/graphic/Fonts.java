package fr.saphyr.ce.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import fr.saphyr.ce.CEFiles;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.register.Resources;

public final class Fonts {

    public static void load(String module) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        Resources.getManager().setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        Resources.getManager().setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        CEFiles.foundInternal(module).forEach(s -> {
            FreetypeFontLoader.FreeTypeFontLoaderParameter font = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
            font.fontFileName = s;
            font.fontParameters.size = 50;
            Resources.MANAGER.load(s, BitmapFont.class, font);
            Logger.info("Loader : " + s);
        });
    }

    public static BitmapFont createTTF(String name, int size) {
        FreeTypeFontGenerator freeTypeFontGenerator=new FreeTypeFontGenerator(Gdx.files.internal(name));
        FreeTypeFontGenerator.FreeTypeFontParameter ftp=new FreeTypeFontGenerator.FreeTypeFontParameter();
        ftp.size=size;
        return freeTypeFontGenerator.generateFont(ftp);
    }
    
    public static BitmapFont get(String name) {
        return Resources.get(name, BitmapFont.class);
    }

}
