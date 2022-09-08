package fr.saphyr.ce.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.function.Consumer;

public class Camera extends OrthographicCamera {

    private float translateVelocity = 20f;
    private final Vector3 border;
    private final Vector3 zeroPosMap;
    private final Vector3 initPos;

    public Camera(Vector3 border, Vector3 ortho) {
        this.setToOrtho(false, ortho.x, ortho.y);
        this.border = new Vector3(border);
        this.zeroPosMap = new Vector3(position);
        this.initPos = new Vector3(position);
        this.update(true);
    }

    public Camera(float widthBorder, float heightBorder, float viewportWidth, float viewportHeight) {
        this.setToOrtho(false, viewportWidth, viewportHeight);
        this.border = new Vector3(widthBorder, heightBorder, 0);
        this.zeroPosMap = new Vector3(this.position);
        this.initPos = new Vector3(position);
        this.update(true);
    }

    public void initPos(Vector3 vector) {
        position.set(vector);
        initPos.set(vector);
        update();
    }

    public void update(final float dt) {
        move(dt);
        updateEdge();
        update(true);
    }

    private void move(final float dt) {
        whenButtonPressed(Input.Buttons.MIDDLE, () -> moveCamera(dt));
    }

    private void moveCamera(final float dt) {
        final float velocity = translateVelocity * dt;
        if (Gdx.input.getDeltaX() < 0) {
            translate(velocity, 0, 0);
        }
        if (Gdx.input.getDeltaX() > 0) {
            translate(-velocity, 0, 0);
        }
        if (Gdx.input.getDeltaY() < 0) {
            translate(0, -velocity, 0);
        }
        if (Gdx.input.getDeltaY() > 0) {
            translate(0, velocity, 0);
        }
    }

    private void whenButtonPressed(int buttons, Runnable runnable) {
        Consumer<Boolean> c = b -> { if (b) runnable.run(); };
        c.accept(Gdx.input.isButtonPressed(buttons));
    }

    private void whenKeyPressed(int key, Runnable runnable) {
        Consumer<Boolean> c = b -> { if (b) runnable.run(); };
        c.accept(Gdx.input.isKeyPressed(key));
    }

    private void updateEdge() {
        position.y = MathUtils.clamp(position.y, zeroPosMap.y, border.y - zeroPosMap.y);
        position.x = MathUtils.clamp(position.x, zeroPosMap.x, border.x - zeroPosMap.x);
    }

    public float getTranslateVelocity() {
        return translateVelocity;
    }

    public void setTranslateVelocity(float translateVelocity) {
        this.translateVelocity = translateVelocity;
    }


}
