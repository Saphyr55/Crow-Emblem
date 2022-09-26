package fr.saphyr.ce.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.world.cell.WorldCell;
import fr.saphyr.ce.world.map.Map;

public class World implements IWorld {

    private final WorldPos mouseWorldPos;
    private final WorldCell worldCell;
    private Camera camera;
    private FollowCell followCell;
    private Map map;
    private final Vector3 initPos;
    private final Array<IEntity> entities;

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
        this.followCell = new FollowCell(this);
        this.worldCell = new WorldCell(this);
    }

    private void setMousePosition(Renderer renderer) {
        renderer.getMapRenderer().setView(getCamera());
        renderer.getMapRenderer().render();
        mouseWorldPos.setPos(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        camera.unproject(mouseWorldPos.getPos());
    }

    @Override
    public void update(final float dt) {
        camera.update(dt);
        followCell.update(dt);
        entities.forEach(entity -> entity.update(dt));
        //entities.get(0).getMoveArea().getAreaWithPos(getMouseWorldPos().getPos()).ifPresent(Logger::debug);
    }

    @Override
    public void render(Renderer renderer) {
        setMousePosition(renderer);
        camera.render(renderer);
        entities.iterator().forEachRemaining(entity -> entity.render(renderer));
        followCell.render(renderer);
    }

    @Override
    public void dispose() {
        map.dispose();
    }

    @Override
    public void addEntities(IEntity entity) {
        entities.add(entity);
    }

    @Override
    public void removeEntities(IEntity entity) {
        entities.removeValue(entity, false);
    }

    @Override
    public int getCountEntities() {
        return entities.size;
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public Map getMap() {
        return map;
    }

    @Override
    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public WorldPos getMouseWorldPos() {
        return mouseWorldPos;
    }

    @Override
    public Vector3 getInitPos() {
        return initPos;
    }

    @Override
    public Array<IEntity> getEntities() {
        return entities;
    }

}
