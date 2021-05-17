package com.helloworld.box2dprueba;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.helloworld.box2dprueba.utils.TiledObjectUtil;

import javax.print.attribute.standard.PagesPerMinute;

import java.awt.*;

import static com.helloworld.box2dprueba.utils.Constants.PPM;
import static java.awt.Color.*;

public class JuegoPrueba extends ApplicationAdapter {

	private boolean DEBUG = false;
	private final float SCALE = 2.0f;

	private OrthographicCamera camera;

	private World world;
	private Body player, platform;

	private Box2DDebugRenderer b2dr;

	private OrthogonalTiledMapRenderer tmr;
	private TiledMap map;

	private SpriteBatch batch;
	private Texture tex;

	private RayHandler rayHandler;
	private PointLight light;
	private float distance = 5;
	int contador = 0;
	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w/SCALE, h/SCALE);

		world = new World(new Vector2(0, 0), false);
		b2dr = new Box2DDebugRenderer();

		player = createBox(40, 40,32,32,false);
		//platform = createBox(40, 30,60, 32, true);

		map = new TmxMapLoader().load("maps/mapita.tmx"); // Devuelve un TiledMap
		tmr = new OrthogonalTiledMapRenderer(map);

		TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("colision").getObjects());

		batch = new SpriteBatch();
		tex = new Texture("images/player.png");

		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0f);


		light = new PointLight(rayHandler,100,	Color.WHITE,distance, 0 , 0);
		light.setSoftnessLength(0f);
		light.attachToBody(player);

		b2dr.setDrawBodies(false);
	}

	@Override
	public void render () {


		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tmr.render();

		b2dr.render(world, camera.combined.scl(PPM));



		rayHandler.render();
		batch.begin();
		batch.draw(tex, player.getPosition().x * PPM - (tex.getWidth()/2), player.getPosition().y * PPM - (tex.getHeight()/2));
		batch.end();
	}
	
	@Override
	public void dispose () {
		world.dispose();
		b2dr.dispose();
		map.dispose();
		tmr.dispose();
		batch.dispose();
		tex.dispose();
		rayHandler.dispose();
		light.dispose();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width/SCALE, height/SCALE);
	}

	public void update(float delta)
	{
		world.step(1/60f, 6, 2);



		inputUpdate(delta);
		cameraUpdate(delta);

		batch.setProjectionMatrix(camera.combined);

		tmr.setView(camera);

		distance *= 0.999f;
		light.setDistance(distance);

		rayHandler.update();
		rayHandler.setCombinedMatrix(camera.combined.scl(PPM), camera.position.x /  PPM, camera.position.y / PPM, camera.viewportWidth, camera.viewportHeight);
	}

	public void inputUpdate(float delta)
	{
		int horizontalForce = 0;
		int verticalForce = 0;

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			horizontalForce -= 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			horizontalForce += 1;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			//player.applyForceToCenter(0, 300, false);
			verticalForce += 1;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			//player.applyForceToCenter(0, 300, false);
			verticalForce -= 1;
		}

		player.setLinearVelocity(horizontalForce * 5, verticalForce * 5);
	}

	public void cameraUpdate(float delta) {
		Vector3 position = camera.position;
		position.x = player.getPosition().x * PPM;
		position.y = player.getPosition().y * PPM;

		camera.position.set(position);

		camera.update();
	}

	public Body createBox(int x, int y, int width, int height, boolean isStatic)
	{
		Body pBody;
		BodyDef def = new BodyDef();

		if(isStatic)
			def.type = BodyDef.BodyType.StaticBody;
		else
			def.type = BodyDef.BodyType.DynamicBody;

		def.position.set(x/PPM, y/PPM);
		def.fixedRotation = true;
		pBody = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2/PPM, height/2/PPM);

		pBody.createFixture(shape, 1.0f);
		shape.dispose();

		return pBody;
	}

}
