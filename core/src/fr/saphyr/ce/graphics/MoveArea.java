package fr.saphyr.ce.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.worlds.World;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class MoveArea extends Array<Array<Optional<MoveArea.Area>>> implements Drawable {

    private final World world;
    private final Array<TiledMapTile> tilesNotExplorable;

    private boolean isOpen;

    public MoveArea(World world, Array<TiledMapTile> tilesNotExplorable) {
        this.world = world;
        this.tilesNotExplorable = tilesNotExplorable;
        this.isOpen = false;
    }

    public MoveArea(MoveArea moveArea) {
        this.world = moveArea.world;
        this.tilesNotExplorable = moveArea.tilesNotExplorable;
        this.isOpen = false;
    }

    @Override
    public void draw(Renderer renderer) {
        iterator().forEachRemaining(optionals -> optionals.iterator().forEachRemaining(
            optional -> optional.ifPresent(area -> {
                changeAreaIfNotExplorable(area);
                renderer.draw(area.texture, area.pos.x, area.pos.y, 1, 1);
            })
        ));
    }

    private void changeAreaIfNotExplorable(MoveArea.Area area) {
        if (area.isContact(this)) {
            area.texture = Area.notExplorableAreaTexture;
            area.isExplorable = false;
            return;
        }
        for(var tileNotExplorable : tilesNotExplorable) {
            if (world.getMap().getTileFrom(area.pos) != null) {
                if (tileNotExplorable.getId() == world.getMap().getTileFrom(area.pos).getId()) {
                    area.texture = Area.notExplorableAreaTexture;
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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public static class Area
    {
        private Vector2 pos;
        private Texture texture;
        private boolean isExplorable;
        public static final Texture explorableAreaTexture = Textures.get("textures/blue_move_zone.png");
        public static final Texture notExplorableAreaTexture = Textures.get("textures/red_move_zone.png");

        public Area(Vector2 pos) {
            this.texture = explorableAreaTexture;
            this.isExplorable = true;
            this.pos = pos;
        }

        public boolean isContact(MoveArea moveArea) {
            final Array<Area> areas = new Array<>();
            final Array<Vector2> checkPos = new Array<>();
            final var bottomPos = new Vector2(pos.x, pos.y - 1);
            final var upPos = new Vector2(pos.x, pos.y + 1);
            final var leftPos = new Vector2(pos.x - 1, pos.y);
            final var rightPos = new Vector2(pos.x + 1, pos.y);
            checkPos.add(bottomPos, upPos, leftPos, rightPos);
            for (int i = 0; i < moveArea.size; i++) {
                for (int j = 0; j < moveArea.get(i).size; j++) {
                    moveArea.get(i).get(j).ifPresent(area -> checkPos.forEach(vector -> {
                        if (vector.equals(area.pos))
                            areas.add(area);
                    }));
                }
            }
            final AtomicInteger index = new AtomicInteger();
            areas.forEach(area -> { if (area.isExplorable) index.getAndIncrement();});
            return index.get() < 1;
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
