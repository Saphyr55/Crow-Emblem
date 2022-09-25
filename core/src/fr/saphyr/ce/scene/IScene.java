package fr.saphyr.ce.scene;

import com.badlogic.gdx.utils.Disposable;
import fr.saphyr.ce.CEObject;

public interface IScene extends CEObject, Disposable {

    void init();

    boolean isActive();

    void setActive(boolean active);

    boolean isInit();

}
