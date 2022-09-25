package fr.saphyr.ce.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.core.Updatable;
import fr.saphyr.ce.world.area.Area;

import java.util.function.Consumer;

public class Camera extends OrthographicCamera implements CEObject {

    public final float TRANSLATE_VELOCITY = 15f;
    private final Vector3 border;
    private final Vector3 zeroPosMap;
    private final Vector3 initPos;
    private final FollowCell followCell;
    private Vector3 middlePos;

    public Camera(float widthBorder, float heightBorder, float viewportWidth, float viewportHeight) {
        this.setToOrtho(false, viewportWidth, viewportHeight);
        this.border = new Vector3(widthBorder, heightBorder, 0);
        this.zeroPosMap = new Vector3(this.position);
        this.initPos = new Vector3(position);
        this.update(true);
        this.middlePos = new Vector3( ((int) viewportHeight) - 1, ((int) viewportHeight) - 1, 0);
        this.followCell = new FollowCell(middlePos);
    }

    public void initPos(Vector3 vector) {
        position.set(vector);
        initPos.set(vector);
        update();
    }

    @Override
    public void update(final float dt) {
        move(dt);
        updateEdge();
        update(true);
    }

    @Override
    public void render(Renderer renderer) {

    }

    private void move(final float dt) {
        final float velocity = TRANSLATE_VELOCITY * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            translate(velocity, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            translate(-velocity, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            translate(0, -velocity, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            translate(0, velocity, 0);
        }
    }
    
    private void updateEdge() {
        position.y = MathUtils.clamp(position.y, zeroPosMap.y, border.y - zeroPosMap.y);
        position.x = MathUtils.clamp(position.x, zeroPosMap.x, border.x - zeroPosMap.x);
    }

}
