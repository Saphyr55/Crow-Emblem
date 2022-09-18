package fr.saphyr.ce.area;

import fr.saphyr.ce.core.register.Resources;

public class MoveAreaAttributes {

    public static MoveAreaAttribute DEFAULT;
    public static MoveAreaAttribute LARGE;
    public static void registers() {
        DEFAULT = get("data/areas/default.json");
        LARGE = get("data/areas/large.json");
    }

    private static MoveAreaAttribute get(String filename) {
        return Resources.MANAGER.get(filename, MoveAreaAttribute.class);
    }
}
