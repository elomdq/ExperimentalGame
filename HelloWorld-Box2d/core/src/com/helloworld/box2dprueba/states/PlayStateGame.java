package com.helloworld.box2dprueba.states;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import com.helloworld.box2dprueba.entidades.Enemigo;
import com.helloworld.box2dprueba.entidades.Jugador;
import com.helloworld.box2dprueba.utils.TiledObjectUtil;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public class PlayStateGame extends State {

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Box2DDebugRenderer b2dr;
    private World world;

    private Jugador jugador;
    private Enemigo skeleton;

    private RayHandler rayHandler;
    //private PointLight light;
    private float distance = 5;

    private ConeLight coneLight;

    public PlayStateGame(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();

        map = new TmxMapLoader().load("maps/mapita.tmx"); // Devuelve un TiledMap
        tmr = new OrthogonalTiledMapRenderer(map);

        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("colision").getObjects());

        jugador = new Jugador(world,
                40,
                40,
                32,
                32,
                false,
                false,
                "images/Male01.png",
                32,
                32,
                3);

        skeleton = new Enemigo(world,
                50,
                60,
                32,
                32,
                false,
                false,
                "images/skeleton.png",
                32,
                32,
                3);

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0f);

        //light = new PointLight(rayHandler,100,   Color.WHITE,distance, 0 , 0);
        //light.setSoftnessLength(0f);
        //light.attachToBody(player);

        coneLight = new ConeLight(rayHandler, 100, Color.WHITE, distance, 0 , 0, jugador.getBody().getAngle(), 30);
        coneLight.attachToBody(jugador.getBody());
        coneLight.setSoftnessLength(0f);

        b2dr.setDrawBodies(false);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float delta) {
        world.step(1/60f, 6, 2);

        inputUpdate(delta);
        cameraUpdate();

        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);

           /*distance *= 0.999f;
      light.setDistance(distance);*/

        rayHandler.update();
        rayHandler.setCombinedMatrix(camera.combined.scl(PPM), camera.position.x /  PPM, camera.position.y / PPM, camera.viewportWidth, camera.viewportHeight);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();

        b2dr.render(world, camera.combined.scl(PPM));

        rayHandler.render();

        //Inicio de Batch

        batch.begin();

        jugador.setStateTime(jugador.getStateTime() + Gdx.graphics.getDeltaTime());
        jugador.setCurrentFrame();

        skeleton.setStateTime(skeleton.getStateTime() + Gdx.graphics.getDeltaTime());
        skeleton.setCurrentFrame();

        batch.draw(jugador.getCurrentFrame(jugador.getAnimation(), jugador.getStateTime()),
                jugador.getBody().getPosition().x * PPM - (32/2),
                jugador.getBody().getPosition().y * PPM - (32/2));

        batch.draw(skeleton.getCurrentFrame(skeleton.getAnimation(), skeleton.getStateTime()),
                skeleton.getBody().getPosition().x * PPM - (32 / 2),
                skeleton.getBody().getPosition().y * PPM - (32/2));

        batch.end();
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        map.dispose();
        tmr.dispose();
        batch.dispose();
        rayHandler.dispose();
        //light.dispose();
        coneLight.dispose();
        jugador.dispose();
        skeleton.dispose();
    }

    public void inputUpdate(float delta)
    {
        int horizontalForce = 0;
        int verticalForce = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            horizontalForce -= 1;
            jugador.setAnimation(jugador.getAnimationLeft());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            horizontalForce += 1;
            jugador.setAnimation(jugador.getAnimationRight());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            verticalForce += 1;
            jugador.setAnimation(jugador.getAnimationUp());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            verticalForce -= 1;
            jugador.setAnimation(jugador.getAnimationDown());
        }

      /*if(!Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.UP)
            && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT))
         animation.setPlayMode();*/

        jugador.getBody().setLinearVelocity(horizontalForce * 5, verticalForce * 5);

        rotatePlayerToMouse(camera);
    }


    private void rotatePlayerToMouse(OrthographicCamera camera) {

        Vector3 mousePos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        float deltaX = mousePos.x - jugador.getBody().getPosition().x *PPM;
        float deltaY = mousePos.y - jugador.getBody().getPosition().y *PPM;

        float nuevoAngulo = (float) Math.atan2(deltaY, deltaX);

        jugador.getBody().setAngularVelocity(0);
        jugador.getBody().setTransform(jugador.getBody().getPosition().x, jugador.getBody().getPosition().y, nuevoAngulo);
    }


    public void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = jugador.getBody().getPosition().x * PPM;
        position.y = jugador.getBody().getPosition().y * PPM;

        camera.position.set(position);

        camera.update();
    }

}

