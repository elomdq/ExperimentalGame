package com.helloworld.box2dprueba.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
    private Viewport viewport;


    private Integer lives;
    private Integer keys;
    private Integer lamps;
    private Integer batteries;
    private Integer bandages;
    private Stopwatch time;

    private Label lifeLabel;
    private Label livesLabel;

    private Label lampLabel;
    private Label lampsLabel;

    private Label keysLabel;
    private Label keyLabel;

    private Label batteriesLabel;
    private Label batteryLabel;

    private Label bandegesLabel;
    private Label bandageLabel;

    private Label timeLabel;
    private Label timesLabel;

    public Hud(SpriteBatch sb){

        lives = CANTIDAD_VIDAS;
        keys = 0;
        batteries = 0;
        lamps = 0;
        bandages = 0;
        this.time=new Stopwatch();

        viewport = new FitViewport(V_WIDTH, V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //Se utiliza una "mesa" para organizar en pantalla la informacion de los Labels
        Table table = new Table();

        //Se alinea arriba de la pantalla
        table.left();
        table.top();

        //Se establece que la "mesa" ocupa toda la pantalla
        table.setFillParent(true);

        //Se define la información que tendrá cada Label
        lifeLabel = new Label("VIDAS", new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        livesLabel = new Label(String.format("%02d", lives), new Label.LabelStyle(new BitmapFont(), Color.BLUE));

        keyLabel = new Label("LLAVES", new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        keysLabel = new Label(String.format("%02d", keys), new Label.LabelStyle(new BitmapFont(), Color.BLUE));

        bandageLabel = new Label("VENDAS", new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        bandegesLabel = new Label(String.format("%02d", bandages), new Label.LabelStyle(new BitmapFont(), Color.BLUE));

        batteryLabel = new Label("BATERIAS", new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        batteriesLabel =new Label(String.format("%02d", batteries), new Label.LabelStyle(new BitmapFont(), Color.BLUE));

        lampLabel = new Label("FAROLES", new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        lampsLabel = new Label(String.format("%02d", lamps), new Label.LabelStyle(new BitmapFont(), Color.BLUE));

        timeLabel = new Label("TIEMPO", new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        timesLabel = new Label(String.format("%02d", (int)time.elapsedTime()), new Label.LabelStyle(new BitmapFont(), Color.BLUE));

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


        table.add(lifeLabel).padTop(10);
        table.add(livesLabel).padLeft(20);
        table.getCell(livesLabel).padTop(10);
        table.row();
        table.add(keyLabel).padTop(10);
        table.add(keysLabel).padLeft(20);
        table.getCell(keysLabel).padTop(10);
        table.row();
        table.add(bandageLabel).padTop(10);
        table.add(bandegesLabel).padLeft(20);
        table.getCell(bandegesLabel).padTop(10);
        table.row();
        table.add(batteryLabel).padTop(10);
        table.add(batteriesLabel).padLeft(20);
        table.getCell(batteriesLabel).padTop(10);
        table.row();
        table.add(lampLabel).padTop(10);
        table.add(lampsLabel).padLeft(20);
        table.getCell(lampsLabel).padTop(10);
        table.row();
        table.add(timeLabel).padTop(10);
        table.add(timesLabel).padLeft(20).padTop(10);
//        table.getCell(timesLabel).padTop(10);

        //Agrega la "mesa" a nuestro a la pantalla
        stage.addActor(table);

    }

    public void update(Jugador player){

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
}
