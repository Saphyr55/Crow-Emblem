package fr.saphyr.ce.entities.enemies;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.graphics.MoveArea;
import fr.saphyr.ce.graphics.MoveAreas;
import fr.saphyr.ce.worlds.World;

public class Slime extends Entity {

    private final Animation<TextureRegion> animationIdle;
    private final Animation<TextureRegion> animationDeath;
    private boolean slimeSelected;
    private float slimeStateTime;

    public Slime(Texture texture, World world, Vector3 worldPos, int[] idNotExplorable) {
        super(texture);
        this.world = world;
        this.pos = worldPos;
        setTilesNotExplorableById(idNotExplorable);
        slimeStateTime = 0;
        slimeSelected = false;

        TextureRegion[][] slimeFrames = TextureRegion.split(
                texture, texture.getWidth() / 3, texture.getHeight() / 3);

        setMoveArea(MoveAreas.DEFAULT_MOVE_ZONE_9);
        animationIdle = new Animation<>(500 / 1000f, slimeFrames[0]);
        animationDeath = new Animation<>(80 / 1000f, slimeFrames[1]);
    }

    @Override
    public void render(Renderer renderer) {
        TextureRegion slimeCurrentFrame = animationIdle.getKeyFrame(slimeStateTime, true);
        renderer.draw(slimeCurrentFrame, pos.x, pos.y, 1, 1);
        if (slimeSelected) {
            moveArea.draw(renderer);
            moveArea.setOpen(true);
        }
        else
            moveArea.setOpen(false);
    }

    @Override
    public void update(final float dt) {
        MoveArea.Area areaClick = getAreaClickFromMoveArea();
        if (areaClick != null && areaClick.isAccessible() && areaClick.isExplorable() && moveArea.isOpen()) {
            setPos(new Vector3(areaClick.getPos(), 0));
            setMoveArea(MoveAreas.DEFAULT_MOVE_ZONE_9);
        }

        slimeStateTime += dt;
        selectOnClick(Input.Buttons.LEFT, slimeSelected,
                () -> slimeSelected = false,
                () -> slimeSelected = true
        );
    }

    public boolean isSlimeSelected() {
        return slimeSelected;
    }

    public void setSlimeSelected(boolean slimeSelected) {
        this.slimeSelected = slimeSelected;
    }

    public Vector3 getPos() {
        return pos;
    }

    public void setPos(Vector3 pos) {
        this.pos = pos;
    }

    public void dispose() {
        texture.dispose();
    }

}
