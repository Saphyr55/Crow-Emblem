package fr.saphyr.ce.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.Renderer;
import fr.saphyr.ce.core.Resources;
import fr.saphyr.ce.worlds.World;

import java.util.Optional;

public class MoveArea extends Array<Array<Optional<MoveArea.Area>>> implements Drawable {

    private final World world;
    private final Array<TiledMapTile> tilesNotExplorable;

    public MoveArea(World world, Array<TiledMapTile> tilesNotExplorable) {
        this.world = world;
        this.tilesNotExplorable = tilesNotExplorable;
    }

    @Override
    public void draw(Renderer renderer) {
        iterator().forEachRemaining(optionals -> optionals.iterator().forEachRemaining(
            optional -> optional.ifPresent(area -> {
                area.texture = Resources.get("textures/red_move_zone.png", Texture.class);
                area.isExplorable = true;
                explorableZone(area);
                renderer.draw(area.texture, area.pos.x, area.pos.y, 1, 1);
            })
        ));
    }

    private void explorableZone(MoveArea.Area area) {
        for(var tileNotExplorable : tilesNotExplorable) {
            if (world.getMap().getTileFrom(area.pos) != null) {
                if (tileNotExplorable.getId() == world.getMap().getTileFrom(area.pos).getId()) {
                    area.texture = Resources.get("textures/blue_move_zone.png", Texture.class);
                    area.isExplorable = false;
                }
            }
        }
    }

    public World getWorld() {
        return world;
    }

    public Array<TiledMapTile> getTilesNotExplorable() {
        return tilesNotExplorable;
    }

    public static class Area
    {
        private Vector2 pos;
        private Texture texture;
        private boolean isExplorable;

        public Area(Vector2 pos) {
            this.pos = pos;
        }

        public Vector2 getPos() {
            return pos;
        }

        public void setPos(Vector2 pos) {
            this.pos = pos;
        }

        public void setPos(float x, float y) {
            this.pos = new Vector2(x, y);
        }

        public Texture getTexture() {
            return texture;
        }

        public void setTexture(Texture texture) {
            this.texture = texture;
        }

        public boolean isExplorable() {
            return isExplorable;
        }

        public void setExplorable(boolean explorable) {
            isExplorable = explorable;
        }
    }

}
