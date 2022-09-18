package fr.saphyr.ce.world.map;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ArrayMap;
import fr.saphyr.ce.CEFiles;
import fr.saphyr.ce.core.Logger;

public final class Maps {

    public static final int UNIT_SCALE = 32;
    public static final ArrayMap<String, Map> maps = new ArrayMap<>();

    public static void load(String module) {
        CEFiles.foundInternal(module).forEach(Maps::loadFile);
    }

    public static void loadFile(String filename) {
        if (!maps.containsKey(filename)) {
            maps.put(filename, new Map(new TmxMapLoader().load(filename), UNIT_SCALE));
            Logger.info("Loader : " + filename);
        }
    }


    public static Map get(String filename) {
        try {
            return maps.get(filename);
        }catch (Exception e) {
            Logger.error("Impossible to found the resource " + filename);
            throw new RuntimeException();
        }
    }

}
