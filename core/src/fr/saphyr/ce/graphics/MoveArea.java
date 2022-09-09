package fr.saphyr.ce.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Entity;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MoveArea extends Array<Array<Optional<MoveArea.Area>>> implements Drawable {

    private final Entity entity;
    private final Array<TiledMapTile> tilesNotExplorable;
    private boolean isOpen;
    private final Array<Area> areasMaskNotAccessible;

    public MoveArea(Entity entity) {
        this.entity = entity;
        this.tilesNotExplorable = entity.getTilesNotExplorable();
        this.isOpen = false;
        this.areasMaskNotAccessible = new Array<>();
    }

    @Override
    public void draw(Renderer renderer) {

        iterator().forEachRemaining(optionals -> optionals.iterator().forEachRemaining(
            optional -> optional.ifPresent(area -> {
                if (area.isAccessible || !area.isExplorable)
                    renderer.draw(area.texture, area.pos.x, area.pos.y, 1, 1);
            })
        ));
    }

    public Area getAreaWithEntity() {
        final var areaWithEntity = new AtomicReference<Area>();
        forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
            if (area.getPos().equals(new Vector2(entity.getPos().x, entity.getPos().y)))
                areaWithEntity.set(area);
        })));
        return areaWithEntity.get();
    }

    public void maskTileNotExplorable() {
        forEach(optionals -> optionals.forEach(optional ->
                optional.ifPresent(this::changeAreaIfNotExplorable)));
    }

    public void maskTileNotAccessible(Area areaWithEntity) {
        final var areas = areaWithEntity.getAroundArea(this);
        areasMaskNotAccessible.add(areaWithEntity);
        for (int i = 0; i < areas.size; i++) {
            final var area = areas.get(i);
            if (area.isExplorable && !areasMaskNotAccessible.contains(area, true)) {
                area.setAccessible(true);
                maskTileNotAccessible(area);
            }
        }
    }

    private void changeAreaIfNotExplorable(MoveArea.Area area) {
        final var map = entity.getWorld().getMap();
        for(var tileNotExplorable : tilesNotExplorable) {
            if (map.getTileFrom(area.pos) != null) {
                if (tileNotExplorable.getId() == map.getTileFrom(area.pos).getId()) {
                    area.texture = Area.notExplorableAreaTexture;
                    area.isExplorable = false;
                }
            }
        }
    }

    public Entity getEntity() {
        return entity;
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
        private boolean isAccessible;
        public static final Texture explorableAreaTexture = Textures.get("textures/blue_move_zone.png");
        public static final Texture notExplorableAreaTexture = Textures.get("textures/red_move_zone.png");

        public Area(Vector2 pos) {
            this.texture = explorableAreaTexture;
            this.isExplorable = true;
            this.pos = pos;
            this.isAccessible = false;
        }

        public void setEntityAccessible(final Entity entity) {
            if (getPos().equals(new Vector2(entity.getPos().x, entity.getPos().y))) {
                setAccessible(true);
            }
        }

        public Array<Area> getAroundArea(MoveArea moveArea) {
            final var areas = new Array<Area>();
            final var checkPos = new Array<Vector2>();
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
            return areas;
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


        public boolean isAccessible() {
            return isAccessible;
        }

        public void setAccessible(boolean accessible) {
            isAccessible = accessible;
        }

        @Override
        public String toString() {
            return "IsAccessible="+isAccessible+ " IsExplorable="+isExplorable +" Pos="+pos;
        }
    }
}
