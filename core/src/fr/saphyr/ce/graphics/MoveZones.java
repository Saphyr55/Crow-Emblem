package fr.saphyr.ce.graphics;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.maps.Map;
import fr.saphyr.ce.worlds.World;

import java.util.Optional;
import java.util.function.Supplier;

public final class MoveZones {

    public static final int[][] DEFAULT_MOVE_ZONE = {
            { 0, 0, 1, 0, 0 },
            { 0, 1, 1, 1, 0 },
            { 1, 1, 1, 1, 1 },
            { 0, 1, 1, 1, 0 },
            { 0, 0, 1, 0, 0 },
    };

    public static MoveZone personalize(Supplier<MoveZone> algo) { return algo.get(); }

    public static MoveZone parse(int[][] moveZoneInt, Entity entity) {
        return MoveZones.personalize(() -> {
            var moveZone = new MoveZone(entity.getWorld(), entity.getTilesNotExplorable());
            for (int i = 0; i < moveZoneInt.length; i++) {
                moveZone.add(new Array<>(moveZoneInt.length));
                for (int j = 0; j < moveZoneInt[i].length; j++) {
                    MoveZone.Zone zone = new MoveZone.Zone(
                            new Vector2(entity.getPos().x + i - ((int) (moveZoneInt.length / 2f)),
                                    entity.getPos().y + j - ((int) (moveZoneInt[i].length / 2f))));

                    if (moveZoneInt[i][j] == 0)
                        moveZone.get(i).add(Optional.empty());
                    else if (moveZoneInt[i][j] == 1) {
                        moveZone.get(i).add(Optional.of(zone));
                    }
                }
            }
            return moveZone;
        });
    }

}
