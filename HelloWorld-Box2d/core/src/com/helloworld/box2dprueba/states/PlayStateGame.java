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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.helloworld.box2dprueba.entidades.Enemigo;
import com.helloworld.box2dprueba.entidades.Jugador;
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

    private RayHandler rayHandler;
    //private PointLight light;
    private float distance = 120/PPM;

    private ConeLight coneLight;


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
                "images/Male01.png",
                32,
                32,
                3);

        skeleton = new Enemigo(world,
                50,
                100,
                32,
                32,
                false,
                false,
                "images/skeleton.png",
                32,
                32,
                3);

        //IA
        target = new B2DSteeringEntity(jugador.getBody(), 30/PPM);
        entity = new B2DSteeringEntity(skeleton.getBody(), 30/PPM);

        Wander<Vector2> wanderSB = new Wander<>(entity)
                .setOwner(target)
                .setWanderRadius(1/PPM)
                .setWanderOffset(1/PPM)
                .setWanderRate(0.1f)
                .setFaceEnabled(false);

        Arrive<Vector2> arriveSB = new Arrive<>(entity, target).setTimeToTarget(0.1f).setArrivalTolerance(2/PPM).setDecelerationRadius(10/PPM);
        entity.setBehavior(arriveSB);

        //Seteo Luz Ambiental
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

        entity.update(delta);
        updateAnimationEnemy(skeleton);

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

        System.out.println(radiansToDegrees(anguloEntreVectores(skeleton.getBody().getPosition(), jugador.getBody().getPosition())));
        if(distanciaEntreVectores(skeleton.getBody().getPosition(), jugador.getBody().getPosition()) < coneLight.getDistance()*PPM
        &&  enfrentados(
                radiansToDegrees(anguloEntreVectores(jugador.getBody().getPosition(), skeleton.getBody().getPosition()))
                , coneLight.getDirection() - coneLight.getConeDegree()/2,
                coneLight.getDirection() + coneLight.getConeDegree()/2) )
        {
            batch.begin();
            skeleton.setStateTime(skeleton.getStateTime() + Gdx.graphics.getDeltaTime());
            skeleton.setCurrentFrame();
            batch.draw(skeleton.getCurrentFrame(skeleton.getAnimation(), skeleton.getStateTime()),
                    skeleton.getBody().getPosition().x * PPM - (32 / 2),
                    skeleton.getBody().getPosition().y * PPM - (32/2));
            batch.end();
        }

        //Inicio de Batch

        batch.begin();

        jugador.setStateTime(jugador.getStateTime() + Gdx.graphics.getDeltaTime());
        jugador.setCurrentFrame();



        batch.draw(jugador.getCurrentFrame(jugador.getAnimation(), jugador.getStateTime()),
                jugador.getBody().getPosition().x * PPM - (32/2),
                jugador.getBody().getPosition().y * PPM - (32/2));



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
            jugador.getAnimation().setFrameDuration(0.1f);

        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            horizontalForce += 1;
            jugador.setAnimation(jugador.getAnimationRight());
            jugador.getAnimation().setFrameDuration(0.1f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            verticalForce += 1;
            jugador.setAnimation(jugador.getAnimationUp());
            jugador.getAnimation().setFrameDuration(0.1f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            verticalForce -= 1;
            jugador.setAnimation(jugador.getAnimationDown());
            jugador.getAnimation().setFrameDuration(0.1f);
        }

      if(!Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.UP)
            && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT))
         jugador.getAnimation().setFrameDuration(0);

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

    public void updateAnimationEnemy(Enemigo enemigo)
    {
        float toDegrees = (float) ((float)  180 / Math.PI);
        float angle = enemigo.getBody().getAngle() * toDegrees;

        //System.out.println("angle = " + angle);
        
        if(angle>-45 && angle<45)
            enemigo.setAnimation(enemigo.getAnimationRight());
        if(angle>45 && angle<135)
            enemigo.setAnimation(enemigo.getAnimationUp());
        if(angle>135 && angle<-135)
            enemigo.setAnimation(enemigo.getAnimationLeft());
        if(angle>-135 && angle<-0)
            enemigo.setAnimation(enemigo.getAnimationDown());
    }

    public float distanciaEntreVectores(Vector2 target, Vector2 referencia)
    {
        double deltaX = target.x - referencia.x;
        double deltaY = target.y - referencia.y;

        double Hypo = Math.hypot(deltaX * PPM, deltaY * PPM);

        return (float) Math.abs(Hypo);
    }

    public float anguloEntreVectores(Vector2 target, Vector2 referencia)
    {
        double deltaX = target.x - referencia.x;
        double deltaY = target.y - referencia.y;

        double alfa = Math.atan2(deltaY * PPM, deltaX * PPM);

        return (float) alfa;
    }

    public float radiansToDegrees(float angle)
    {
        float toDegrees = (float) ((float)  180 / Math.PI);

        return angle * toDegrees;
    }

    //en grados, no radianes
    public float anguloOpuesto(float angle)
    {
        if(angle >0 )
            return angle - 180;
        else
            return angle + 180;
    }

    public boolean enfrentados(float angleTarget, float angle1, float angle2)
    {
        if(anguloOpuesto(angleTarget) >= angle1 && anguloOpuesto(angleTarget) <= angle2)
            return true;

        return false;
    }

}

