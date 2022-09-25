package fr.saphyr.ce.world.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import fr.saphyr.ce.CEFiles;
import fr.saphyr.ce.CETest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest extends CETest {

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void testGetTileFromWhenNull() {
        final Map map = Maps.get("maps/map1.tmx");
        final var tile = map.getTileFrom(-1,-1);
        assertNull(tile);
    }

    @Test
    void testGetTileFrom() {
        final var tile = Maps.get("maps/map1.tmx").getTileFrom(0, 0);
        assertEquals(tile.getId(), 1);
    }
}