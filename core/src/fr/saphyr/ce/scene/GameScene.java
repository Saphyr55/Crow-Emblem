package fr.saphyr.ce.scene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ArrayMap;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.EntityType;
import fr.saphyr.ce.world.map.Maps;
import fr.saphyr.ce.world.World;
import fr.saphyr.ce.world.WorldPos;

public final class GameScene extends Scene {

    private final ArrayMap<String, World> worlds;
    private World currentWorld;
    private final Renderer renderer;

    public GameScene(Renderer renderer) {
        worlds = new ArrayMap<>();
        this.renderer = renderer;
    }

    @Override
    public void init() {
        super.init();
        int[] tilesNotExplorable = { 2, 3, 8, 10, 11 };
        World world = new World(Maps.get("maps/map1.tmx"), new Vector3(10, 10, 3));

        world.addEntities(EntityType.SLIME.construct()
                .withWorldPos(new WorldPos(world, new Vector2(4, 10)))
                .withTileNotExplorable(tilesNotExplorable)
                .build());

        world.addEntities(EntityType.BLADE_LORD.construct()
                .withWorldPos(new WorldPos(world, new Vector2(3, 5)))
                .withTileNotExplorable(tilesNotExplorable)
                .build());

        world.addEntities(EntityType.BLADE_LORD.construct()
                .withWorldPos(new WorldPos(world, new Vector2(4, 5)))
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
        worlds.forEach(stringWorldEntry -> stringWorldEntry.value.dispose());
    }

}
