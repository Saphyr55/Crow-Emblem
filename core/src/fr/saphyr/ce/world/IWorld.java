package fr.saphyr.ce.world;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.world.area.WorldArea;
import fr.saphyr.ce.world.area.cell.WorldCell;
import fr.saphyr.ce.world.map.Map;

public interface IWorld extends Disposable, CEObject {

    Camera getCamera();

    Map getMap();

    WorldPos getMouseWorldPos();

    Vector3 getInitPos();

    Array<IEntity> getEntities();

    void addEntities(IEntity entity);

    void removeEntities(IEntity entity);

    int getCountEntities();

    WorldArea getWorldArea();

    @Override
    default void dispose() {
        getMap().dispose();
    }

    FollowCamera getFollowCamera();
}
