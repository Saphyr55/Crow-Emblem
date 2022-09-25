package fr.saphyr.ce.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.graphic.Textures;
import fr.saphyr.ce.utils.CEMath;

import static fr.saphyr.ce.world.area.Area.YELLOW_AREA_TEXTURE;

public class FollowCell implements CEObject {

    private final Texture texture = YELLOW_AREA_TEXTURE;
    private final Vector3 pos;
    public final float TRANSLATE_VELOCITY = 10f;

    public FollowCell(final Vector3 pos) {
        this.pos = new Vector3(pos);
    }

    public FollowCell(float x, float y) {
        this.pos = new Vector3(x, y, 0);
    }

    @Override
    public void update(float dt) {

    }

    public Vector3 getPos() {
        return pos;
    }

    private void move(final float dt) {
        final float velocity = TRANSLATE_VELOCITY * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            pos.add(velocity, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            pos.add(-velocity, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            pos.add(0, -velocity, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            pos.add(0, velocity, 0);
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.draw(texture, pos.x, pos.y, 1, 1);
    }
}
