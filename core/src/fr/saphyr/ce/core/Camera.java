package fr.saphyr.ce.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.function.Consumer;

public class Camera extends OrthographicCamera {

    private float translateVelocity = 0.25f;
    private final Vector3 border;
    private final Vector3 firstPos;


    public Camera(Vector2 border, Vector2 ortho) {
        this.setToOrtho(false, ortho.x, ortho.y);
        this.border = new Vector3(border, 0);
        this.firstPos = new Vector3(this.position);
        this.update(true);
    }

    public Camera(float widthBorder, float heightBorder, float viewportWidth, float viewportHeight) {
        this.setToOrtho(false, viewportWidth, viewportHeight);
        this.border = new Vector3(widthBorder, heightBorder, 0);
        this.firstPos = new Vector3(this.position);
        this.update(true);
    }

    public void update(final float dt) {
        move();
        updateEdge();
        update(true);
    }

    private void move() {
        whenKeyPressed(Input.Keys.A, () -> translate(-translateVelocity, 0, 0));
        whenKeyPressed(Input.Keys.D, () -> translate(translateVelocity, 0, 0));
        whenKeyPressed(Input.Keys.S, () -> translate(0, -translateVelocity, 0));
        whenKeyPressed(Input.Keys.W, () -> translate(0, translateVelocity, 0));
    }

    private void whenKeyPressed(int key, Runnable runnable) {
        Consumer<Boolean> c = b -> { if (b) runnable.run(); };
        c.accept(Gdx.input.isKeyPressed(key));
    }

    private void updateEdge() {
        position.y = MathUtils.clamp(position.y, firstPos.y, border.y - firstPos.y);
        position.x = MathUtils.clamp(position.x, firstPos.x, border.x - firstPos.x);
    }

    public float getTranslateVelocity() {
        return translateVelocity;
    }

    public void setTranslateVelocity(float translateVelocity) {
        this.translateVelocity = translateVelocity;
    }


}
