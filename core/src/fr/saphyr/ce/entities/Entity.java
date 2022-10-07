package fr.saphyr.ce.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.area.*;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.WorldPos;
import fr.saphyr.ce.entities.area.cell.MoveCell;
import fr.saphyr.ce.world.area.cell.WorldCell;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Entity implements IEntity {

    public static final float EPSILON = 0.09f;
    protected TraceCell traceCell;
    protected MoveArea moveArea;
    protected AttackArea attackArea;
    protected Array<TiledMapTile> tilesNotExplorable;
    protected WorldPos worldPos;
    protected Array<WorldPos> snapshotWorldPos;
    protected float stateTime = 0.0f;
    protected EntityState state;
    protected Direction direction;
    protected MoveAreaAttribute moveAreaAttribute;
    protected MoveCell moveCellPressed;
    protected WorldCell worldCell;
    protected boolean isSelected = false;

    protected Entity(WorldPos worldPos, int[] tileNotExplorable, MoveAreaAttribute moveAreaAttribute) {
        this.worldPos = worldPos;
        this.state = EntityState.WAIT;
        this.tilesNotExplorable = new Array<>();
        setTilesNotExplorableById(tileNotExplorable);
        setMoveArea(moveAreaAttribute);
        updateWorldCell();
        this.traceCell = new TraceCell(moveArea);
        this.attackArea = AttackAreas.getDefault(this);
        this.snapshotWorldPos = new Array<>();
    }

    public Entity() {
        this.state = EntityState.WAIT;
        this.tilesNotExplorable = new Array<>();
    }

    @Override
    public Optional<WorldCell> getWorldCellWhenPressedBy(int key) {
        if (moveArea.isOpen() && state == EntityState.WAIT) {
            final AtomicReference<Optional<WorldCell>> posPressed = new AtomicReference<>(Optional.empty());
            worldCell.getArea().getHandle().forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(cell -> {
                if (isPressedOnFrame(key, getWorld(), cell.getPos().x, cell.getPos().y)) posPressed.set(Optional.of(cell));
            })));
            return posPressed.get();
        }
        return Optional.empty();
    }

    @Override
    public void render(Renderer renderer) {
        moveArea.setOpen(isSelected);
        if (moveArea.isOpen()) moveArea.render(renderer);
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        traceCell.update(dt);
        updateWorldCell();
    }

    protected final void move(float velocity) {
        if (state == EntityState.WAIT) {
            traceCell.init();
            snapshotWorldPos.add(WorldPos.of(worldPos));
        }

        if (traceCell.hasNext()) {
            moveUp(velocity);
            moveBottom(velocity);
            moveLeft(velocity);
            moveRight(velocity);
            traceCell.next();
        }
        else {
            traceCell.stop();
            state = EntityState.PREPARED;
        }
    }

    private void moveUp(float velocity) {
        if (traceCell.getNext().getPos().y > worldPos.getPos().y) {
            direction = Direction.TOP;
            translate(0, velocity);
        }
    }

    private void moveLeft(float velocity) {
        if (traceCell.getNext().getPos().x < worldPos.getPos().x) {
            direction = Direction.LEFT;
            translate(-velocity, 0);
        }
    }

    private void moveBottom(float velocity) {
        if (traceCell.getNext().getPos().y < worldPos.getPos().y) {
            direction = Direction.BOTTOM;
            translate(0, -velocity);
        }
    }

    private void moveRight(float velocity) {
        if (traceCell.getNext().getPos().x > worldPos.getPos().x) {
            direction = Direction.RIGHT;
            translate(velocity, 0);
        }
    }

    private void updateWorldCell() {
        getWorld().getWorldArea().getCellAt(worldPos.getPos())
                .ifPresent(this::setWorldCell);
    }

    @Override
    public void setMoveArea(MoveAreaAttribute moveAreaAttribute) {
        this.moveAreaAttribute = moveAreaAttribute;
        moveArea = MoveAreas.parse(this.moveAreaAttribute, this);
    }

    protected void translate(float velocityX, float velocityY) {
        getPos().add(velocityX, velocityY, 0);
    }

    @Override
    public void setTilesNotExplorableById(int[] tilesSolidId) {
        Arrays.stream(tilesSolidId).forEach(this::addTileById);
    }

    private void addTileById(int id) {
        final TiledMapTile tile = getWorld().getMap().getHandle().getTileSets().getTile(id);
        if (tile != null && !tilesNotExplorable.contains(tile, false))
            tilesNotExplorable.add(tile);
        else if (tilesNotExplorable.contains(tile, false))
            Logger.warning("Tile id="+id+" is already masked");
        else
            Logger.warning("Unrecognized tile from id="+id+" ");
    }

    @Override
    public IWorld getWorld() {
        return getWorldPos().getWorld();
    }

    @Override
    public Vector3 getPos() {
        return getWorldPos().getPos();
    }

    @Override
    public WorldPos getWorldPos() {
        return worldPos;
    }

    @Override
    public Array<TiledMapTile> getTilesNotExplorable() {
        return tilesNotExplorable;
    }

    @Override
    public MoveArea getMoveArea() {
        return moveArea;
    }

    @Override
    public void setMoveArea(MoveArea moveZoneArea) {
        this.moveArea = moveZoneArea;
    }

    @Override
    public TraceCell getTraceCell() {
        return traceCell;
    }

    public float getStateTime() {
        return stateTime;
    }

    @Override
    public void setState(EntityState state) {
        this.state = state;
    }

    @Override
    public EntityState getState() {
        return state;
    }
    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public MoveAreaAttribute getMoveAreaAttribute() {
        return moveAreaAttribute;
    }

    @Override
    public Optional<MoveCell> getMoveCellPressed() {
        if (moveCellPressed != null)
            return Optional.of(moveCellPressed);
        return Optional.empty();
    }

    @Override
    public void setMoveCellPressed(MoveCell moveCellPressed) {
        this.moveCellPressed = moveCellPressed;
    }

    @Override
    public WorldCell getWorldCell() {
        return worldCell;
    }

    @Override
    public void setWorldCell(WorldCell worldCell) {
        this.worldCell = worldCell;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public AttackArea getAttackArea() {
        return attackArea;
    }

    @Override
    public void setAttackArea(AttackArea attackArea) {
        this.attackArea = attackArea;
    }

    @Override
    public Array<WorldPos> getSnapshotWorldPos() {
        return snapshotWorldPos;
    }
}
