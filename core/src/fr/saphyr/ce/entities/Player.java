package fr.saphyr.ce.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.entities.characters.Character;
import fr.saphyr.ce.entities.area.MoveAreaAttribute;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.world.WorldPos;
import fr.saphyr.ce.world.area.cell.WorldCell;

public abstract class Player extends Character {

    public static Player playerSelected = null;
    public static boolean hasPlayerSelected = false;
    protected final float velocityMove = 4;
    private float velocityMoveDeltaTime;

    public Player(WorldPos worldPos, int[]tileNotExplorable, MoveAreaAttribute moveAreaAttribute) {
        super(worldPos, tileNotExplorable, moveAreaAttribute);
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
        if (state == EntityState.PREPARED) {
            if (attackArea.haveMinimumOne()) {
                attackArea.render(renderer);
            }
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setVelocityMoveDeltaTime(dt);
        if (moveArea.isOpen()) updateTraceCell();
        updateMove();
        movePlayer();
        updatePlayerSelected();

        if (state == EntityState.PREPARED) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
                worldPos = snapshotWorldPos.peek();
                worldCell = new WorldCell(getPos(), getWorld().getWorldArea());
                getWorld().getFollowCamera().getPos().set(getPos());
                getWorld().getFollowCamera().getCurrentWorldCell().getPos().set(getPos());
                traceCell.stop();
                state = EntityState.WAIT;
            }
        }

    }

    private void updateTraceCell() {
        moveArea.getCellAt(getWorld().getFollowCamera().getPos())
                .ifPresent(cell -> getWorld().getWorldArea().getCellAt(cell.getPos())
                .ifPresent(worldCell -> {
                    if (worldCell.getContentEntity().isEmpty() || this == worldCell.getContentEntity().get())
                        traceCell.updateEndCell(cell);
                }));
    }

    private void updatePlayerSelected() {
        if((state == EntityState.WAIT || state == EntityState.FINISH) && isPressedOnFrame(Input.Keys.ENTER, worldPos)) {
            if (hasPlayerSelected) {
                hasPlayerSelected = false;
                playerSelected.isSelected = false;
                playerSelected = null;
            }
            else {
                hasPlayerSelected = true;
                playerSelected = this;
                isSelected = true;
            }
        }
    }

    private void updateMove() {
        if (state == EntityState.WAIT && getMoveCellPressed().isEmpty())
            getWorldCellWhenPressedBy(Input.Keys.ENTER).ifPresent(worldCell -> {
                if (worldCell.getContentEntity().isEmpty()) {
                    moveArea.getCellAt(worldCell.getPos()).ifPresent(worldCell1 -> moveCellPressed = worldCell1);
                }
                /*
                else {
                    final Character character = (Character) worldCell.getContentEntity().get();
                    if (character instanceof Enemy) {
                        this.launchCombatWith(character);
                        moveCellPressed = traceCell.getEndCell();
                    }
                 }
                 */
            });
    }

    private void movePlayer() {
        getMoveCellPressed().ifPresent(cell -> {
            if (cell.isAccessible() && cell.isExplorable())
                move(velocityMoveDeltaTime);
            else
                moveCellPressed = null;
        });
    }

    private void setVelocityMoveDeltaTime(float dt) {
        velocityMoveDeltaTime = velocityMove * dt;
    }

    public static Player getPlayerSelected() {
        return playerSelected;
    }

    public static boolean hasEntitySelected() {
        return hasPlayerSelected;
    }
}


