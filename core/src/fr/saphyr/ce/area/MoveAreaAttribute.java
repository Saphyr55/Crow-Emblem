package fr.saphyr.ce.area;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.google.gson.Gson;
import fr.saphyr.ce.CEFiles;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.maps.Maps;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record MoveAreaAttribute(Array<AreaAttribute> areaAttributes, int[][] pattern) {

    private static final ArrayMap<String, MoveAreaAttribute> MOVE_AREA_ATTRIBUTE_ENTRIES = new ArrayMap<>();

    public static MoveAreaAttribute get(String id) {
        return MOVE_AREA_ATTRIBUTE_ENTRIES.get(id);
    }

    public static void send(String id, MoveAreaAttribute attribute) {
        if (!MOVE_AREA_ATTRIBUTE_ENTRIES.containsKey(id))
            MOVE_AREA_ATTRIBUTE_ENTRIES.put(id, attribute);
        else Logger.warning(MoveAreaAttribute.class + " id=" + id + " is already load");
    }

    public static void load(String module) {
        CEFiles.foundInternal(module).forEach(MoveAreaAttribute::loadFile);
    }

    public static void loadFile(String filepath) {
        try {
            final var gson =  new Gson();
            final Reader reader = Files.newBufferedReader(Paths.get(Gdx.files.internal(filepath).path()));
            final Map<?, ?> map = gson.fromJson(reader, Map.class);
            reader.close();
            final var keys = (Map<?, ?>) map.get("keys");
            final var listAreaAttributes = new Array<AreaAttribute>();
            keys.forEach((key, value) -> {
                final var values = (Map<?, ?>) value;
                listAreaAttributes.add(new AreaAttribute(
                        Integer.parseInt((String) key),
                        (String) values.get("texture"),
                        (boolean)values.get("explorable")));
            });
            MoveAreaAttribute.send((String) map.get("id"), new MoveAreaAttribute(listAreaAttributes,
                    ((ArrayList<ArrayList<Double>>) map.get("pattern")).stream().map(doubles -> doubles
                    .parallelStream().mapToInt(Double::intValue).boxed().toList()
                    .stream().mapToInt(Integer::intValue).toArray()).toArray(int[][]::new)));
            Logger.info("Loader : " + filepath);
        } catch (IOException e) {
            Logger.error("Impossible to found the resource " + filepath);
            throw new RuntimeException(e);
        }
    }
}
