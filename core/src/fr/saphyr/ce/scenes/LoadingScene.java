package fr.saphyr.ce.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.core.Resources;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.graphics.fonts.Fonts;

public class LoadingScene extends Scene {

    private ProgressBar progressBar;
    private float progressLogic;

    @Override
    public void init() {
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
    public void update(float dt) {
        progressLogic += dt;
        progressBar.setValue(progressLogic);
    }
}
