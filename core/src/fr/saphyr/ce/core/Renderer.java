package fr.saphyr.ce.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import fr.saphyr.ce.worlds.World;

public class Renderer extends SpriteBatch {

    private MapRenderer mapRenderer;

    public Renderer() { }

    public Renderer(MapRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
    }

    public static Renderer orthogonal(World world) {
        return new Renderer(new OrthogonalTiledMapRenderer(
                world.getMap().getHandle(), 1f / world.getMap().getUnitScale()));
    }

    public MapRenderer getMapRenderer() {
        return mapRenderer;
    }

    public void setMapRenderer(MapRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
    }

}
