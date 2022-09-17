package fr.saphyr.ce.scenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ArrayMap;
import fr.saphyr.ce.area.MoveAreas;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.enemies.Slime;
import fr.saphyr.ce.entities.players.LordPlayer;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.maps.Maps;
import fr.saphyr.ce.worlds.World;
import fr.saphyr.ce.worlds.WorldPos;

public class GameScene extends Scene {

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
        // world.addEntities(new Slime(new WorldPos(world, new Vector2(2, 2)), tilesNotExplorable, MoveAreas.DEFAULT_MOVE_ZONE_9));
        // world.addEntities(new LordPlayer(new WorldPos(world, new Vector2(3, 2)), tilesNotExplorable, MoveAreas.DEFAULT_MOVE_ZONE_9));
        world.addEntities(new LordPlayer(new WorldPos(world, new Vector2(4, 2)), tilesNotExplorable, MoveAreas.DEFAULT_MOVE_ZONE_9));
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
