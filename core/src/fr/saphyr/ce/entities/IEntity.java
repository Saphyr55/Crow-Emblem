package fr.saphyr.ce.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.entities.area.AttackArea;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.WorldPos;
import fr.saphyr.ce.entities.area.cell.MoveCell;
import fr.saphyr.ce.entities.area.TraceCell;
import fr.saphyr.ce.entities.area.MoveArea;
import fr.saphyr.ce.entities.area.MoveAreaAttribute;
import fr.saphyr.ce.world.area.cell.WorldCell;

import java.util.Optional;

public interface IEntity extends CEObject, Selectable {


    MoveAreaAttribute getMoveAreaAttribute();

    void setState(EntityState state);

    EntityState getState();

    Direction getDirection();

    TraceCell getTraceCell();

    MoveArea getMoveArea();
    
    Vector3 getPos();

    IWorld getWorld();

    WorldPos getWorldPos();

    Array<TiledMapTile> getTilesNotExplorable();

    WorldCell getWorldCell();

    AttackArea getAttackArea();

    Optional<MoveCell> getMoveCellPressed();
    
    Optional<WorldCell> getWorldCellWhenPressedBy(int key);

    void setMoveArea(MoveAreaAttribute moveAreaAttribute);

    void setTilesNotExplorableById(int[] tilesSolidId);

    void setMoveArea(MoveArea moveZoneArea);

    void setMoveCellPressed(MoveCell cellClicked);

    boolean isSelected();

    void setSelected(boolean selected);

    float getStateTime();

    void setAttackArea(AttackArea attackArea);

    void setWorldCell(WorldCell worldCell);

    Array<WorldPos> getSnapshotWorldPos();
}
