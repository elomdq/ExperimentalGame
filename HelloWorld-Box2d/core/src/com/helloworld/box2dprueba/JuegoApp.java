package com.helloworld.box2dprueba;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

	private InputProcessor inputProcessor;

	private GameStateManager gsm;
	private OrthographicCamera camera;

	private ExtendViewport viewport;

	private SpriteBatch batch;

	private Music backgroundMusic; /** NUEVO **/



	@Override
	public void create () {
		//float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();



		//viewport = new ExtendViewport(800, 400, camera);

		inputProcessor = Gdx.input.getInputProcessor();

		camera.setToOrtho(false, V_WIDTH/SCALE, H_HEIGHT/SCALE);

		batch = new SpriteBatch();

		gsm = new GameStateManager(this);

//		/** INICIO NUEVO TO++ **/
//
//		this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/BackgroundMusic.mp3"));
//		backgroundMusic.play();
//		backgroundMusic.setLooping(true);
//		backgroundMusic.setVolume(0.02f);
//
//		/** FIN NUEVO TO++ **/
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
//		backgroundMusic.dispose();
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

	public GameStateManager getGameStateManager(){return gsm;}

	public InputProcessor getInputProcessor(){return inputProcessor;}

}
