package fr.saphyr.ce.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.world.area.WorldArea;
import fr.saphyr.ce.world.map.Map;

public class World implements IWorld {

    private final WorldPos mouseWorldPos;
    private final WorldArea worldArea;
    private final Camera camera;
    private final FollowCamera followCamera;
    private final Map map;
    private final Vector3 initPos;
    private final Array<IEntity> entities;

    public World(Map map, Vector3 initPos) {
        this.initPos = initPos;
        this.map = map;
        this.camera = new Camera(map.getWidth(), map.getHeight(), 20f, 10f);
        this.camera.initPos(initPos);
        this.entities = new Array<>();
        this.mouseWorldPos = new WorldPos(this, new Vector2());
        this.camera.position.set(initPos);
        this.worldArea = new WorldArea(this);
        this.followCamera = new FollowCamera(this);
    }

    private void setMousePosition(Renderer renderer) {
        renderer.getMapRenderer().setView(getCamera());
        renderer.getMapRenderer().render();
        mouseWorldPos.setPos(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        camera.unproject(mouseWorldPos.getPos());
    }

    @Override
    public void update(final float dt) {
        entities.forEach(entity -> entity.update(dt));
        followCamera.update(dt);
        camera.update(dt);
        //entities.get(0).getMoveArea().getAreaWithPos(getMouseWorldPos().getPos()).ifPresent(Logger::debug);
    }

    @Override
    public void render(Renderer renderer) {
        setMousePosition(renderer);
        camera.render(renderer);
        entities.iterator().forEachRemaining(entity -> entity.render(renderer));
        followCamera.render(renderer);
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
    public WorldArea getWorldArea() {
        return worldArea;
    }

    @Override
    public Map getMap() {
        return map;
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

    @Override
    public FollowCamera getFollowCamera() {
        return followCamera;
    }
}
