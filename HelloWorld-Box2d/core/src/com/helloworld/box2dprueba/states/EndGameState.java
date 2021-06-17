package com.helloworld.box2dprueba.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.helloworld.box2dprueba.entidades.Player;
import com.helloworld.box2dprueba.score.Score;
import com.helloworld.box2dprueba.score.AuxFiles;

public class EndGameState extends State{

    private Stage stage;
    private Table table;
    private Skin skin;

    TextureRegion background;
    TextureAtlas atlas;

    private BitmapFont font, font2;

    private Score score;

    private Label tittle, scoreString, scoreLabel, description;
    private TextField name;
    private TextButton button;
    private Image image;


    public class Event extends ClickListener
    {
        GameStateManager gsm;

        public Event(GameStateManager gsm)
        {this.gsm = gsm;}

    }



    public EndGameState(GameStateManager gsm, Player player) {
        super(gsm);

        score = new Score();
        score.setScore(Score.defineScore(player));

        stage = new Stage(new ExtendViewport(1080, 720, camera));

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);

        skin = new Skin();

        stage.addActor(table);

        background = new TextureRegion(new Texture("ui/EndGame-Back.jpg"));
        atlas = new TextureAtlas("ui/pruebas.txt");

        skin.add("background", background);
        skin.addRegions(atlas);

        table.setSkin(skin);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 55;
        params.color = Color.WHITE;
        font = generator.generateFont(params);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/8-bit Arcade In.ttf"));
        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 45;
        params.color = Color.WHITE;
        font2 = generator.generateFont(params);

        skin.add("default-font", font);
        skin.add("score-font", font2);

        skin.load(Gdx.files.internal("ui/endGame.json"));


        if(player.getHealth() != 0){

            table.setBackground("background");

            tittle = new Label("¡ HAS LOGRADO ESCAPAR !", skin);

            scoreString = new Label("PUNTAJE", skin, "score");
            scoreLabel = new Label(String.format("%d", score.getScore()), skin, "score");
            scoreLabel.setFontScale(1.2f);
            scoreString.setFontScale(1.2f);

            description = new Label("Ingresa tu nombre para el historial", skin);
            description.setFontScale(0.6f);

            name = new TextField("", skin);
            name.setMaxLength(6);
            name.setAlignment(Align.center);

            button = new TextButton("Enter", skin);

            button.addListener(new Event(this.gsm){
                @Override
                public void clicked(InputEvent event, float x, float y){

                    if(name.getText() == "" || name.getText() == null )
                        return;

                    score.setName(name.getText()); // aca iria el nombre que ingresa el jugador al finalizar

                    //AuxFiles.updateScores(AuxFiles.returnSortedList(), score);
                    AuxFiles.generateJSON(score);
                    gsm.setState(GameStateManager.GameState.MENU);
                }
            });

            table.top();
            table.add(tittle).padTop(80).colspan(2);
            table.row();
            table.add(scoreString).right().padRight(10).padTop(80);
            table.add(scoreLabel).left().padLeft(10).padTop(80);
            table.row();
            table.add(description).padTop(80).colspan(2);
            table.row();
            table.add(name).width(270).padTop(30).colspan(2);
            table.row().expandY();
            table.add(button).width(224).height(40).top().padTop(30).padBottom(50).colspan(2);
        }else{
            tittle = new Label("¡ TE MORISTE !", skin);
            description = new Label("Sos malisimo", skin);
            description.setFontScale(0.6f);
            image = new Image(skin, "muerte");

            button = new TextButton("Volver al Menu", skin);

            button.addListener(new Event(this.gsm){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    gsm.setState(GameStateManager.GameState.MENU);
                }
            });

            table.add(tittle);
            table.row();
            table.add(image).padTop(60);
            table.row();
            table.add(description).padTop(60);
            table.row();
            table.add(button).height(40).width(330).padTop(80);
        }
    }

    //parar updetear el state
    public void update(float delta){
        stage.act();
    }

    //para renderizar lo necesario del state
    public void render(){
        Gdx.gl.glClearColor(0f,0f,0f,1f);
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

}
