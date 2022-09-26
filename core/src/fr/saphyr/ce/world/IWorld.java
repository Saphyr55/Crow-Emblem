package fr.saphyr.ce.world;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.world.map.Map;

public interface IWorld extends Disposable, CEObject {

    Camera getCamera();

    Map getMap();

    WorldPos getMouseWorldPos();

    Vector3 getInitPos();

    void setMap(Map map);

    Array<IEntity> getEntities();

    void addEntities(IEntity entity);

    void removeEntities(IEntity entity);

    int getCountEntities();

    void setCamera(Camera camera);

}