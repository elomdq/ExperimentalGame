package com.helloworld.box2dprueba;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.helloworld.box2dprueba.states.GameStateManager;

public class JuegoApp extends ApplicationAdapter {

	//Debug
	private boolean DEBUG = false;

	public static final String TITLE = "Dungeon Crawler";
	public static final int V_WIDTH = 800;
	public static final int H_HEIGHT = 600;

	private final float SCALE = 1.5f;

	private GameStateManager gsm;
	private OrthographicCamera camera;

	private ExtendViewport viewport;

	private SpriteBatch batch;

	@Override
	public void create () {
		//float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();

		//viewport = new ExtendViewport(800, 400, camera);

		camera.setToOrtho(false, V_WIDTH/SCALE, H_HEIGHT/SCALE);

		batch = new SpriteBatch();

		gsm = new GameStateManager(this);
	}

	@Override
	public void render () {
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
	}

	@Override
	public void dispose () {
		gsm.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		gsm.resize(width, height, SCALE);
	}

	public OrthographicCamera getCamera()
	{
		return camera;
	}

	public SpriteBatch getBatch()
	{
		return batch;
	}

}
