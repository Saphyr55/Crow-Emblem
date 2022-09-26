package fr.saphyr.ce.world.area;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.area.cell.WorldCell;

import java.util.Optional;

public class WorldArea extends AbstractArea<WorldCell> {

    public WorldArea(IWorld world) {
        super(world);
        setupHandleFromWorld();
    }

    private void setupHandleFromWorld() {
        for (int i = 0; i < world.getMap().getWidth(); i++) {
            handle.add(new Array<>());
            for (int j = 0; j < world.getMap().getHeight(); j++) {
                handle.get(i).add(Optional.of(new WorldCell(new Vector3(i,j,0), this)));
            }
        }
    }

}
