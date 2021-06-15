package com.helloworld.box2dprueba.states;

import com.badlogic.gdx.Audio;
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
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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

    private Music backgroundMusic; /** NUEVO **/

    public class Evento extends ClickListener
    {
        GameStateManager gsm;

        public Evento(GameStateManager gsm)
        {this.gsm = gsm;}

        @Override
        public void clicked(InputEvent event, float x, float y){
            gsm.setState(GameStateManager.GameState.PLAY);
        }
    }

    public MenuState(GameStateManager gsm){
        super(gsm);

        stage = new Stage(new ExtendViewport(800, 600, camera));

        /*System.out.println("Width: " + camera.viewportWidth);
        System.out.println("Height: " + camera.viewportHeight);*/

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
        table.bottom().padBottom(170);
        table.add(buttonPlay).pad(10).height(40).width(224);
        table.row();
        table.add(buttonExit).pad(10).height(40).width(224);

        /** INICIO NUEVO TO++ **/

        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/IntroMusic.mp3"));
        backgroundMusic.play();
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.2f);

        /** FIN NUEVO TO++ **/
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
        //font.dispose();
        //atlas.dispose();
        stage.dispose();
        skin.dispose();
        backgroundMusic.dispose();
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

        buttonPlay.addListener(new Evento(this.gsm));

        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });
    }
}
