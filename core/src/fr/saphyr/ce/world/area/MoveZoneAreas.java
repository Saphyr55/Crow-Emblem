package fr.saphyr.ce.world.area;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.entities.Entity;
import fr.saphyr.ce.graphic.Textures;

import java.util.*;
import java.util.function.Supplier;

public final class MoveZoneAreas {

    public static MoveArea personalize(Supplier<MoveArea> algo) { return algo.get(); }

    public static MoveArea parse(MoveAreaAttribute moveAreaAttribute, Entity entity) {
        return personalize(() -> {
            final MoveArea moveZoneArea = new MoveArea(entity);
            for (int i = 0; i < moveAreaAttribute.pattern().length; i++) {
                moveZoneArea.getHandle().add(new Array<>(moveAreaAttribute.pattern().length));
                for (int j = 0; j < moveAreaAttribute.pattern()[i].length; j++) {
                    final var area = new fr.saphyr.ce.world.cell.MoveCell(new Vector3(
                            entity.getWorldPos().getPos().x - i + ((int) (moveAreaAttribute.pattern().length / 2f)),
                            entity.getWorldPos().getPos().y - j + ((int) (moveAreaAttribute.pattern()[i].length / 2f)), 0),
                            new Vector3(i, j, 0), moveZoneArea);
                    final int finalI = i;
                    final int finalJ = j;
                    area.setAreaEntityAccessible(entity);
                    moveAreaAttribute.areaAttributes().forEach(areaAttribute -> {
                        if (areaAttribute.key() == moveAreaAttribute.pattern()[finalI][finalJ]) {
                            area.setTexture(Textures.get(areaAttribute.textureFilepath()));
                            area.setExplorable(areaAttribute.isExplorable());
                            moveZoneArea.getHandle().get(finalI).add(Optional.of(area));
                        } else {
                            moveZoneArea.getHandle().get(finalI).add(Optional.empty());
                        }
                    });
                }
            }
            return moveZoneArea.personalize();
        });
    }
}
