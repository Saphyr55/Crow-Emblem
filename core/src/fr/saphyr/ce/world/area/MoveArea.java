package fr.saphyr.ce.world.area;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.world.area.ai.AreaGraph;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.graphic.Drawable;
import fr.saphyr.ce.world.map.Map;

import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class MoveArea implements Drawable {

    private final Entity entity;
    private final Array<Array<Optional<IArea>>> handle;
    private final Array<TiledMapTile> tilesNotExplorable;
    private boolean isOpen;
    private final Array<IArea> areasMaskNotAccessible;
    private final AreaGraph areaGraph;
    private IArea areaWithEntity;

    public MoveArea(Entity entity) {
        this.entity = entity;
        this.tilesNotExplorable = entity.getTilesNotExplorable();
        this.handle = new Array<>();
        this.isOpen = false;
        this.areasMaskNotAccessible = new Array<>();
        this.areaGraph = new AreaGraph();
    }

    public void connect() {
        handle.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(areaGraph::addArea)));
        handle.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
            if (area.isAccessible() && area.isExplorable()) {
                area.getAroundArea().forEach(optional1 -> optional1.ifPresent(area1 -> {
                    if (area1.isAccessible() && area1.isExplorable())
                        areaGraph.connectAreas(area, area1);
                }));
            }
        })));
    }

    @Override
    public void draw(Renderer renderer) {
        handle.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
            if (area.getTexture() != null && (!area.isExplorable() || area.isAccessible()) )
                renderer.draw(area.getTexture(), area.getPos().x, area.getPos().y, 1, 1);
            else if (area.isExplorable()) {
                area.setTexture(Area.RED_AREA_TEXTURE);
                renderer.draw(area.getTexture(), area.getPos().x, area.getPos().y, 1, 1);
            }
        })));
    }

    public Optional<IArea> getAreaWithPos(final int x, final int y) {
        final var optional = new AtomicReference<Optional<IArea>>();
        optional.set(Optional.empty());
        if (isOpen) {
            for (int i = 0; i < handle.size; i++) {
                for (int j = 0; j < handle.get(i).size; j++) {
                    final Optional<IArea> finalOptional = handle.get(i).get(j);
                    finalOptional.ifPresent(area -> {
                        if(area.getPos().x == x && area.getPos().y == y){
                            optional.set(finalOptional);
                        }
                    });
                }
            }
        }
        return optional.get();
    }

    public Optional<IArea> getAreaWithPos(final Vector3 pos) {
        return getAreaWithPos((int) pos.x, (int) pos.y);
    }

    public IArea getAreaWithMainEntity() {
        return areaWithEntity;
    }

    public void updateAreaEntity() {
        handle.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
            if (area.getPos().equals(entity.getWorldPos().getPos()))
                areaWithEntity = area;
        })));
    }

    public void mask(Consumer<IArea> mask) {
        handle.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(mask)));
    }

    public void maskTileNotAccessible(IArea areaWithEntity) {
        final Array<Optional<IArea>> areas = areaWithEntity.getAroundArea();
        areasMaskNotAccessible.add(areaWithEntity);
        areas.forEach(optional -> optional.ifPresent(area -> {
            if (area.isExplorable() && !areasMaskNotAccessible.contains(area, true)) {
                area.setAccessible(true);
                maskTileNotAccessible(area);
            }
        }));
    }

    public void maskSoloTile(IArea area) {
        final Array<Optional<IArea>> areas = area.getAroundArea();
        final var index = new AtomicInteger(0);
        final var size = new AtomicInteger(0);
        areas.forEach(optional -> optional.ifPresent(areaAround -> {
            if (!areaAround.isAccessible() && areaAround.isExplorable())
                index.getAndIncrement();
            size.getAndIncrement();
        }));
        if (index.get() == size.get())
            area.setTexture(null);
    }

    public void maskAreaIfNotExplorable(IArea area) {
        final Map map = entity.getWorldPos().getWorld().getMap();
        for(var tileNotExplorable : tilesNotExplorable) {
            if (map.getTileFrom(area.getPos()) != null) {
                if (tileNotExplorable.getId() == map.getTileFrom(area.getPos()).getId()) {
                    area.setTexture(Area.RED_AREA_TEXTURE);
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

    public MoveArea personalize() {
        // order of mask is important
        updateAreaEntity();
        mask(this::maskAreaIfNotExplorable);
        maskTileNotAccessible(getAreaWithMainEntity());
        mask(this::maskSoloTile);
        connect();
        return this;
    }

    public Array<Array<Optional<IArea>>> getHandle() {
        return handle;
    }
}

