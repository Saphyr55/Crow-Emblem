package fr.saphyr.ce.entities.area;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.entities.area.cell.AttackCell;
import fr.saphyr.ce.graphic.Textures;
import fr.saphyr.ce.world.area.cell.AbstractCell;

import java.util.Optional;

public final class AttackAreas {
    
    private static final int[][] attackAreaIntDefault = {
            { 0, 1, 0 },
            { 1, 0, 1 },
            { 0, 1, 0 },
    };

    public static AttackArea getDefault(IEntity entity) {
        return parse(entity, attackAreaIntDefault);
    }

    public static AttackArea parse(IEntity entity, int[][] attackAreaInt) {
        final AttackArea attackArea = new AttackArea(entity.getWorld());
        for (int i = 0; i < attackAreaInt.length; i++) {
            attackArea.getHandle().add(new Array<>());
            for (int j = 0; j < attackAreaInt[i].length; j++) {
                final AttackCell attackCell = new AttackCell(new Vector3(
                        entity.getPos().x - i + ((int) (attackAreaInt.length / 2f)),
                        entity.getPos().y - j + ((int) (attackAreaInt[i].length / 2f)), 0),
                        new Vector3(i, j, 0), attackArea);
                switch (attackAreaInt[i][j]) {
                    case 0 -> attackArea.getHandle().get(i).add(Optional.empty());
                    case 1 -> {
                        attackCell.setTexture(AbstractCell.ATTACK_CELL_TEXTURE);
                        attackArea.getHandle().get(i).add(Optional.of(attackCell));
                    }
                }
            }
        }
        return attackArea;
    }

}
