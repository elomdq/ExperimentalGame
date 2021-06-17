package com.helloworld.box2dprueba.states;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.helloworld.box2dprueba.entidades.Entidad;
import com.helloworld.box2dprueba.entidades.Jugador;
import com.helloworld.box2dprueba.objetos.*;
import com.helloworld.box2dprueba.objetos.Farol;
import com.helloworld.box2dprueba.objetos.Linterna;
import com.helloworld.box2dprueba.objetos.Cofre;
import com.helloworld.box2dprueba.scenes.Hud;
import com.helloworld.box2dprueba.utils.MyContactListener;
import com.helloworld.box2dprueba.entidades.enemigos.Banshee;
import com.helloworld.box2dprueba.entidades.enemigos.Skeleton;
import com.helloworld.box2dprueba.entidades.enemigos.Smeller;
import com.helloworld.box2dprueba.utils.Stopwatch;
import com.helloworld.box2dprueba.utils.TiledObjectUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.helloworld.box2dprueba.utils.Constants.*;
import static com.helloworld.box2dprueba.utils.CositasLindas.*;


public class PlayStateGame extends State {

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private MapProperties mapProperties;

    private Box2DDebugRenderer b2dr;
    private World world;

    private ExtendViewport viewport;

    public static Jugador jugador;
    private Skeleton skeleton1;
    private Banshee banshee;
    private Smeller smeller1;
    private Smeller smeller2;

    private Hud hud;

    private List<Cofre> chests;

    private RayHandler rayHandler;
    private float distance;

    private Linterna linterna;

    private float alpha = 1;

    private Music backgroundMusic;

    public Stopwatch stopwatch;

    private Puerta puerta;


    //Constructor
    public PlayStateGame(GameStateManager gsm) {
        super(gsm);

        Gdx.input.setInputProcessor(gsm.getApplication().getInputProcessor());

        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new MyContactListener());
        b2dr = new Box2DDebugRenderer();

        map = new TmxMapLoader().load("maps/mapita.tmx"); // Devuelve un TiledMap
        tmr = new OrthogonalTiledMapRenderer(map);
        //mapProperties = map.getProperties();
        //int width = map.getProperties().get("with", Integer.class);

        //Seteo un viewport y la camara se adapta
        viewport = new ExtendViewport(1080, 720, camera);

