package fr.saphyr.ce.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Entity;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

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
                if (area.texture != null && (area.isAccessible || !area.isExplorable))
                    renderer.draw(area.texture, area.pos.x, area.pos.y, 1, 1);
            })
        ));
    }

    public Optional<Area> getAreaWithPos(final int x, final int y) {
        final var optional = new AtomicReference<Optional<Area>>();
        optional.set(Optional.empty());
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < get(i).size; j++) {
                Optional<Area> finalOptional = get(i).get(j);
                finalOptional.ifPresent(area -> {
                    if(area.getPos().x == x && area.getPos().y ==y){
                        optional.set(finalOptional);
                    }
                });
            }
        }
        return optional.get();
    }

    public Area getAreaWithEntity() {
        final var areaWithEntity = new AtomicReference<Area>();
        forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
            if (area.getPos().equals(new Vector2(entity.getPos().x, entity.getPos().y)))
                areaWithEntity.set(area);
        })));
        return areaWithEntity.get();
    }

    public void mask(Consumer<Area> consumer) {
        forEach(optionals -> optionals.forEach(optional ->
                optional.ifPresent(consumer)));
    }

    public void maskTileNotAccessible(Area areaWithEntity) {
        final var areas = areaWithEntity.getAroundArea();
        areasMaskNotAccessible.add(areaWithEntity);
        for (int i = 0; i < areas.size; i++) {
            final var optional = areas.get(i);
            optional.ifPresent(area -> {
                if (area.isExplorable && !areasMaskNotAccessible.contains(area, true)) {
                    area.setAccessible(true);
                    maskTileNotAccessible(area);
                }
            });
        }
    }

    public void maskSoloTile(Area area) {
        var areas = area.getAroundArea();
        final var index = new AtomicInteger(0);
        areas.forEach(optional -> optional.ifPresent(areaAround -> {
            if (!areaAround.isAccessible && areaAround.isExplorable)
                index.getAndIncrement();
        }));
        if (index.get() == areas.size) {
            area.texture = null;
        }
    }

    public void maskAreaIfNotExplorable(Area area) {
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

    public static class Area {

        private MoveArea moveArea;
        private Vector2 pos;
        private Texture texture;
        private boolean isExplorable;
        private boolean isAccessible;
        public static final Texture explorableAreaTexture = Textures.get("textures/blue_move_zone.png");
        public static final Texture notExplorableAreaTexture = Textures.get("textures/red_move_zone.png");

        public Area(Vector2 pos, MoveArea moveArea) {
            this.texture = explorableAreaTexture;
            this.isExplorable = true;
            this.pos = pos;
            this.isAccessible = false;
            this.moveArea = moveArea;
        }

        public void setAreaEntityAccessible(final Entity entity) {
            if (getPos().equals(new Vector2(entity.getPos().x, entity.getPos().y))) {
                setAccessible(true);
            }
        }

        public Optional<Area> getArea(Direction direction) {
            return switch (direction) {
                case UP -> getAreaFromVector(new Vector2(pos.x, pos.y + 1));
                case RIGHT -> getAreaFromVector(new Vector2(pos.x + 1, pos.y));
                case LEFT -> getAreaFromVector(new Vector2(pos.x - 1, pos.y));
                case BOTTOM -> getAreaFromVector(new Vector2(pos.x, pos.y - 1));
                case TOP_LEFT -> getAreaFromVector(new Vector2(pos.x - 1, pos.y + 1));
                case TOP_RIGHT -> getAreaFromVector(new Vector2(pos.x + 1, pos.y + 1));
                case BOTTOM_LEFT -> getAreaFromVector(new Vector2(pos.x - 1, pos.y - 1));
                case BOTTOM_RIGHT -> getAreaFromVector(new Vector2(pos.x + 1, pos.y - 1));
            };
        }

        private Optional<Area> getAreaFromVector(Vector2 vector) {
            final AtomicReference<Optional<Area>> optional = new AtomicReference<>(Optional.empty());
            for (int i = 0; i < moveArea.size; i++) {
                for (int j = 0; j < moveArea.get(i).size; j++) {
                    moveArea.get(i).get(j).ifPresent(area -> {
                        if (vector.equals(area.pos))
                            optional.set(Optional.of(area));
                    });
                }
            }
            return optional.get();
        }

        public Array<Optional<Area>> getAroundArea() {
            final Array<Optional<Area>> areas = new Array<>();
            areas.add(
                    getArea(Direction.UP),
                    getArea(Direction.BOTTOM),
                    getArea(Direction.LEFT),
                    getArea(Direction.RIGHT)
            );
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
