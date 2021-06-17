package com.helloworld.box2dprueba.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.helloworld.box2dprueba.entidades.Player;
import com.helloworld.box2dprueba.utils.Stopwatch;

import static com.helloworld.box2dprueba.utils.Constants.AMOUNT_LIVES;

public class Hud {

    private static final int V_WIDTH = 720;
    private static final int V_HEIGHT = 480;

    public Stage stage;
    private Skin skin;
    private Viewport viewport;
    private TextureAtlas atlas;
    private BitmapFont font, fontTimer;


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

        lives = AMOUNT_LIVES;
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
        params.size = 35;
        params.color = Color.WHITE;
        font = generator.generateFont(params);
        skin.add("default-font", font);

        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("ui/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params2.size = 35;
        params2.color = Color.WHITE;
        fontTimer = generator2.generateFont(params2);
        skin.add("timer", fontTimer);

        skin.load(Gdx.files.internal("ui/hudSkin.json")); //asignando json al skin

        //Se utiliza una tabla para organizar en pantalla la informacion de los Labels
        Table table = new Table();
        stage.addActor(table);


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


        timesLabel = new Label(String.format("%02d%c%02d", (int) Stopwatch.getMinutes(), 58, (int) Stopwatch.getSeconds()), skin, "timer");
        //timesLabel.setFontScale(1.3f);


        table.setFillParent(true); //Se establece que la "mesa" ocupa toda la pantalla
        //table.setDebug(true);

        table.add(timesLabel).expandX().colspan(5).expandY().padTop(20).center().top();
        table.row().expandX();
        table.add(livesLabel).padLeft(396).padTop(10).spaceBottom(3);
        table.add(lampsLabel).padTop(10).spaceBottom(3).padLeft(10).padRight(5);
        table.add(bandegesLabel).padTop(10).spaceBottom(3).padLeft(5).padRight(5);
        table.add(batteriesLabel).padTop(10).spaceBottom(3).padLeft(5).padRight(10);
        table.add(keysLabel).padRight(396).padTop(10).spaceBottom(3);
        table.row();
        table.add(life).width(45).height(45).padLeft(396).right().padBottom(20);
        table.add(lamp).width(45).height(45).padLeft(10).padRight(5).padBottom(20);
        table.add(bandage).width(45).height(45).padLeft(5).padRight(5).padBottom(20);
        table.add(mana).width(45).height(45).padLeft(5).padRight(10).padBottom(20);
        table.add(key).width(45).height(45).padRight(396).left().padBottom(20);
    }


    public void update(Player player){

        stage.act();
        time.elapsedTime();

        this.lives = player.getHealth();
        livesLabel.setText(String.format("%02d", lives));

        this.keys = player.getCantidadDeLlaves();
        keysLabel.setText(String.format("%02d", keys));

        this.batteries = player.getCantidadDeBaterias();
        batteriesLabel.setText(String.format("%02d", batteries));

        this.lamps = player.getCantidadDeFaroles();
        lampsLabel.setText(String.format("%02d", lamps));

        this.bandages = player.getCantidadDeBandages();
        bandegesLabel.setText(String.format("%02d", bandages));

        timesLabel.setText(String.format("%02d%c%02d", (int)Stopwatch.getMinutes(), 58 ,(int)Stopwatch.getSeconds()));

        if(this.keys > 0)
            key.setDrawable(this.skin, "llave");
        else
            key.setDrawable(this.skin, "llave-zero");

        if(this.bandages > 0)
            bandage.setDrawable(this.skin, "venda");
        else
            bandage.setDrawable(this.skin, "venda-zero");

        if(this.batteries > 0)
            mana.setDrawable(this.skin, "mana");
        else
            mana.setDrawable(this.skin, "mana-zero");

        if(this.lamps > 0)
            lamp.setDrawable(this.skin, "farol");
        else
            lamp.setDrawable(this.skin, "farol-zero");
    }

    public void render()
    {
        stage.draw();
    }

    public void resize(int width, int height, float scale)
    {
        stage.getViewport().update(width, height, true);
    }

    public void dispose()
    {
        //atlas.dispose();
        stage.dispose();
        skin.dispose();
    }
}
