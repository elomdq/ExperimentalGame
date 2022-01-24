package org.states;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import org.entidades.Entity;
import org.entidades.Player;
import org.objetos.*;
import org.objetos.Lantern;
import org.objetos.Flashlight;
import org.objetos.Chest;
import org.scenes.Hud;
import org.score.Score;
import org.utils.MyContactListener;
import org.entidades.enemigos.Banshee;
import org.entidades.enemigos.Skeleton;
import org.entidades.enemigos.Smeller;
import org.utils.Stopwatch;
import org.utils.TiledObjectUtil;
import java.util.ArrayList;
import java.util.List;

import static org.utils.Constants.*;
import static org.utils.BeautifulThings.*;


public class PlayStateGame extends State {

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Box2DDebugRenderer b2dr;
    private World world;

    private ExtendViewport viewport;

    public static Player player;
    private Skeleton skeleton1;
    private Banshee banshee;
    private Smeller smeller1;
    private Smeller smeller2;

    private Hud hud;

    private List<Chest> chests;

    private RayHandler rayHandler;
    private float distance;

    private Flashlight flashlight;

    private float alpha = 1;

    private Music backgroundMusic;

    public Stopwatch stopwatch;

    private Door door;


    //Constructor
    public PlayStateGame(GameStateManager gsm) {
        super(gsm);

        Gdx.input.setInputProcessor(gsm.getApplication().getInputProcessor());

        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new MyContactListener());
        b2dr = new Box2DDebugRenderer();

        map = new TmxMapLoader().load("maps/mapita.tmx"); // Devuelve un TiledMap
        tmr = new OrthogonalTiledMapRenderer(map);

        //Seteo un viewport y la camara se adapta
        viewport = new ExtendViewport(1080, 720, camera);

