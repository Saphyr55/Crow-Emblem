package fr.saphyr.ce.world.area.cell;

import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.core.Updatable;
import fr.saphyr.ce.world.area.WorldArea;

public class WorldCell extends AbstractCell<WorldCell> implements Updatable {

    public WorldCell(Vector3 pos, WorldArea area) {
        super(pos, area);
    }

    @Override
    public void update(float dt) {
        setContentEntity();
    }

}
