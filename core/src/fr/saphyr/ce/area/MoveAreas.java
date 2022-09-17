package fr.saphyr.ce.area;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.entities.Entity;

import java.util.Optional;
import java.util.function.Supplier;

public final class MoveAreas {


    public static final int[][] DEFAULT_MOVE_ZONE_9 = {
            { 0, 0, 0, 0, 1, 0, 0, 0, 0 },
            { 0, 0, 0, 1, 1, 1, 0, 0, 0 },
            { 0, 0, 1, 1, 1, 1, 1, 0, 0 },
            { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
            { 0, 0, 1, 1, 1, 1, 1, 0, 0 },
            { 0, 0, 0, 1, 1, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 1, 0, 0, 0, 0 },
    };

    public static final int[][] DEFAULT_MOVE_ZONE_7 = {
            { 0, 0, 0, 1, 0, 0, 0 },
            { 0, 0, 1, 1, 1, 0, 0 },
            { 0, 1, 1, 1, 1, 1, 0 },
            { 1, 1, 1, 1, 1, 1, 1 },
            { 0, 1, 1, 1, 1, 1, 0 },
            { 0, 0, 1, 1, 1, 0, 0 },
            { 0, 0, 0, 1, 0, 0, 0 },
    };
    
    public static MoveArea personalize(Supplier<MoveArea> algo) { return algo.get(); }

    public static MoveArea parse(int[][] moveAreaInt, Entity entity) {
        return personalize(() -> {
            var moveArea = new MoveArea(entity);
            for (int i = 0; i < moveAreaInt.length; i++) {
                moveArea.add(new Array<>(moveAreaInt.length));
                for (int j = 0; j < moveAreaInt[i].length; j++) {
                    if (moveAreaInt[i][j] == 1) {
                        var area = new Area(new Vector3(
                                entity.getWorldPos().getPos().x - i + ((int) (moveAreaInt.length / 2f)),
                                entity.getWorldPos().getPos().y - j + ((int) (moveAreaInt[i].length / 2f)), 0),
                                new Vector3(i, j, 0), moveArea);
                        area.setAreaEntityAccessible(entity);
                        moveArea.get(i).add(Optional.of(area));
                    }
                    else {
                        moveArea.get(i).add(Optional.empty());
                    }
                }
            }
            moveArea.personalize();
            return moveArea;
        });
    }

}
