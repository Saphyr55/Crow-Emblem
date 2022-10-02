package fr.saphyr.ce.scene;

import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.EntityType;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.map.Maps;
import fr.saphyr.ce.world.World;
import fr.saphyr.ce.world.WorldPos;

public final class GameScene extends Scene {

    private final ArrayMap<String, IWorld> worlds;
    private IWorld currentWorld;
    private final Renderer renderer;
    private static GameScene currentGameScene;

    public GameScene(Renderer renderer) {
        worlds = new ArrayMap<>();
        this.renderer = renderer;
        currentGameScene = this;
    }

    @Override
    public void init() {
        super.init();
        int[] tilesNotExplorable = { 2, 3, 8, 10, 11 };
        final IWorld world = World.of(Maps.get("maps/map1.tmx"), 10, 10);

        world.addEntities(EntityType.BLADE_LORD.construct()
                .withWorldPos(WorldPos.of(world, 3, 5))
                .withTileNotExplorable(tilesNotExplorable)
                .build());

        world.addEntities(EntityType.EIRIKA.construct()
                .withWorldPos(WorldPos.of(world, 4, 5))
                .withTileNotExplorable(tilesNotExplorable)
                .build());

        worlds.put("world1", world);

        currentWorld = worlds.get("world1");

        renderer.ortho(currentWorld);
    }

    @Override
    public void update(float dt) {
        currentWorld.update(dt);
    }

    @Override
    public void render(Renderer renderer) {
        currentWorld.render(renderer);
    }

    @Override
    public void dispose() {
        worlds.forEach(this::disposeByEntry);
    }

    private void disposeByEntry(ObjectMap.Entry<String, IWorld> entry) {
        entry.value.dispose();
    }

    public IWorld getCurrentWorld() {
        return currentWorld;
    }

    public static GameScene getCurrentGameScene() {
        return currentGameScene;
    }

}
