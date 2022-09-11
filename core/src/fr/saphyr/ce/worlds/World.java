package fr.saphyr.ce.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.core.Camera;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.maps.Map;

public class World implements Disposable, CEObject {

    private final WorldPos mouseWorldPos;
    private Camera camera;
    private Map map;
    private final Vector3 initPos;
    private final Array<Entity> entities;

    public World(Map map, Vector3 initPos) {
        this.initPos = initPos;
        this.camera = new Camera(
                map.getHandle().getProperties().get("width", Integer.class),
                map.getHandle().getProperties().get("height", Integer.class),
                20, 10);
        this.camera.initPos(initPos);
        this.map = map;
        this.entities = new Array<>();
        this.mouseWorldPos = new WorldPos(this, new Vector2());
        this.camera.position.set(initPos);
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
        mouseWorldPos.setPos(Gdx.input.getX(), Gdx.input.getY());
        camera.unproject(mouseWorldPos.getPos());
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

    public WorldPos getMouseWorldPos() {
        return mouseWorldPos;
    }

    public final Vector3 getInitPos() {
        return initPos;
    }

    public final Array<Entity> getEntities() {
        return entities;
    }

}
