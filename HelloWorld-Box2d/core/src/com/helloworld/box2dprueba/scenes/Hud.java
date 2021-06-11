package com.helloworld.box2dprueba.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.helloworld.box2dprueba.entidades.Jugador;
import com.helloworld.box2dprueba.states.PlayStateGame;
import com.helloworld.box2dprueba.utils.Stopwatch;

import static com.helloworld.box2dprueba.utils.Constants.CANTIDAD_VIDAS;

public class Hud {

    private static final int V_WIDTH = 720;
    private static final int V_HEIGHT = 480;

    public Stage stage;
    private Skin skin;
    private Viewport viewport;
    private TextureAtlas atlas;
    private BitmapFont font;


    private Integer lives;
    private Integer keys;
    private Integer lamps;
    private Integer batteries;
    private Integer bandages;
    private Stopwatch time;

    private Image life;
    private Label livesLabel;

    private Image lamp;
    private Label lampsLabel;

    private Image key;
    private Label keysLabel;

    private Image mana;
    private Label batteriesLabel;

    private Image bandage;
    private Label bandegesLabel;

    private Label timesLabel;


    public Hud(SpriteBatch batch, Camera camera){

        lives = CANTIDAD_VIDAS;
        keys = 0;
        batteries = 0;
        lamps = 0;
        bandages = 0;
        this.time=new Stopwatch();

        atlas = new TextureAtlas("ui/hud.txt");

        this.viewport = new ExtendViewport(camera.viewportWidth, camera.viewportHeight);
        stage = new Stage(viewport, batch);

        skin = new Skin();
        skin.addRegions(atlas);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/8-bit Arcade In.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 30;
        params.color = Color.WHITE;
        font = generator.generateFont(params);
        skin.add("default-font", font);

        skin.load(Gdx.files.internal("ui/hudSkin.json"));


        //Se utiliza una tabla para organizar en pantalla la informacion de los Labels
        Table table = new Table();


        stage.addActor(table);
        //Se alinea arriba de la pantalla
       /* table.left();
        table.top();*/

        //Se define la información que tendrá cada Label
        livesLabel = new Label(String.format("%02d", lives), skin);
        life = new Image(skin, "vida");

        keysLabel = new Label(String.format("%02d", keys), skin);
        key = new Image(skin, "llave-zero");

        bandegesLabel = new Label(String.format("%02d", bandages), skin);
        bandage = new Image(skin, "venda-zero");

        batteriesLabel =new Label(String.format("%02d", batteries), skin);
        mana = new Image(skin, "mana-zero");

        lampsLabel = new Label(String.format("%02d", lamps), skin);
        lamp = new Image(skin, "farol-zero");

        timesLabel = new Label(String.format("%02d", (int)time.elapsedTime()), skin);


//        //Adrega los labels a la "mesa" en una primera fila
//        table.add(lampLabel).expandX().padTop(10);
//        table.add(keyLabel).expandX().padTop(10);
//        table.add(batteryLabel).expandX().padTop(10);
//
//        //Se crea una segunda fila para agregar la siguiente info.
//        table.row();
//        table.add(lampsLavel).expandX();
//        table.add(keysLabel).expandX();
//        table.add(batteriesLabel).expandX();

        table.setFillParent(true); //Se establece que la "mesa" ocupa toda la pantalla
        table.setDebug(true);



        table.add(timesLabel);
        table.row().expandX();
        table.add(livesLabel).padTop(10);
        table.add(lampsLabel).padTop(10);
        table.add(bandegesLabel).padTop(10);
        table.add(batteriesLabel).padTop(10);
        table.add(keysLabel).padTop(10);
        table.row();
        table.add(life).width(60);
        table.add(lamp);
        table.add(bandage);
        table.add(mana);
        table.add(key);
    }


    public void update(Jugador player){

        stage.act();

        this.lives = player.getVidas();
        livesLabel.setText(String.format("%02d", lives));

        this.keys = player.getCantidadDeLlaves();
        keysLabel.setText(String.format("%02d", keys));

        this.batteries = player.getCantidadDeBaterias();
        batteriesLabel.setText(String.format("%02d", batteries));

        this.lamps = player.getCantidadDeFaroles();
        lampsLabel.setText(String.format("%02d", lamps));

        this.bandages = player.getCantidadDeBandages();
        bandegesLabel.setText(String.format("%02d", bandages));

        timesLabel.setText(String.format("%02d", (int)time.elapsedTime()));
    }

    public void render()
    {
        stage.draw();
    }

    public void dispose()
    {
        //atlas.dispose();
        stage.dispose();
        skin.dispose();
    }
}
