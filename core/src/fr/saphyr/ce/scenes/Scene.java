package fr.saphyr.ce.scenes;

import com.badlogic.gdx.utils.Disposable;
import fr.saphyr.ce.CEObject;

public abstract class Scene implements CEObject, Disposable {

    protected boolean isActive = false;
    protected boolean isInit = false;

    public Scene() {

    }

    public void init() { isInit = true; }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isInit() {
        return isInit;
    }
}
