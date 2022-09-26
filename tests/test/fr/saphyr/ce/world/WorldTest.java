package fr.saphyr.ce.world;

import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.CETest;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.world.map.Map;
import fr.saphyr.ce.world.map.Maps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldTest extends CETest {

    private World world;
    private Map map;
    private Vector3 initPos;
    private Entity entity;

    @BeforeEach
    public void setUp() {
        initPos = new Vector3(0,0,0);
        map = Maps.get("maps/map1.tmx");
        world = new World(map, initPos);
        entity = new Entity() { };
    }

    @AfterEach
    public void tearDown() {
        world.dispose();
    }

    @Test
    public void addEntities() {
        world.addEntities(entity);
        assertTrue(world.getEntities().contains(entity, true));
    }

    @Test
    public void removeEntities() {
        world.removeEntities(entity);
        assertFalse(world.getEntities().contains(entity, true));
    }

}