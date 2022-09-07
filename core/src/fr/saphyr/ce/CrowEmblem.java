package fr.saphyr.ce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.core.Resources;
import fr.saphyr.ce.entities.Slime;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.graphics.fonts.Fonts;
import fr.saphyr.ce.maps.Maps;
import fr.saphyr.ce.worlds.World;

public final class CrowEmblem extends ApplicationAdapter {

	private Renderer renderer;
	private World world;

	private Table table;
	private Stage stage;

	@Override
	public void create() {
		Resources.load();


		world = new World(Maps.get("maps/map1.tmx"), new Vector3(10, 10, 3));

		world.addEntities(new Slime(
				Textures.get("textures/slime/slime_spritesheet.png"),
				world, new Vector3(2, 2, 0), new int[]{ 2, 3, 8, 10, 11} ));

		stage = new Stage();

		table = new Table();
		var style = new Label.LabelStyle(Fonts.get("fonts/CinzelDecorative-Black.ttf"), Color.WHITE);
		table.add(new Label("Crow Emblem", style));
		table.setDebug(true);

		stage.addActor(table);
		renderer = Renderer.orthogonal(world);
	}

	public void update(final float dt) {
		world.update(dt);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.CLEAR);
		renderer.begin();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		if (false) {
			world.render(renderer);
			update(Gdx.graphics.getDeltaTime());
		}

		renderer.end();
	}

	@Override
	public void dispose() {
		renderer.dispose();
		world.dispose();
		stage.dispose();
		Resources.dispose();
	}



}
