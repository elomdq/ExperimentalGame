package com.helloworld.box2dprueba.states;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.helloworld.box2dprueba.entidades.Enemigo;
import com.helloworld.box2dprueba.entidades.Jugador;
import com.helloworld.box2dprueba.objetos.Linterna;
import com.helloworld.box2dprueba.objetos.Cofre;
import com.helloworld.box2dprueba.scenes.Hud;
import com.helloworld.box2dprueba.utils.MyContactListener;
import com.helloworld.box2dprueba.entities.B2DSteeringEntity;
import com.helloworld.box2dprueba.utils.TiledObjectUtil;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import static com.helloworld.box2dprueba.utils.Constants.PPM;


public class PlayStateGame extends State {

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Box2DDebugRenderer b2dr;
    private World world;

    private B2DSteeringEntity entity, target;

    private Jugador jugador;
    private Enemigo skeleton;

    //El siguiente código es de prueba, hay que borrarlo después
    /***************HUD****************/
    Hud hud;
    /***************HUD****************/

    /*******Cofres*******/
    private Cofre cofre;
    private Cofre cofre2;
    private Cofre cofre3;
    private Cofre cofre4;
    private Texture cofreTexture;
    /*******Cofres*******/


    private RayHandler rayHandler;
    //private PointLight light;
    private float distance = 220/PPM;

    private ConeLight coneLight;
    private Linterna linterna;

    private float alpha =1;

    //Constructor
    public PlayStateGame(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new MyContactListener());
        b2dr = new Box2DDebugRenderer();

        map = new TmxMapLoader().load("maps/mapita.tmx"); // Devuelve un TiledMap
        tmr = new OrthogonalTiledMapRenderer(map);

        //generacion de los cuerpos solidos del mapa
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("colision").getObjects());

        //Creacion de personajes
        jugador = new Jugador(world,
                batch,
                40,
                40,
                30,
                30,
                false,
                false,
                "images/sprites2.txt",
                32,
                32,
                3);

        skeleton = new Enemigo(world,
                batch,
                120,
                400,
                15,
                15,
                false,
                false,
                "images/sprites.txt",
                32,
                32,
                3,
                jugador);
        //IA
        target = new B2DSteeringEntity(jugador.getBody(), 10/PPM);
        entity = new B2DSteeringEntity(skeleton.getBody(), 10/PPM);

        Wander<Vector2> wanderSB = new Wander<>(entity)
                .setOwner(target)
                .setWanderRadius(10/PPM)
                .setWanderOffset(10/PPM)
                .setWanderRate(0.1f)
                .setFaceEnabled(false);

        Arrive<Vector2> arriveSB = new Arrive<>(entity, target).setTimeToTarget(0.2f).setArrivalTolerance(1f).setDecelerationRadius(10/PPM);
        entity.setBehavior(arriveSB);

        //El siguiente código es de prueba, hay que borrarlo después
        /***************HUD****************/
        hud = new Hud(batch);
        /***************HUD****************/

        /*******Cofres*******/
        cofreTexture = new Texture("images/cofre.png");

        cofre = new Cofre(world,
                batch,
                100,
                60,
                30,
                30,
                true,
                false);

        cofre2 = new Cofre(world,
                batch,
                100,
                150,
                30,
                30,
                true,
                false);

        cofre3 = new Cofre(world,
                batch,
                250,
                150,
                30,
                30,
                true,
                false);

        cofre4 = new Cofre(world,
                batch,
                250,
                200,
                30,
                30,
                true,
                false);
        /*******Cofres*******/


        //Seteo Luz Ambiental
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0f);

        //light = new PointLight(rayHandler,100,   Color.WHITE,distance, 0 , 0);
        //light.setSoftnessLength(0f);
        //light.attachToBody(player);

        //seteo luz linterna
        linterna = new Linterna (world,
                batch,
                jugador.getBody().getPosition().x,
                jugador.getBody().getPosition().y,
                32,
                32,
                false,
                false,
                rayHandler,
                distance);
        linterna.getLinterna().setConeDegree(25);
        linterna.equipar(jugador);

        jugador.setIluminacion(linterna); //le digo al jugador que iluminacion tiene

        b2dr.setDrawBodies(false);
        //b2dr.setDrawVelocities(true);
        //b2dr.setDrawAABBs(true);
        //b2dr.setDrawContacts(true);
    }


    @Override
    public void update(float delta) {
        world.step(1/60f, 6, 2);

        cameraUpdate();

        entity.update(delta);
        skeleton.update(delta);
        jugador.update(delta);

        rotatePlayerToMouse(camera);

        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);

         /*distance *= 0.999f;
         light.setDistance(distance);*/

        //El siguiente código es de prueba, hay que borrarlo después
        /***************HUD****************/
        hud.update(jugador);
        /***************HUD****************/

        rayHandler.update();
        rayHandler.setCombinedMatrix(camera.combined.scl(PPM), camera.position.x /  PPM, camera.position.y / PPM, camera.viewportWidth, camera.viewportHeight);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();

        rayHandler.render();

        batch.enableBlending();

        batch.begin();

        skeleton.render();
        jugador.render();

        //El siguiente código es de prueba, hay que borrarlo después
        /*******Cofre*******/
        batch.draw(cofreTexture,cofre.getBody().getPosition().x * PPM - (30/2), cofre.getBody().getPosition().y * PPM - (30/2));
        batch.draw(cofreTexture,cofre2.getBody().getPosition().x * PPM - (30/2), cofre2.getBody().getPosition().y * PPM - (30/2));
        batch.draw(cofreTexture,cofre3.getBody().getPosition().x * PPM - (30/2), cofre3.getBody().getPosition().y * PPM - (30/2));
        batch.draw(cofreTexture,cofre4.getBody().getPosition().x * PPM - (30/2), cofre4.getBody().getPosition().y * PPM - (30/2));
        /*******Cofre*******/

        batch.end();


        b2dr.render(world, camera.combined); //por alguna razon si dejo el .scl(PPM) no me hace los bodies, muy raaarro

        //El siguiente código es de prueba, hay que borrarlo después
        /***************HUD****************/
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        /***************HUD****************/

        batch.disableBlending();
    }

    @Override
    public void resize(int w, int h, float scale) {
        super.resize(w, h, scale);
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        map.dispose();
        tmr.dispose();
        batch.dispose();
        rayHandler.dispose();
        coneLight.dispose();
        jugador.dispose();
        skeleton.dispose();
        linterna.dispose();
    }

    public void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = jugador.getBody().getPosition().x * PPM;
        position.y = jugador.getBody().getPosition().y * PPM;

        camera.position.set(position);

        camera.update();
    }

    private void rotatePlayerToMouse(OrthographicCamera camera) {

        Vector3 mousePos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        float deltaX = mousePos.x - jugador.getBody().getPosition().x *PPM;
        float deltaY = mousePos.y - jugador.getBody().getPosition().y *PPM;

        float nuevoAngulo = (float) Math.atan2(deltaY, deltaX);

        jugador.getBody().setAngularVelocity(0);
        jugador.getBody().setTransform(jugador.getBody().getPosition().x, jugador.getBody().getPosition().y, nuevoAngulo);
    }


}

