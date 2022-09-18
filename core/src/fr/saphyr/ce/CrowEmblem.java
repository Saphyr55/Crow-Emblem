package fr.saphyr.ce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.saphyr.ce.area.MoveAreaAttribute;
import fr.saphyr.ce.area.MoveAreas;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.core.Resources;
import fr.saphyr.ce.scenes.GameScene;
import fr.saphyr.ce.scenes.LoadingScene;
import fr.saphyr.ce.scenes.SceneManager;

import java.lang.management.ManagementFactory;

public final class CrowEmblem extends ApplicationAdapter {

	public static final boolean IS_DEBUGGING = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

	private Renderer renderer;
	private SceneManager sceneManager;

	@Override
	public void create() {
		Resources.load();
		renderer = new Renderer();
		sceneManager = new SceneManager(renderer);
		sceneManager.add("load", new LoadingScene());
		sceneManager.add("game", new GameScene(renderer));
		sceneManager.switchTo("load");
		sceneManager.init();
	}

	@Override
	public void render () {
		final float dt = Gdx.graphics.getDeltaTime();
		ScreenUtils.clear(Color.CLEAR);
		if (Resources.manager.isFinished()) {
			sceneManager.switchTo("game");
		}
		sceneManager.init();
		sceneManager.update(dt);
		sceneManager.render(renderer);
	}

	@Override
	public void dispose() {
		sceneManager.dispose();
		renderer.dispose();
		Resources.dispose();
	}



}
