package fr.saphyr.ce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import fr.saphyr.ce.core.ConsoleLogger;
import fr.saphyr.ce.core.Logger;

import java.util.Iterator;

public final class CrowEmblem extends ApplicationAdapter {

	private static final Logger logger = Logger.create(ConsoleLogger.class);

	private SpriteBatch batch;
	private Texture img;
	private Array<Rectangle> raindrops;
	private long lastDropTime;

	private boolean slimeSelected;
	private OrthographicCamera camera;

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("slime_spritesheet.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 780, 520);
		slimeSelected = false;
		raindrops = new Array<>();
		spawnRaindrop();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		batch.begin();
		batch.draw(img, 0, 0);
		for(Rectangle raindrop: raindrops) {
			batch.draw(img, raindrop.x, raindrop.y);
		}
		batch.end();
		update(Gdx.graphics.getDeltaTime());
	}

	public void update(float dt) {
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

		for (Iterator<Rectangle> i = raindrops.iterator(); i.hasNext(); ) {
			Rectangle raindrop = i.next();
			raindrop.y -= 200 * dt;
			if(raindrop.y + 64 < 0) i.remove();
		}

		if (slimeSelected && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			slimeSelected = false;
			System.out.println("Slime deselect");
		}

		else if (!slimeSelected && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			slimeSelected = true;
			System.out.println("Slime select");
		}

	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
