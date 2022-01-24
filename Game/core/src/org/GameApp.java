package org;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.states.GameStateManager;

public class GameApp extends ApplicationAdapter {

	//Debug
	private boolean DEBUG = false;

	public static final String TITLE = "Dungeon Crawler";
	public static final int V_WIDTH = 1080;
	public static final int H_HEIGHT = 720;

	private final float SCALE = 1.5f;

	private InputProcessor inputProcessor;

	private GameStateManager gsm;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private ExtendViewport viewport;



	private Music backgroundMusic; /** NUEVO **/



	@Override
	public void create () {

		camera = new OrthographicCamera();


		viewport = new ExtendViewport(1080, 720, camera);

		inputProcessor = Gdx.input.getInputProcessor();

		//camera.setToOrtho(false, V_WIDTH/SCALE, H_HEIGHT/SCALE);

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

	public Viewport getViewPort(){return viewport;}

	public GameStateManager getGameStateManager(){return gsm;}

	public InputProcessor getInputProcessor(){return inputProcessor;}

}
