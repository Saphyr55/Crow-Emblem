package fr.saphyr.ce.world.area.cell;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.core.Updatable;
import fr.saphyr.ce.world.area.WorldArea;

public class WorldCell extends AbstractCell<WorldCell> {

    private final TiledMapTile tile;

    public WorldCell(Vector3 pos, WorldArea area) {
        super(pos, area);
        tile = area.getWorld().getMap().getTileFrom(pos);
        setContentEntity();
    }

    public TiledMapTile getTile() {
        return tile;
    }

}
