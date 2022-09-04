package fr.saphyr.ce.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Map implements Disposable {

    private TiledMap handle;
    private final int unitScale;

    public Map(TiledMap handle, int unitScale) {
        this.handle = handle;
        this.unitScale = unitScale;
    }

    public int getUnitScale() {
        return unitScale;
    }

    public TiledMap getHandle() {
        return handle;
    }

    public void setHandle(TiledMap handle) {
        this.handle = handle;
    }

    public TiledMapTile getTileFrom(Vector2 pos) {
        for(int i = 0; i < getHandle().getLayers().size(); i++) {
            int lastId = getHandle().getLayers().size() - i - 1;
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) getHandle().getLayers().get(lastId);
            TiledMapTileLayer.Cell cell = tileLayer.getCell((int) pos.x, (int) pos.y);
            if (cell != null) return cell.getTile();
        }
        return null;
    }

    @Override
    public void dispose() {
        getHandle().dispose();
    }
}
