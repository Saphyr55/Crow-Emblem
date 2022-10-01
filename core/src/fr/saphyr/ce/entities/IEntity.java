package fr.saphyr.ce.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.WorldPos;
import fr.saphyr.ce.world.area.cell.ICell;
import fr.saphyr.ce.world.area.cell.MoveCell;
import fr.saphyr.ce.world.area.cell.TraceCell;
import fr.saphyr.ce.world.area.MoveArea;
import fr.saphyr.ce.world.area.MoveAreaAttribute;

import java.util.Optional;

public interface IEntity extends CEObject, Selectable {

    boolean isMoved();

    boolean isSelected();

    MoveAreaAttribute getMoveAreaAttribute();

    Direction getDirection();

    TraceCell getTraceArea();

    MoveArea getMoveArea();

    Texture getTexture();

    Vector3 getPos();

    IWorld getWorld();

    WorldPos getWorldPos();

    Optional<MoveCell> getCellPressed();
    
    Optional<MoveCell> getCellWherePressed();

    void setMoveArea(MoveAreaAttribute moveAreaAttribute);

    void setTilesNotExplorableById(int[] tilesSolidId);

    void setMoveArea(MoveArea moveZoneArea);

    void setMoved(boolean isMoved);

    void setCellPressed(MoveCell cellClicked);

    Array<TiledMapTile> getTilesNotExplorable();
}
