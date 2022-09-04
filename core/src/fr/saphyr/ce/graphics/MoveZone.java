package fr.saphyr.ce.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.worlds.World;

import java.util.Optional;

public class MoveZone extends Array<Array<Optional<MoveZone.Zone>>> implements Drawable {

    private final World world;
    private final Array<TiledMapTile> tilesNotExplorable;

    public MoveZone(World world, Array<TiledMapTile> tilesNotExplorable) {
        this.world = world;
        this.tilesNotExplorable = tilesNotExplorable;
    }

    @Override
    public void draw(Batch batch) {
        iterator().forEachRemaining(optionals -> optionals.iterator().forEachRemaining(
            optional -> optional.ifPresent(zone -> {
                zone.texture = Textures.get("red_move_zone.png");
                zone.isExplorable = true;
                replaceZone(zone);
                batch.draw(zone.texture, zone.pos.x, zone.pos.y, 1, 1);
            })
        ));
    }

    private void replaceZone(Zone zone) {
        for(var tileNotExplorable : tilesNotExplorable) {
            if (world.getMap().getTileFrom(zone.pos) != null) {
                if (tileNotExplorable.getId() == world.getMap().getTileFrom(zone.pos).getId()) {
                    zone.texture = Textures.get("blue_move_zone.png");
                    zone.isExplorable = false;
                    break;
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

    public static class Zone
    {
        private Vector2 pos;
        private Texture texture;
        private boolean isExplorable;

        public Zone(Vector2 pos) {
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