        //generacion de los cuerpos solidos del mapa
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collisions").getObjects());
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("doors-collision").getObjects());

        //seteo luz
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.0000000f);
        distance = LIGHT_DISTANCE / PPM;

        //Creacion de personajes y objetos
        player = new Player(world,
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


        //Seteo de Enemigos

        skeleton1 = new Skeleton(world,
                batch,
                player,
                1310,
                646);

        banshee = new Banshee(world,
                batch,
                player,
                416,
                1200);

        smeller1 = new Smeller(world,
                batch,
                player,
                1324,
                448,
                7.5f);

        smeller2 = new Smeller(world,
                batch,
                player,
                1970,
                1510,
                3.2f);

        //Seteo de Linterna
        flashlight = new Flashlight(world,
                batch,
                player.getBody().getPosition().x,
                player.getBody().getPosition().y,
                4,
                10,
                false,
                false,
                rayHandler,
                distance);

        //Las siguientes dos líneas de código quizas haya que mandarlas a la clase Linterna
        flashlight.getLight().setConeDegree(25);
        flashlight.equip(player);
        player.setIluminacion(flashlight);
        player.setIluminacion(flashlight); //le digo al jugador que iluminacion tiene

        //seteo de puerta de salida
        door = new Door(world,
                batch,
                1968,
                1964,
                89,
                89,
                true,
                false);

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

        Score.defineScore(player);

        //seteando limites de camara
        float startX = camera.viewportWidth / 2;
        float startY = camera.viewportHeight / 2;
        float width = 67 * 32 - startX*2;
        float height = 63 * 32 - startY*2;
        /*bounderies(camera,startX,startY, width, height);*/

        player.update(delta);
        skeleton1.update(delta);
        banshee.update(delta);
        smeller1.update(delta);
        smeller2.update(delta);

//      ciclo utilizado para el comportamiento de los faroles
        for(EquippableItem item : player.getInventory()){
            if(item instanceof Lantern){
                ((Lantern)item).update(player);
            }
        }

        rotatePlayerToMouse(camera);

        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);

        hud.update(player);

        for(Chest chest : chests){
            updateAlpha( chest, player);
        }

        flashlight.update();

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
        player.render();
        banshee.render();
        smeller1.render();
        smeller2.render();

        for(Chest chest : chests){
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

        rayHandler.dispose();
        flashlight.dispose();

        player.dispose();

        banshee.dispose();
        skeleton1.dispose();
        smeller1.dispose();
        smeller2.dispose();

        backgroundMusic.dispose();

        hud.dispose();

        door.dispose();
        for (Chest chest : chests) {
            chest.dispose();
        }
    }

    public void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = player.getBody().getPosition().x * PPM;
        position.y = player.getBody().getPosition().y * PPM;

        camera.position.set(position);

        camera.update();
    }

    private void rotatePlayerToMouse(OrthographicCamera camera) {

        Vector3 mousePos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        float deltaX = mousePos.x - player.getBody().getPosition().x *PPM;
        float deltaY = mousePos.y- player.getBody().getPosition().y *PPM;

        float nuevoAngulo = (float) Math.atan2(deltaY, deltaX);

        player.getBody().setAngularVelocity(0);
        player.getBody().setTransform(player.getBody().getPosition().x, player.getBody().getPosition().y, nuevoAngulo);
    }


    /*********Cofres e Items**********/
    private List<EquippableItem> createItems(){

        List<EquippableItem> list = new ArrayList<>();

        for(int i = 0; i < AMOUNT_KEYS; i++){
            list.add(new Key(world,batch,DEFAULT_POS,DEFAULT_POS,10,10,true,false));
        }

        for(int i = 0; i < AMOUNT_BANDAGES; i++){
            list.add(new Bandage(world,batch,DEFAULT_POS,DEFAULT_POS,10,10,true,false));
        }

        for(int i = 0; i < AMOUNT_BATTERIES; i++){
            list.add(new Battery(world,batch,DEFAULT_POS,DEFAULT_POS,10,10,true,false));
        }

        for(int i = 0; i < AMOUNT_LANTERN; i++){
            list.add(new Lantern(world,batch,DEFAULT_POS,DEFAULT_POS,1,1,true,false,rayHandler,0));
        }

        return list;
    }

    private List<Chest> createChests(){

        List<Chest> list = new ArrayList<>();

        list.add( new Chest(world,batch,POS_CHEST_1.x,POS_CHEST_1.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_2.x ,POS_CHEST_2.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_3.x,POS_CHEST_3.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_4.x,POS_CHEST_4.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_5.x,POS_CHEST_5.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_6.x,POS_CHEST_6.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_7.x,POS_CHEST_7.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_8.x,POS_CHEST_8.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_9.x,POS_CHEST_9.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_10.x,POS_CHEST_10.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_11.x,POS_CHEST_11.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_12.x,POS_CHEST_12.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_13.x,POS_CHEST_13.y,25,26,true,false));
        list.add( new Chest(world,batch,POS_CHEST_14.x,POS_CHEST_14.y,25,26,true,false));

        return list;
    }

    private List<Chest> assignItems(List<EquippableItem> items , List<Chest> chests){

        int nullItem = chests.size() - AMOUNT_ITEMS;

        for(Chest chest : chests){

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
    public void updateAlpha(Entity objeto, Player target) {
        float ratio, coefA, coefB;
        float min = 0.65f, max = 1f;

        coefA = 1 / (min - max);
        coefB = -1 * max * coefA;
        ratio = distanceBetweenVectors(objeto.getBody().getPosition(), target.getBody().getPosition()) / target.getIluminacion().getDistance();

        if (ratio < min)
            objeto.setAlpha(1f);
        else if (ratio > max)
            objeto.setAlpha(0f);
        else
            objeto.setAlpha(coefA * ratio + coefB);

        if (!faced(radiansToDegrees(angleBetweenVectors(target.getBody().getPosition(), objeto.getBody().getPosition()))
                , target.getIluminacion().getDirection() - target.getIluminacion().getConeDegree(),
                target.getIluminacion().getDirection() + target.getIluminacion().getConeDegree()))

            objeto.setAlpha(0f);
    }

    /*public void bounderies(Camera camera, float startX, float startY, float width, float height)
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
    }*/

    private void endGameEvaluation(){
        if (player.getHealth()==0 || door.isOpen()){
            Stopwatch.setEndGame(stopwatch.elapsedTime());

            boolean isPlayerAlive = true;

            if(player.getHealth()==0){
                isPlayerAlive=false;
            }

            gsm.setEndState(gsm.getEndState(player));
        }

    }

}



