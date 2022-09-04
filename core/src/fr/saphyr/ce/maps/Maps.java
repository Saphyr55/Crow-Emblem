package fr.saphyr.ce.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ArrayMap;

public class Maps {

    public static final int UNIT_SCALE = 32;

    private static final ArrayMap<String, Map> maps = new ArrayMap<>();

    public static Map get(String fileName) {
        if (!maps.containsKey(fileName))
            maps.put(fileName, new Map(new TmxMapLoader().load(fileName), UNIT_SCALE));
        return maps.get(fileName);
    }

}
