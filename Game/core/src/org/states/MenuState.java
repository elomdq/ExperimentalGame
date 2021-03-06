package org.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MenuState extends State{

    private Stage stage;
    private Table table;
    private Skin skin;

    private TextureAtlas atlas;
    private TextureRegion background;
    private BitmapFont font;

    TextButton buttonPlay;
    TextButton buttonExit;
    TextButton buttonScores;

    private Music backgroundMusic;

    public class Event extends ClickListener
    {
        GameStateManager gsm;

        public Event(GameStateManager gsm)
        {this.gsm = gsm;}
    }

    public MenuState(GameStateManager gsm){
        super(gsm);

        stage = new Stage(new ExtendViewport(800, 600, camera));

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);

        skin = new Skin();

        stage.addActor(table);

        background = new TextureRegion(new Texture("ui/Menu.jpg"));
        atlas = new TextureAtlas("ui/pruebas.txt");


        skin.addRegions(atlas);
        skin.add("background", background);

        table.setSkin(skin);
        table.setBackground("background");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/8-bit Arcade In.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 38;
        params.color = Color.WHITE;
        font = generator.generateFont(params);
        skin.add("default-font", font);

        skin.load(Gdx.files.internal("ui/uiSkin.json"));


        initiateButtons();

        buttonPlay.act(Gdx.graphics.getDeltaTime());

        //table.setDebug(true);
        table.bottom().padBottom(130);
        table.add(buttonPlay).pad(10).height(40).width(224);
        table.row();
        table.add(buttonScores).pad(10).height(40).width(224);
        table.row();
        table.add(buttonExit).pad(10).height(40).width(224);

        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/IntroMusic.mp3"));
        backgroundMusic.play();
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.2f);

    }

    public void update(float delta)
    {
        stage.act();
    }

    public void render()
    {
        Gdx.gl.glClearColor(1f,1f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    public void dispose()
    {
        backgroundMusic.dispose();
        stage.dispose();
        skin.dispose();
    }

    public void resize(int width, int height, float scale)
    {
        stage.getViewport().update(width, height, true);
    }

    public void menuUpdate(float delta)
    {

    }

    public void initiateButtons()
    {
        buttonPlay = new TextButton("Empezar", this.skin);
        buttonExit = new TextButton("Salir", this.skin);
        buttonScores = new TextButton("Scores", this.skin);

        buttonPlay.addListener(new Event(this.gsm){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gsm.setState(GameStateManager.GameState.PLAY);
            }
        });

        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        buttonScores.addListener(new Event(this.gsm){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gsm.setState(GameStateManager.GameState.SCORE);
            }
        });
    }
}