        //generacion de los cuerpos solidos del mapa
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collisions").getObjects());
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("doors-collision").getObjects());

        //seteo luz
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.0000001f);
        distance = DISTANCIA_LUMINARIA / PPM;

        //Creacion de personajes y objetos
        jugador = new Jugador(world,
                batch,
                160,//1952,
                32,//1760,
                32,
                32,
                false,
                false,
                "images/jugador.txt",
                32,
                32,
                3);

        /*jugador.getInventario().add(new Llave(world,batch,0,0,1,1,true, true));
        jugador.getInventario().add(new Llave(world,batch,0,0,1,1,true, true));
        jugador.getInventario().add(new Llave(world,batch,0,0,1,1,true, true));
        jugador.setVidas(1);*/


        //Seteo de Enemigos

        skeleton1 = new Skeleton(world,
                batch,
                jugador,
                1310,
                646);

        banshee = new Banshee(world,
                batch,
                jugador,
                416,//416
                1200); //1200

        smeller1 = new Smeller(world,
                batch,
                jugador,
                1324,
                448,
                7.5f);

        smeller2 = new Smeller(world,
                batch,
                jugador,
                1970,
                1510,
                3.2f);

        //Seteo de Linterna
        linterna = new Linterna(world,
                batch,
                jugador.getBody().getPosition().x,
                jugador.getBody().getPosition().y,
                4,
                10,
                false,
                false,
                rayHandler,
                distance);

        //Las siguientes dos líneas de código quizas haya que mandarlas a la clase Linterna
        linterna.getLight().setConeDegree(25);
        linterna.equipar(jugador);
        jugador.setIluminacion(linterna);
        jugador.setIluminacion(linterna); //le digo al jugador que iluminacion tiene

        //seteo de puerta de salida
        puerta = new Puerta(world,
                batch,
                1968,
                1964,
                89,
                89,
                true,
                false);
        /*puerta.getBody().getFixtureList().get(0).setDensity(0f);
        puerta.getBody().getFixtureList().get(0).setSensor(true);*/

        //seteo de cofres e items equipables
        chests = assignItems(createItems(),createChests());

        //seteo de HUD
        hud = new Hud(batch, camera);

        //seteo de musica de ambiente
        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/BackgroundMusic.mp3"));
        backgroundMusic.play();
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.02f);

        //seteo de reloj
        stopwatch = new Stopwatch();


        b2dr.setDrawBodies(false);
    }

    @Override
    public void update(float delta) {
        world.step(1/60f, 6, 2);

        cameraUpdate();

        //seteando limites de camara
        float startX = camera.viewportWidth / 2;
        float startY = camera.viewportHeight / 2;
        float width = 67 * 32 - startX*2;
        float height = 63 * 32 - startY*2;
        bounderies(camera,startX,startY, width, height);

        jugador.update(delta);
        skeleton1.update(delta);
        banshee.update(delta);
        smeller1.update(delta);
        smeller2.update(delta);

//      ciclo utilizado para el comportamiento de los faroles
        for(ItemEquipable item : jugador.getInventario()){
            if(item instanceof Farol){
                ((Farol)item).update(jugador);
            }
        }

        rotatePlayerToMouse(camera);

        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);

        hud.update(jugador);

        for(Cofre chest : chests){
            updateAlpha( chest, jugador);
        }

        linterna.update();

        rayHandler.update();
        rayHandler.setCombinedMatrix(camera.combined.scl(PPM), camera.position.x /  PPM, camera.position.y / PPM, camera.viewportWidth, camera.viewportHeight);

        endGameEvaluation();

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();

        rayHandler.render();

        //batch.enableBlending();

        batch.begin();

        skeleton1.render();
        jugador.render();
        banshee.render();
        smeller1.render();
        smeller2.render();

        for(Cofre chest : chests){
            chest.render();
        }

        batch.end();

        b2dr.render(world, camera.combined); //por alguna razon si dejo el .scl(PPM) no me hace los bodies, muy raaarro

        batch.setProjectionMatrix(hud.stage.getCamera().combined);

        hud.render();

    }

    @Override
    public void resize(int w, int h, float scale) {
        super.resize(w, h, scale);
        hud.resize(w,h,scale);
    }

    @Override
    public void dispose() {
        world.dispose();
        map.dispose();
        b2dr.dispose();
        tmr.dispose();

        //batch.dispose();
        rayHandler.dispose();
        linterna.dispose();

        jugador.dispose();

        banshee.dispose();
        skeleton1.dispose();
        smeller1.dispose();
        smeller2.dispose();

        backgroundMusic.dispose();

        hud.dispose();

        puerta.dispose();
        for (Cofre cofre : chests) {
            cofre.dispose();
        }
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
        float deltaY = mousePos.y- jugador.getBody().getPosition().y *PPM;

        float nuevoAngulo = (float) Math.atan2(deltaY, deltaX);

        jugador.getBody().setAngularVelocity(0);
        jugador.getBody().setTransform(jugador.getBody().getPosition().x, jugador.getBody().getPosition().y, nuevoAngulo);
    }


    /*********Cofres e Items**********/
    private List<ItemEquipable> createItems(){

        List<ItemEquipable> list = new ArrayList<>();

        for(int i = 0 ; i < CANTIDAD_LLAVES ; i++){
            list.add(new Llave(world,batch,DEFAULT_POS,DEFAULT_POS,10,10,true,false));
        }

        for(int i = 0 ; i < CANTIDAD_VENDAS ; i++){
            list.add(new Bandage(world,batch,DEFAULT_POS,DEFAULT_POS,10,10,true,false));
        }

        for(int i = 0 ; i < CANTIDAD_BATERIAS ; i++){
            list.add(new Bateria(world,batch,DEFAULT_POS,DEFAULT_POS,10,10,true,false));
        }

        for(int i = 0 ; i < CANTIDAD_FAROLES ; i++){
            list.add(new Farol(world,batch,DEFAULT_POS,DEFAULT_POS,1,1,true,false,rayHandler,0));
        }

        return list;
    }

    private List<Cofre> createChests(){

        List<Cofre> list = new ArrayList<>();

        list.add( new Cofre(world,batch,POS_CHEST_1.x,POS_CHEST_1.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_2.x ,POS_CHEST_2.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_3.x,POS_CHEST_3.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_4.x,POS_CHEST_4.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_5.x,POS_CHEST_5.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_6.x,POS_CHEST_6.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_7.x,POS_CHEST_7.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_8.x,POS_CHEST_8.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_9.x,POS_CHEST_9.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_10.x,POS_CHEST_10.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_11.x,POS_CHEST_11.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_12.x,POS_CHEST_12.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_13.x,POS_CHEST_13.y,25,26,true,false));
        list.add( new Cofre(world,batch,POS_CHEST_14.x,POS_CHEST_14.y,25,26,true,false));

        return list;
    }

    private List<Cofre> assignItems(List<ItemEquipable> items , List<Cofre> chests){

        int nullItem = 4;

        for(Cofre chest : chests){

            if(nullItem != 0){
                if(((int)(Math.random()*11) % 2) == 0){
                    nullItem--;
                }else{
                    chest.setItem( items.remove((int)(Math.random() * items.size())) );
                }
            }else{
                chest.setItem( items.remove((int)(Math.random() * items.size())) );
            }
        }

        return chests;
    }

    //metodo para actualizar alpha de los Sprites
    public void updateAlpha(Entidad objeto, Jugador target) {
        float ratio, coefA, coefB;
        float min = 0.65f, max = 1f;

        coefA = 1 / (min - max);
        coefB = -1 * max * coefA;
        ratio = distanciaEntreVectores(objeto.getBody().getPosition(), target.getBody().getPosition()) / target.getIluminacion().getDistance();

        if (ratio < min)
            objeto.setAlpha(1f);
        else if (ratio > max)
            objeto.setAlpha(0f);
        else
            objeto.setAlpha(coefA * ratio + coefB);

        if (!enfrentados(radiansToDegrees(anguloEntreVectores(target.getBody().getPosition(), objeto.getBody().getPosition()))
                , target.getIluminacion().getDirection() - target.getIluminacion().getConeDegree(),
                target.getIluminacion().getDirection() + target.getIluminacion().getConeDegree()))

            objeto.setAlpha(0f);
    }


    public void bounderies(Camera camera, float startX, float startY, float width, float height)
    {
        Vector3 position = camera.position;

        if(position.x < startX)
        {
            position.x = startX;
        }

        if(position.y < startY)
        {
            position.y = startY;
        }

        if(position.x > startX + width)
        {
            position.x = startX + width;
        }

        if(position.y > startY + height)
        {
            position.y = startY + height;
        }

        camera.position.set(position);
        camera.update();
    }


    private void endGameEvaluation(){
        if (jugador.getVidas()==0 || puerta.isEstaAbierta()){
            Stopwatch.setEndGame(stopwatch.elapsedTime());

            boolean isPlayerAlive = true;

            if(jugador.getVidas()==0){
                isPlayerAlive=false;
            }

            gsm.setEndState(gsm.getEndState(jugador));
        }

    }

}



