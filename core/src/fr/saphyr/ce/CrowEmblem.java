package fr.saphyr.ce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.saphyr.ce.entities.Slime;
import fr.saphyr.ce.graphics.Textures;
import fr.saphyr.ce.maps.Maps;
import fr.saphyr.ce.worlds.World;

public final class CrowEmblem extends ApplicationAdapter {

	private Slime slime;

	private Renderer renderer;
	private World world;

	@Override
	public void create() {
		world = new World(Maps.get("maps/map1.tmx"));

		world.addEntities(new Slime(
				Textures.get("slime/slime_spritesheet.png"), world,
				new Vector3(2, 2, 0), new int[]{ 2, 3, 8, 10}));

		renderer = Renderer.orthogonal(world);
	}

	public void update(final float dt) {
		world.update(dt);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.CLEAR);
		renderer.begin();
		world.render(renderer);
		update(Gdx.graphics.getDeltaTime());
		renderer.end();
	}

	@Override
	public void dispose() {
		renderer.dispose();
		world.dispose();
	}



}
