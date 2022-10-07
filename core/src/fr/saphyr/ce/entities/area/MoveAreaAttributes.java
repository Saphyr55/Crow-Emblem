package fr.saphyr.ce.entities.area;

import fr.saphyr.ce.core.register.Resources;

public class MoveAreaAttributes {

    public static MoveAreaAttribute DEFAULT;
    public static MoveAreaAttribute LARGE;
    public static void registers() {
        DEFAULT = register("data/areas/default.json");
        LARGE = register("data/areas/large.json");
    }

    private static MoveAreaAttribute register(String filename) {
        return Resources.MANAGER.get(filename, MoveAreaAttribute.class);
    }
}
