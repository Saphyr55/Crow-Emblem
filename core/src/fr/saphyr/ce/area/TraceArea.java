package fr.saphyr.ce.area;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.graphics.Textures;

public class TraceArea implements CEObject {

    private static final Texture textureTrace = Textures.get("textures/green_move_zone.png");

    private Array<Area> trace;
    private final MoveArea moveArea;

    public TraceArea(MoveArea moveArea) {
        this.moveArea = moveArea;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(Renderer renderer) {

    }

    public void add(Area area) {
        if (!trace.contains(area, false))
            trace.add(area);
    }

    public MoveArea getMoveArea() {
        return moveArea;
    }

}

