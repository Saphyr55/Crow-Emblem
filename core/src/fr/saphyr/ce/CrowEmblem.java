package fr.saphyr.ce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.saphyr.ce.core.Logger;

public final class CrowEmblem extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture slime;
	private TextureRegion[][] slimeFrames;
	private Animation<TextureRegion> slimeAnimation;
	private boolean slimeSelected;
	private float slimeStateTime;

	private OrthographicCamera camera;
	private float rotationSpeed;


	@Override
	public void create () {
		batch = new SpriteBatch();

		// Slime
		final int SLIME_COL = 3;
		final int SLIME_ROW = 1;
		slimeStateTime = 0;
		slimeSelected = false;
		slime = new Texture("slime_spritesheet.png");
		slimeFrames = TextureRegion.split(slime, slime.getWidth() / SLIME_COL, slime.getHeight() / SLIME_ROW);
		var frames = new TextureRegion[SLIME_COL * SLIME_ROW];
		int index = 0;
		for (int i = 0; i < SLIME_ROW; i++) {
			for (int j = 0; j < SLIME_COL; j++) {
				frames[index++] = slimeFrames[i][j];
			}
		}
		slimeAnimation = new Animation<>(100 / 1000f, frames);

		// Camera
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		Logger.debug(Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.CLEAR,true);
		batch.begin();
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		// Slime
		slimeStateTime += Gdx.graphics.getDeltaTime();
		TextureRegion slimeCurrentFrame = slimeAnimation.getKeyFrame(slimeStateTime, true);
		batch.draw(slimeCurrentFrame, 0, 0, 0, 0, 32, 32, 2, 2, 0);

		batch.end();
		update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(final int width, final int height) {
		camera.viewportWidth = Gdx.graphics.getWidth();
		camera.viewportHeight = Gdx.graphics.getHeight();
		camera.update();
	}

	private void handleInput(final float dt) {

		Logger.debug(camera.zoom);
		float zoomVelocity = .025f;
		float minZoom = .5f;
		float maxZoom = 2f;

/*		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (camera.zoom < maxZoom) camera.zoom += zoomVelocity;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			if (camera.zoom > minZoom) camera.zoom -= zoomVelocity;
		}*/
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, -3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, 3, 0);
		}

/*		camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100 / camera.viewportWidth);
		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;
		camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth,100- effectiveViewportWidth);
		camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight, 100 - effectiveViewportHeight);*/
	}

	public void update(final float dt) {
		handleInput(dt);
		if (slimeSelected && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			slimeSelected = false;
			Logger.info("Slime deselect");
		}

		else if (!slimeSelected && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			slimeSelected = true;
			Logger.info("Slime select");
		}

	}

	@Override
	public void dispose () {
		batch.dispose();
		slime.dispose();
	}
}
