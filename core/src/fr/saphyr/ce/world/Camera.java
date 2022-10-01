package fr.saphyr.ce.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Renderer;

public class Camera extends OrthographicCamera implements CEObject {

    public final float TRANSLATE_VELOCITY = 15f;
    private final Vector3 border;
    private final Vector3 zeroPosMap;
    private final Vector3 initPos;
    private Vector3 middlePos;

    public Camera(float widthBorder, float heightBorder, float viewportWidth, float viewportHeight) {
        this.setToOrtho(false, viewportWidth, viewportHeight);
        this.border = new Vector3(widthBorder, heightBorder, 1);
        this.zeroPosMap = new Vector3(position);
        this.middlePos = new Vector3(position);
        this.initPos = new Vector3(position);
        update(true);
    }

    public void initPos(Vector3 vector) {
        position.set(vector);
        initPos.set(vector);
        update();
    }

    @Override
    public void update(final float dt) {
        updateEdge();
        update(true);
    }

    @Override
    public void render(Renderer renderer) {
    }

    private void updateEdge() {
        position.y = MathUtils.clamp(position.y, zeroPosMap.y, border.y - zeroPosMap.y);
        position.x = MathUtils.clamp(position.x, zeroPosMap.x, border.x - zeroPosMap.x);
    }

    public Vector3 getBorder() {
        return border;
    }

    public Vector3 getZeroPosMap() {
        return zeroPosMap;
    }

    public Vector3 getInitPos() {
        return initPos;
    }

    public Vector3 getMiddlePos() {
        return middlePos;
    }

    public void setMiddlePos(Vector3 middlePos) {
        this.middlePos = middlePos;
    }
}
