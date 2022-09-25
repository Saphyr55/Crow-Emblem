package fr.saphyr.ce.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.world.map.Map;

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
                21, 11);
        this.camera.initPos(initPos);
        this.map = map;
        this.entities = new Array<>();
        this.mouseWorldPos = new WorldPos(this, new Vector2());
        this.camera.position.set(initPos);
    }

    @Override
    public void update(final float dt) {
        camera.update(dt);
        entities.forEach(entity -> entity.update(dt));
        //entities.get(0).getMoveArea().getAreaWithPos(getMouseWorldPos().getPos()).ifPresent(Logger::debug);
    }

    @Override
    public void render(Renderer renderer) {
        setMousePosition(renderer);
        camera.render(renderer);
        entities.iterator().forEachRemaining(entity -> entity.render(renderer));
    }

    private void setMousePosition(Renderer renderer) {
        renderer.getMapRenderer().setView(getCamera());
        renderer.getMapRenderer().render();
        mouseWorldPos.setPos(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        camera.unproject(mouseWorldPos.getPos());
    }

    @Override
    public void dispose() {
        map.dispose();
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
