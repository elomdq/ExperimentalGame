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

public class Hud {

    private static final int V_WIDTH = 720;
    private static final int V_HEIGHT = 480;

    public Stage stage;
    private Viewport viewport;


    private Integer keys;
    private Integer lamps;
    private Integer batteries;


    private Label lampLabel;
    private Label lampsLavel;

    private  Label keysLabel;
    private Label keyLabel;

    private Label batteriesLabel;
    private Label batteryLabel;

    public Hud(SpriteBatch sb){

        keys = 0;
        batteries = 0;
        lamps = 0;

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
        lampLabel = new Label("FAROLES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lampsLavel = new Label(String.format("%02d", keys), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        keyLabel = new Label("LLAVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        keysLabel = new Label(String.format("%02d", keys), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        batteryLabel = new Label("BATERIAS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        batteriesLabel =new Label(String.format("%02d", batteries), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

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


        table.add(lampLabel).padTop(10);
        table.add(lampsLavel).padLeft(20);
        table.getCell(lampsLavel).padTop(10);
        table.row();
        table.add(keyLabel).padTop(10);
        table.add(keysLabel).padLeft(20);
        table.getCell(keysLabel).padTop(10);
        table.row();
        table.add(batteryLabel).padTop(10);
        table.add(batteriesLabel).padLeft(20);
        table.getCell(batteriesLabel).padTop(10);

        //add our table to the stage
        stage.addActor(table);


        //Agrega la "mesa" a nuestro a la pantalla
        stage.addActor(table);

    }

    public void update(Jugador player){
        if(player.getCantidadDeLlaves() > this.keys)
            this.keys = player.getCantidadDeLlaves();
        keysLabel.setText(String.format("%02d", keys));

        if(player.getCantidadDeBaterias() > this.batteries)
            this.batteries = player.getCantidadDeBaterias();
        batteriesLabel.setText(String.format("%02d", batteries));

        if(player.getCantidadDeFaroles() > this.lamps)
            this.lamps = player.getCantidadDeFaroles();
        lampsLavel.setText(String.format("%02d", lamps));
    }
}
