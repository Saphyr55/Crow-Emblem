package fr.saphyr.ce.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.core.Resources;
import fr.saphyr.ce.graphics.Fonts;

public class LoadingScene extends Scene {

    private ProgressBar progressBar;
    private float progressLogic;
    protected Stage stage;
    protected Table root;

    public LoadingScene() {
        stage = new Stage(new ScreenViewport());
        root = new Table();
    }

    @Override
    public void init() {
        super.init();
        Table loadingTable = new Table();
        final var label = new Label("Crow Emblem", new Label.LabelStyle(Fonts.createTTF("fonts/CinzelDecorative-Black.ttf", 50), Color.WHITE));
        final var textureBar = new TextureRegionDrawable(new TextureRegion(new Texture("textures/ui/progress_bar.png")));
        final var progressBarStyle =  new ProgressBar.ProgressBarStyle(null, textureBar );
        progressBar = new ProgressBar(0f, 1f, 0.1f, true, progressBarStyle);
        loadingTable.row();
        loadingTable.add(progressBar).padBottom(100);
        loadingTable.row();
        // root.add(label).center().expandY().padTop(200);
        root.add(label);
        root.row();
        // root.add(loadingTable);
        root.setFillParent(true);
        stage.addActor(root);
    }

    @Override
    public void render(Renderer renderer) {
        if (!Resources.manager.update()) {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Table getRoot() {
        return root;
    }

    public void setRoot(Table root) {
        this.root = root;
    }
}
