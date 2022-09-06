package fr.saphyr.ce.graphics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.entities.Entity;

import java.util.Optional;
import java.util.function.Supplier;

public final class MoveAreas {

    public static final int[][] DEFAULT_MOVE_ZONE = {
            { 0, 0, 1, 0, 0 },
            { 0, 1, 1, 1, 0 },
            { 1, 1, 1, 1, 1 },
            { 0, 1, 1, 1, 0 },
            { 0, 0, 1, 0, 0 },
    };

    public static MoveArea personalize(Supplier<MoveArea> algo) { return algo.get(); }

    public static MoveArea parse(int[][] moveAreaInt, Entity entity) {
        return personalize(() -> {
            var moveZone = new MoveArea(entity.getWorld(), entity.getTilesNotExplorable());

            for (int i = 0; i < moveAreaInt.length; i++) {
                moveZone.add(new Array<>(moveAreaInt.length));

                for (int j = 0; j < moveAreaInt[i].length; j++) {
                    MoveArea.Area area = new MoveArea.Area(
                            new Vector2(entity.getPos().x - i + ((int) (moveAreaInt.length / 2f)),
                                    entity.getPos().y - j + ((int) (moveAreaInt[i].length / 2f))));

                    if (moveAreaInt[i][j] == 0)
                        moveZone.get(i).add(Optional.empty());
                    else if (moveAreaInt[i][j] == 1) {
                        moveZone.get(i).add(Optional.of(area));
                    }
                }
            }
            return moveZone;
        });
    }

}
