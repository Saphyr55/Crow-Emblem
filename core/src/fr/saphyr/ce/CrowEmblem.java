package fr.saphyr.ce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.core.Resources;
import fr.saphyr.ce.entities.Slime;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.graphics.fonts.Fonts;
import fr.saphyr.ce.maps.Maps;
import fr.saphyr.ce.scenes.LoadingScene;
import fr.saphyr.ce.worlds.World;

public final class CrowEmblem extends ApplicationAdapter {

	private Renderer renderer;
	private World world;
	private LoadingScene loadingScene;
	private boolean alreadyInit = true;

	@Override
	public void create() {
		Resources.load();
		loadingScene = new LoadingScene();
		loadingScene.init();
		renderer = new Renderer();
	}

	public void update(final float dt) {
		loadingScene.update(dt);
		world.update(dt);
	}

	private void init() {
		if (alreadyInit) {
			alreadyInit = false;
			world = new World(Maps.get("maps/map1.tmx"), new Vector3(10, 10, 3));
			world.addEntities(new Slime(
					Textures.get("textures/slime/slime_spritesheet.png"),
					world, new Vector3(2, 2, 0), new int[]{ 2, 3, 8, 10, 11} ));
			renderer = Renderer.orthogonal(world);
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.CLEAR);
		if(!Resources.getHandle().update()) {
			renderer.begin();
			loadingScene.draw(renderer);
			renderer.end();
		}
		else {
			init();
			renderer.begin();
			Resources.getHandle().finishLoading();
			world.render(renderer);
			update(Gdx.graphics.getDeltaTime());
			renderer.end();
		}
	}

	@Override
	public void dispose() {
		world.dispose();
		loadingScene.dispose();
		renderer.dispose();
		Resources.dispose();
	}



}
