package fr.saphyr.ce.world.area;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.world.cell.AbstractCell;
import fr.saphyr.ce.world.cell.ICell;
import fr.saphyr.ce.world.cell.MoveCell;
import fr.saphyr.ce.world.cell.ai.AreaGraph;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.graphic.Drawable;
import fr.saphyr.ce.world.map.Map;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class MoveArea extends AbstractArea<MoveCell> implements Drawable {

    private final Entity entity;
    private final Array<TiledMapTile> tilesNotExplorable;
    private boolean isOpen;
    private final Array<MoveCell> cellsMaskNotAccessible;
    private final AreaGraph areaGraph;
    private MoveCell cellWithEntity;

    public MoveArea(Entity entity) {
        super(entity.getWorld());
        this.entity = entity;
        this.tilesNotExplorable = entity.getTilesNotExplorable();
        this.isOpen = false;
        this.cellsMaskNotAccessible = new Array<>();
        this.areaGraph = new AreaGraph();
    }

    public void connect() {
        zone.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(areaGraph::addArea)));
        zone.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
            final fr.saphyr.ce.world.cell.MoveCell moveCell1 = (fr.saphyr.ce.world.cell.MoveCell) area;
            if (moveCell1.isAccessible() && moveCell1.isExplorable()) {
                area.getAroundCell().forEach(optional1 -> optional1.ifPresent(area1 -> {
                    final fr.saphyr.ce.world.cell.MoveCell moveCell = (fr.saphyr.ce.world.cell.MoveCell) area1;
                    if (moveCell.isAccessible() && moveCell.isExplorable())
                        areaGraph.connectAreas(moveCell1, moveCell);
                }));
            }
        })));
    }

    @Override
    public void draw(Renderer renderer) {
        zone.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(cell -> {
            if (cell.getTexture() != null && (!cell.isExplorable() || cell.isAccessible()) )
                renderer.draw(cell.getTexture(), cell.getPos().x, cell.getPos().y, 1, 1);
            else if (cell.isExplorable()) {
                cell.setTexture(AbstractCell.RED_AREA_TEXTURE);
                renderer.draw(cell.getTexture(), cell.getPos().x, cell.getPos().y, 1, 1);
            }
        })));
    }

    public MoveCell getCellWithMainEntity() {
        return cellWithEntity;
    }

    public void updateCellEntity() {
        zone.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(cell -> {
            if (cell.getPos().equals(entity.getWorldPos().getPos()))
                cellWithEntity = cell;
        })));
    }

    public void mask(Consumer<MoveCell> mask) {
        zone.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(mask)));
    }

    public void maskTileNotAccessible(MoveCell cellWithEntity) {
        final Array<Optional<ICell>> areas = cellWithEntity.getAroundCell();
        cellsMaskNotAccessible.add(cellWithEntity);
        areas.forEach(optional -> optional.ifPresent(area -> {
            final MoveCell cell = (MoveCell) area;
            if (cell.isExplorable() && !cellsMaskNotAccessible.contains(cell, true)) {
                cell.setAccessible(true);
                maskTileNotAccessible(cell);
            }
        }));
    }

    public void maskSoloTile(MoveCell moveCell) {
        final Array<Optional<ICell>> areas = moveCell.getAroundCell();
        final var index = new AtomicInteger(0);
        final var size = new AtomicInteger(0);
        areas.forEach(optional -> optional.ifPresent(cell -> {
            final MoveCell moveCellIn = (MoveCell) cell;
            if (!moveCellIn.isAccessible() && moveCellIn.isExplorable())
                index.getAndIncrement();
            size.getAndIncrement();
        }));
        if (index.get() == size.get())
            moveCell.setTexture(null);
    }

    public void maskAreaIfNotExplorable(MoveCell moveCell) {
        final Map map = entity.getWorldPos().getWorld().getMap();
        for(var tileNotExplorable : tilesNotExplorable) {
            if (map.getTileFrom(moveCell.getPos()) != null) {
                if (tileNotExplorable.getId() == map.getTileFrom(moveCell.getPos()).getId()) {
                    moveCell.setTexture(AbstractCell.RED_AREA_TEXTURE);
                    moveCell.setExplorable(false);
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
        updateCellEntity();
        mask(this::maskAreaIfNotExplorable);
        maskTileNotAccessible(getCellWithMainEntity());
        mask(this::maskSoloTile);
        connect();
        return this;
    }
}
