package fr.saphyr.ce.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.world.World;
import fr.saphyr.ce.world.WorldPos;
import fr.saphyr.ce.world.area.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Entity implements CEObject, Selectable {

    public static final float EPSILON = 0.09f;

    protected Texture texture;
    protected TraceArea traceArea;
    protected MoveArea moveArea;
    protected Array<TiledMapTile> tilesNotExplorable;
    protected final WorldPos worldPos;
    protected float stateTime = 0.0f;
    protected boolean isSelected;
    protected boolean isMoved = false;
    protected Direction direction;
    private MoveAreaAttribute moveAreaAttribute;
    private Area areaClicked;

    public Entity(WorldPos worldPos, int[] tileNotExplorable, MoveAreaAttribute moveAreaAttribute) {
        this.worldPos = worldPos;
        this.isSelected = false;
        this.tilesNotExplorable = new Array<>();
        setTilesNotExplorableById(tileNotExplorable);
        setMoveArea(moveAreaAttribute);
        this.traceArea = new TraceArea(moveArea);
    }

    @Override
    public Optional<Area> getAreaSelect() {
        AtomicReference<Optional<Area>> posClicked = new AtomicReference<>(Optional.empty());
        if (moveArea.isOpen() && !isMoved) {
            moveArea.forEach(optionals -> optionals.forEach(optional -> optional.ifPresent(area -> {
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
        if (!isMoved) traceArea.init();
        if (traceArea.hasNext()) {
            moveUp(velocity);
            moveBottom(velocity);
            moveLeft(velocity);
            moveRight(velocity);
            traceArea.next();
        } else traceArea.stop();
    }

    private void moveUp(float velocity) {
        if (traceArea.getNext().getPos().y > worldPos.getPos().y) {
            direction = Direction.UP;
            translate(0, velocity);
        }
    }

    private void moveLeft(float velocity) {
        if (traceArea.getNext().getPos().x < worldPos.getPos().x) {
            direction = Direction.LEFT;
            translate(-velocity, 0);
        }
    }

    private void moveBottom(float velocity) {
        if (traceArea.getNext().getPos().y < worldPos.getPos().y) {
            direction = Direction.BOTTOM;
            translate(0, -velocity);
        }
    }

    private void moveRight(float velocity) {
        if (traceArea.getNext().getPos().x > worldPos.getPos().x) {
            direction = Direction.RIGHT;
            translate(velocity, 0);
        }
    }

    public void setMoveArea(MoveAreaAttribute moveAreaAttribute) {
        this.moveAreaAttribute = moveAreaAttribute;
        moveArea = MoveAreas.parse(moveAreaAttribute, this);
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

    public World getWorld() {
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

    public void setMoveArea(MoveArea moveArea) {
        this.moveArea = moveArea;
    }

    public TraceArea getTraceArea() {
        return traceArea;
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

    public Optional<Area> getAreaClicked() {
        if (areaClicked != null)
            return Optional.of(areaClicked);
        return Optional.empty();
    }

    public void setAreaClicked(Area areaClicked) {
        this.areaClicked = areaClicked;
    }


}
