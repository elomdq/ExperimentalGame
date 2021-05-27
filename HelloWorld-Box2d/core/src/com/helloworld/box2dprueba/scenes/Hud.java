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

public class Hud {

    private static final int V_WIDTH = 720;
    private static final int V_HEIGHT = 480;

    public Stage stage;
    private Viewport viewport;


    private Integer keys;
    private float timeCount;
    private static Integer batteries;


    private Label worldLabel;
    private Label levelLabel;

    private  Label keysLabel;
    private Label keyLabel;

    private Label batteriesLabel;
    private Label batteryLabel;

    public Hud(SpriteBatch sb){

        keys = 0;
        batteries = 0;

        viewport = new FitViewport(V_WIDTH, V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //Se utiliza una "mesa" para organizar en pantalla la informacion de los Labels
        Table table = new Table();

        //Se alinea arriba de la pantalla
        table.top();

        //Se establece que la "mesa" ocupa toda la pantalla
        table.setFillParent(true);

        //Se define la información que tendrá cada Label
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //ToDo definir la cantidad de niveles y actualizar esta info con el nivel en cuestión//
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        keyLabel = new Label("LLAVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        keysLabel = new Label(String.format("%02d", keys), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        batteryLabel = new Label("BATERIAS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        batteriesLabel =new Label(String.format("%02d", batteries), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //Adrega los labels a la "mesa" en una primera fila
        table.add(worldLabel).expandX().padTop(10);
        table.add(keyLabel).expandX().padTop(10);
        table.add(batteryLabel).expandX().padTop(10);

        //Se crea una segunda fila para agregar la siguiente info.
        table.row();
        table.add(levelLabel).expandX();
        table.add(keysLabel).expandX();
        table.add(batteriesLabel).expandX();

        //Agrega la "mesa" a nuestro a la pantalla
        stage.addActor(table);

    }
}
