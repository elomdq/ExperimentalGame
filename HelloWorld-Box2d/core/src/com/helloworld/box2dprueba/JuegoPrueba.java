package com.helloworld.box2dprueba;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.helloworld.box2dprueba.states.GameStateManager;

public class JuegoPrueba extends ApplicationAdapter {

	//Debug
	private boolean DEBUG = false;

	private final float SCALE = 1f;

	private GameStateManager gsm;
	private OrthographicCamera camera;

	private SpriteBatch batch;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w/SCALE, h/SCALE);

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
