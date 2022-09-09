package fr.saphyr.ce.area;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.area.ai.AreaGraph;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.graphics.Drawable;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class MoveArea extends Array<Array<Optional<Area>>> implements Drawable {

    private final Entity entity;
    private final Array<TiledMapTile> tilesNotExplorable;
    private boolean isOpen;
    private final Array<Area> areasMaskNotAccessible;
    private final AreaGraph areaGraph;

    public MoveArea(Entity entity) {
        this.entity = entity;
        this.tilesNotExplorable = entity.getTilesNotExplorable();
        this.isOpen = false;
        this.areasMaskNotAccessible = new Array<>();
        this.areaGraph = new AreaGraph();
    }

    public void connect() {
        forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
            if (area.isMovable())
                areaGraph.addArea(area);
        })));
        AtomicReference<Area> oldArea = new AtomicReference<>();
        forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
            if (area.isMovable()) {
                if (oldArea.get() != null)
                    oldArea.set(area);
                else{
                    oldArea.set(area);
                    areaGraph.connectAreas(oldArea.get(), area);
                }
            }
        })));
    }

    @Override
    public void draw(Renderer renderer) {
        iterator().forEachRemaining(optionals -> optionals.iterator().forEachRemaining(
            optional -> optional.ifPresent(area -> {
                if (area.isMovable())
                    renderer.draw(area.getTexture(), area.getPos().x, area.getPos().y, 1, 1);
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
            areas.get(i).ifPresent(area -> {
                if (area.isExplorable() && !areasMaskNotAccessible.contains(area, true)) {
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
            if (!areaAround.isAccessible() && areaAround.isExplorable())
                index.getAndIncrement();
        }));
        if (index.get() == areas.size) {
            area.setTexture(null);
        }
    }

    public void maskAreaIfNotExplorable(Area area) {
        final var map = entity.getWorld().getMap();
        for(var tileNotExplorable : tilesNotExplorable) {
            if (map.getTileFrom(area.getPos()) != null) {
                if (tileNotExplorable.getId() == map.getTileFrom(area.getPos()).getId()) {
                    area.setTexture(Area.notExplorableAreaTexture);
                    area.setExplorable(false);
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

    public AreaGraph getAreaGraph() {
        return areaGraph;
    }
}
