package fr.saphyr.ce.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.WorldPos;
import fr.saphyr.ce.world.area.AbstractArea;
import fr.saphyr.ce.world.cell.*;
import fr.saphyr.ce.world.area.MoveArea;
import fr.saphyr.ce.world.area.MoveAreaAttribute;
import fr.saphyr.ce.world.area.MoveZoneAreas;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Entity implements IEntity {

    public static final float EPSILON = 0.09f;

    protected Texture texture;
    protected TraceCell traceCell;
    protected MoveArea moveArea;
    protected Array<TiledMapTile> tilesNotExplorable;
    protected WorldPos worldPos;
    protected float stateTime = 0.0f;
    protected boolean isSelected;
    protected boolean isMoved = false;
    protected Direction direction;
    private MoveAreaAttribute moveAreaAttribute;
    private MoveCell cellClicked;

    public Entity(WorldPos worldPos, int[] tileNotExplorable, MoveAreaAttribute moveAreaAttribute) {
        this.worldPos = worldPos;
        this.isSelected = false;
        this.tilesNotExplorable = new Array<>();
        setTilesNotExplorableById(tileNotExplorable);
        setMoveArea(moveAreaAttribute);
        this.traceCell = new TraceCell(moveArea);
    }

    public Entity() {
        this.isSelected = false;
        this.tilesNotExplorable = new Array<>();
    }

    @Override
    public Optional<ICell> getAreaSelect() {
        AtomicReference<Optional<ICell>> posClicked = new AtomicReference<>(Optional.empty());
        if (moveArea.isOpen() && !isMoved) {
            moveArea.getHandle().forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
                if (isClickOnFrame(Input.Buttons.LEFT, getWorld(), area.getPos().x, area.getPos().y))
                    posClicked.set(Optional.of(area));
            })));
        }
        return posClicked.get();
    }

    @Override
    public void render(Renderer renderer) {
        moveArea.setOpen(isSelected);
        if (moveArea.isOpen()) {
            moveArea.draw(renderer);
        }
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
    }

    protected final void move(float velocity) {
        if (!isMoved) traceCell.init();
        if (traceCell.hasNext()) {
            moveUp(velocity);
            moveBottom(velocity);
            moveLeft(velocity);
            moveRight(velocity);
            traceCell.next();
        } else traceCell.stop();
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

    public void setMoveArea(MoveAreaAttribute moveAreaAttribute) {
        this.moveAreaAttribute = moveAreaAttribute;
        moveArea = MoveZoneAreas.parse(moveAreaAttribute, this);
    }

    protected void translate(float velocityX, float velocityY) {
        getPos().add(velocityX, velocityY, 0);
    }

    protected TextureRegion[][] splitTexture(int col, int row) {
        return TextureRegion.split(texture, texture.getWidth() / col, texture.getHeight() / row);
    }

    public void setTilesNotExplorableById(int[] tilesSolidId) {
        Arrays.stream(tilesSolidId).forEach(this::addTileById);
    }

    private void addTileById(int id) {
        final TiledMapTile tile = getWorld().getMap().getHandle().getTileSets().getTile(id);
        if (tile != null && !tilesNotExplorable.contains(tile, false)) tilesNotExplorable.add(tile);
        else if (tilesNotExplorable.contains(tile, false)) Logger.warning("Tile id="+id+" is already masked");
        else Logger.warning("Unrecognized tile from id="+id+" ");
    }

    public IWorld getWorld() {
        return getWorldPos().getWorld();
    }

    public Vector3 getPos() {
        return getWorldPos().getPos();
    }

    public Texture getTexture() {
        return texture;
    }

    public WorldPos getWorldPos() {
        return worldPos;
    }

    public Array<TiledMapTile> getTilesNotExplorable() {
        return tilesNotExplorable;
    }

    public MoveArea getMoveArea() {
        return moveArea;
    }

    public void setMoveArea(MoveArea moveZoneArea) {
        this.moveArea = moveZoneArea;
    }

    public TraceCell getTraceArea() {
        return traceCell;
    }

    public float getStateTime() {
        return stateTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void setMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }

    public Direction getDirection() {
        return direction;
    }

    public MoveAreaAttribute getMoveAreaAttribute() {
        return moveAreaAttribute;
    }

    public Optional<ICell> getCellClicked() {
        if (cellClicked != null)
            return Optional.of(cellClicked);
        return Optional.empty();
    }

    public void setCellClicked(MoveCell cellClicked) {
        this.cellClicked = cellClicked;
    }


}
