package fr.saphyr.ce.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.core.Updatable;
import fr.saphyr.ce.graphics.Drawable;

public abstract class Scene implements Drawable, Disposable, Updatable {

    protected Stage stage;
    protected Table root;

    public Scene() {
        stage = new Stage(new ScreenViewport());
        root = new Table();
    }

    public abstract void init();

    @Override
    public void draw(Renderer renderer) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
