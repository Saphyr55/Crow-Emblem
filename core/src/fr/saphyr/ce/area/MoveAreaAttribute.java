package fr.saphyr.ce.area;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.google.gson.Gson;
import fr.saphyr.ce.CEFiles;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.register.Registry;
import fr.saphyr.ce.core.register.Resources;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public final class MoveAreaAttribute {

    private Array<AreaAttribute> areaAttributes;
    private final int[][] pattern;

    public MoveAreaAttribute(Array<AreaAttribute> areaAttributes, int[][] pattern) {
        this.areaAttributes = areaAttributes;
        this.pattern = pattern;
    }

    public Array<AreaAttribute> areaAttributes() {
        return areaAttributes;
    }

    public int[][] pattern() {
        return pattern;
    }

    public void setAreaAttributes(Array<AreaAttribute> areaAttributes) {
        this.areaAttributes = areaAttributes;
    }
}
