package fr.saphyr.ce.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.saphyr.ce.core.CEObject;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.core.Updatable;
import fr.saphyr.ce.graphics.Drawable;

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
