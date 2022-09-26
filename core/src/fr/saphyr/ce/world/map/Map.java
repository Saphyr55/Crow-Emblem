package fr.saphyr.ce.world.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class Map implements Disposable {

    private TiledMap handle;
    private final int unitScale;
    private final int height;
    private final int width;

    public Map(TiledMap handle, int unitScale) {
        this.handle = handle;
        this.unitScale = unitScale;
        this.width = this.handle.getProperties().get("width", Integer.class);
        this.height = this.handle.getProperties().get("height", Integer.class);
    }
    
    public TiledMapTile getTileFrom(int x, int y) {
        for(int i = 0; i < getHandle().getLayers().size(); i++) {
            int lastId = getHandle().getLayers().size() - i - 1;
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) getHandle().getLayers().get(lastId);
            TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
            if (cell != null) return cell.getTile();
        }
        return null;
    }

    public TiledMapTile getTileFrom(Vector3 pos) {
        return getTileFrom((int)pos.x, (int) pos.y);
    }

    @Override
    public void dispose() {
        handle.dispose();
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

