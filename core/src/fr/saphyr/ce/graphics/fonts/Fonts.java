package fr.saphyr.ce.graphics.fonts;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import fr.saphyr.ce.core.CEFiles;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Resources;

public class Fonts {

    public static void load(String module) {
        CEFiles.foundInternal(module).forEach(s -> {
            FreetypeFontLoader.FreeTypeFontLoaderParameter font = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
            font.fontFileName = s;
            font.fontParameters.size = 10;
            Resources.getHandle().load(s, BitmapFont.class, font);
            Logger.info("Loader : " + s);
        });
    }

    public static BitmapFont get(String name) {
        return Resources.get(name, BitmapFont.class);
    }

}
