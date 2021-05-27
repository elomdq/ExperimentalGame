package com.helloworld.box2dprueba.states;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.helloworld.box2dprueba.entidades.Enemigo;
import com.helloworld.box2dprueba.entidades.Jugador;
import com.helloworld.box2dprueba.entities.B2DSteeringEntity;

import com.helloworld.box2dprueba.utils.TiledObjectUtil;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;

import static com.helloworld.box2dprueba.utils.Constants.PPM;
import static com.helloworld.box2dprueba.utils.CositasLindas.*;


public class PlayStateGame extends State {

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Box2DDebugRenderer b2dr;
    private World world;

    private B2DSteeringEntity entity, target;

    private Jugador jugador;
    private Enemigo skeleton;

    private RayHandler rayHandler;
    //private PointLight light;
    private float distance = 220/PPM;

    private ConeLight coneLight;

    private float alpha =1;

    //Constructor
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
                30,
                30,
                false,
                false,
                "images/sprites2.txt",
                32,
                32,
                3);

        skeleton = new Enemigo(world,
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

        //Seteo Luz Ambiental
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0f);

        //light = new PointLight(rayHandler,100,   Color.WHITE,distance, 0 , 0);
        //light.setSoftnessLength(0f);
        //light.attachToBody(player);

        coneLight = new ConeLight(rayHandler, 100, Color.WHITE, distance, 0 , 0, jugador.getBody().getAngle(), 25);
        coneLight.attachToBody(jugador.getBody());
        coneLight.setSoftnessLength(0f);

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

        alpha = updateAlphaEnemigo(skeleton, coneLight);

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


        skeleton.setStateTime(skeleton.getStateTime() + Gdx.graphics.getDeltaTime());
        skeleton.setCurrentFrame();

        jugador.setStateTime(jugador.getStateTime() + Gdx.graphics.getDeltaTime());
        jugador.setCurrentFrame();

        /*System.out.println("Skeleton: " + skeleton.distanciaAlTarget(skeleton.getTarget().getBody().getPosition()));
        System.out.println("luz: " + coneLight.getDistance());*/

        if(skeleton.distanciaAlTarget(skeleton.getTarget().getBody().getPosition()) < coneLight.getDistance()
        &&  enfrentados(
                radiansToDegrees(anguloEntreVectores(jugador.getBody().getPosition(), skeleton.getBody().getPosition()))
                , coneLight.getDirection() - coneLight.getConeDegree(),
                coneLight.getDirection() + coneLight.getConeDegree()) )
        {
            rayHandler.render();

            batch.enableBlending();

            batch.begin();


            /*batch.draw((TextureRegion) jugador.getCurrentFrame(jugador.getAnimation(), jugador.getStateTime()),
                    jugador.getBody().getPosition().x * PPM - (32/2),
                    jugador.getBody().getPosition().y * PPM - (32/2));

            batch.draw(skeleton.getCurrentFrame(skeleton.getAnimation(), skeleton.getStateTime()),
                    skeleton.getBody().getPosition().x * PPM - (30/2),
                    skeleton.getBody().getPosition().y * PPM - (30/2));*/

            jugador.getCurrentFrame(jugador.getAnimation(), jugador.getStateTime())
                    .setPosition(jugador.getBody().getPosition().x * PPM - (32/2),jugador.getBody().getPosition().y * PPM - (32/2));
            jugador.getCurrentFrame(jugador.getAnimation(), jugador.getStateTime()).draw(batch, 1f);

            skeleton.getCurrentFrame(skeleton.getAnimation(), skeleton.getStateTime())
                    .setPosition(skeleton.getBody().getPosition().x * PPM - (32/2),skeleton.getBody().getPosition().y * PPM - (32/2));
            skeleton.getCurrentFrame(skeleton.getAnimation(), skeleton.getStateTime()).draw(batch, alpha);


           batch.enableBlending();
        }
        else
        {
            batch.begin();


            batch.draw(skeleton.getCurrentFrame(skeleton.getAnimation(), skeleton.getStateTime()),
                    skeleton.getBody().getPosition().x * PPM - (32 / 2),
                    skeleton.getBody().getPosition().y * PPM - (32/2));

            batch.end();

            rayHandler.render();

            batch.begin();

            batch.draw(jugador.getCurrentFrame(jugador.getAnimation(), jugador.getStateTime()),
                    jugador.getBody().getPosition().x * PPM - (32/2),
                    jugador.getBody().getPosition().y * PPM - (32/2));


        }
        batch.end();

        b2dr.render(world, camera.combined); //por alguna razon si dejo el .scl(PPM) no me hace los bodies, muy raaarro

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
        //light.dispose();
        coneLight.dispose();
        jugador.dispose();
        skeleton.dispose();
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

    public float updateAlphaEnemigo(Enemigo enemigo, ConeLight coneLight)
    {
        //establecemos una relacion minima para un alpha=1 de 0,8 - entonces distnacia/distanciaLuz = 0,8 -> alpha=1
        //y una relacion maxima de 1.4 para alpha = 0;
        //y hacemos una funcion lineal con esos datos alpha = ratio 1/0.6 - 0.8/0.6

        float alpha;
        float ratio, coefA, coefB;
        float min=0.7f, max=1.4f;

        coefA = 1 / (max - min);
        coefB = -1 * coefA * min;
        ratio = enemigo.distanciaAlTarget(enemigo.getTarget().getBody().getPosition()) / coneLight.getDistance();

        if(ratio<min)
            alpha=1;
        else if(ratio>max)
            alpha=0f;
        else
            alpha = coefA * ratio + coefB;
        return alpha;
    }


}

