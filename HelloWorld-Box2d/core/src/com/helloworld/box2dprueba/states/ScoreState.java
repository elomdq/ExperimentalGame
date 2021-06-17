package com.helloworld.box2dprueba.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.helloworld.box2dprueba.score.AuxFiles;
import com.helloworld.box2dprueba.score.Score;


public class ScoreState extends State{

    private Stage stage;
    private Table table;
    private Skin skin;

    private BitmapFont font, font2;

    private TextureRegion background;
    private TextureAtlas atlas;

    private Label title;
    private TextButton button;

    private java.util.List<Score> scoreList;

    public class Event extends ClickListener
    {
        GameStateManager gsm;

        public Event(GameStateManager gsm)
        {this.gsm = gsm;}

    }


    public ScoreState(GameStateManager gsm) {
        super(gsm);

        background = new TextureRegion(new Texture("ui/EndGame-Back.jpg"));
        atlas = new TextureAtlas("ui/pruebas.txt");

        skin = new Skin();
        skin.addRegions(atlas);
        skin.add("background", background);

        stage = new Stage(gsm.getApplication().getViewPort()); //pasarle un viewport
        Gdx.input.setInputProcessor(stage); //seteo como input processor al stage, para reaccionar a los clicks

        table = new Table();
        table.setFillParent(true);
        table.setSkin(skin);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 55;
        params.color = Color.WHITE;
        font = generator.generateFont(params);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/8-bit Arcade In.ttf"));
        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 50;
        params.color = Color.WHITE;
        font2 = generator.generateFont(params);

        stage.addActor(table);

        skin.add("title", font);
        skin.add("default-font", font2);

        skin.load(Gdx.files.internal("ui/score.json"));


        /** PROBANDO */

       /* scoreList = new ArrayList<>();

        scoreList.add(new Score("Carl", 20));
        scoreList.add(new Score("Soprano", 340));
        scoreList.add(new Score("Memphis", 20));
        scoreList.add(new Score("Lewis", 4350));*/

        //System.out.println(AuxFiles.readJsonFile("core\\assets\\scores\\scores.json"));
        //System.out.println(AuxFiles.scoreFromGson(AuxFiles.readJsonFile("core\\assets\\scores\\scores.json")));

        /** FIN Probando */


        title = new Label("SCORES", skin, "title");

        button = new TextButton("Menu", skin);
        button.addListener(new Event(gsm){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gsm.setState(GameStateManager.GameState.MENU);
            }
        });


        //table.setDebug(true);
        table.setBackground(skin.getDrawable("background"));

        table.top();
        table.add(title).padTop(80).padBottom(60).colspan(2).center();
        table.row();
        deploydScores(table, AuxFiles.scoreFromGson(AuxFiles.readJsonFile("core\\assets\\scores\\scores.json")));
        table.row().expandY();
        table.add(button).top().width(224).height(40).padTop(60).colspan(2);
        table.row();

    }


    //parar updetear el state
    public void update(float delta){
        stage.act();
    }

    //para renderizar lo necesario del state
    public void render(){
        stage.draw();
    }

    //para limpiar texturas, audio y otras entidades y facilitar el manejo de memoria
    public void dispose(){
        stage.dispose();
        skin.dispose();
    }

    //metodos para levantar los score del archivo
    public void deploydScores(Table table, java.util.List<Score> scores)
    {
        for (Score score:
        scores) {
            table.add(new Label(score.getName(), this.skin)).spaceRight(150).left().padBottom(30);
            table.add(new Label(score.getScoreString(), this.skin)).spaceLeft(150).right().padBottom(30);
            table.row();
        }
    }
}
