package fr.saphyr.ce.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Direction;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.World;
import fr.saphyr.ce.world.WorldPos;
import fr.saphyr.ce.world.area.*;

import java.util.Optional;

public interface IEntity extends CEObject, Selectable {

    boolean isMoved();

    boolean isSelected();

    MoveAreaAttribute getMoveAreaAttribute();

    Direction getDirection();

    TraceArea getTraceArea();

    MoveArea getMoveArea();

    Texture getTexture();

    Vector3 getPos();

    IWorld getWorld();

    WorldPos getWorldPos();

    Optional<IArea> getAreaClicked();
    
    Optional<IArea> getAreaSelect();

    void setMoveArea(MoveAreaAttribute moveAreaAttribute);

    void setTilesNotExplorableById(int[] tilesSolidId);

    void setMoveArea(MoveArea moveArea);

    void setMoved(boolean isMoved);

    void setAreaClicked(IArea areaClicked);


}
