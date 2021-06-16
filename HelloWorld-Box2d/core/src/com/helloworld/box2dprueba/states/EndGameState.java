package com.helloworld.box2dprueba.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class EndGameState extends State{

    private Stage stage;
    private Table table;
    private Skin skin;
    TextureRegion background;

    BitmapFont font, font2;

    Label titulo, puntajeString, puntaje;
    TextField nombre;


    public EndGameState(GameStateManager gsm, boolean isPlayerAlive) {
        super(gsm);

        stage = new Stage(new ExtendViewport(1080, 720, camera));

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);

        skin = new Skin();

        stage.addActor(table);

        background = new TextureRegion(new Texture("ui/EndGame-Back.jpg"));

        skin.add("background", background);

        table.setSkin(skin);
        table.setBackground("background");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 55;
        params.color = Color.WHITE;
        font = generator.generateFont(params);

        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("ui/8-bit Arcade In.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params2.size = 45;
        params2.color = Color.WHITE;
        font2 = generator2.generateFont(params2);

        skin.add("default-font", font);
        skin.add("score-font", font2);

        skin.load(Gdx.files.internal("ui/endGame.json"));

        if(isPlayerAlive){
            titulo = new Label("¡ HAS LOGRADO ESCAPAR !", skin);
        }else{
            titulo = new Label("¡ HAS MUERTO !", skin);
        }

        puntajeString = new Label("PUNTAJE", skin, "score");

        table.top();
        table.add(titulo).padTop(80);
        table.row();
        table.add(puntajeString);

    }


    //parar updetear el state
    public void update(float delta){
        stage.act();
    }

    //para renderizar lo necesario del state
    public void render(){
        Gdx.gl.glClearColor(1f,1f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.draw();
    }

    //para limpiar texturas, audio y otras entidades y facilitar el manejo de memoria
    public void dispose(){
        stage.dispose();
        skin.dispose();
    }

    public void resize(int width, int height, float scale)
    {
        stage.getViewport().update(width, height, true);
    }

    //metodos para ingresar nombre y guardar score
}
