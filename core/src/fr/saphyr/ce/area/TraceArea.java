package fr.saphyr.ce.area;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.graphics.Textures;

public class TraceArea {

    private Array<Area> trace;
    private final MoveArea moveArea;
    private static final Texture textureTrace = Textures.get("textures/green_move_zone");

    public TraceArea(MoveArea moveArea) {
        this.moveArea = moveArea;

    }


    public MoveArea getMoveArea() {
        return moveArea;
    }
}

