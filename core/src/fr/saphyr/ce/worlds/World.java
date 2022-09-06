package fr.saphyr.ce.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.Renderer;
import fr.saphyr.ce.core.Camera;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.maps.Map;

public class World implements Disposable, CEObject {

    private Vector3 mousePosInWorld;

    private Camera camera;
    private Map map;

    private final Array<Entity> entities;

    public World(Map map) {
        this.camera = new Camera(
                map.getHandle().getProperties().get("width", Integer.class),
                map.getHandle().getProperties().get("height", Integer.class),
                20, 10);
        this.map = map;
        this.entities = new Array<>();
        this.mousePosInWorld = new Vector3();
    }

    @Override
    public void update(final float dt) {
        camera.update(dt);
        entities.iterator().forEachRemaining(entity -> entity.update(dt));
    }

    @Override
    public void render(Renderer renderer) {
        setMousePosition(renderer);
        entities.iterator().forEachRemaining(entity -> entity.render(renderer));
    }

    private void setMousePosition(Renderer renderer) {
        renderer.getMapRenderer().setView(getCamera());
        renderer.getMapRenderer().render();
        mousePosInWorld.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePosInWorld);
    }

    public Vector3 getMousePos() {
        return mousePosInWorld;
    }

    public void addEntities(Entity entity) {
        entities.add(entity);
    }

    public void removeEntities(Entity entity) {
        if(!entities.removeValue(entity, false))
            Logger.error("", new Exception("Impossible to remove a entity"));
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public void dispose() {
        map.dispose();
    }


}
