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

    private Label titulo, puntajeString, puntaje, descripcion;
    private TextField nombre;
    private TextButton button;
    private Image image;


    public class Evento extends ClickListener
    {
        GameStateManager gsm;

        public Evento(GameStateManager gsm)
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


        //table.setDebug(true);

        if(player.getHealth() != 0){

            table.setBackground("background");

            titulo = new Label("¡ HAS LOGRADO ESCAPAR !", skin);

            puntajeString = new Label("PUNTAJE", skin, "score");
            puntaje = new Label(String.format("%d", score.getScore()), skin, "score");
            puntaje.setFontScale(1.2f);
            puntajeString.setFontScale(1.2f);

            descripcion = new Label("Ingresa tu nombre para el historial", skin);
            descripcion.setFontScale(0.6f);

            nombre = new TextField("", skin);
            nombre.setMaxLength(6);
            nombre.setAlignment(Align.center);

            button = new TextButton("Enter", skin);

            button.addListener(new Evento(this.gsm){
                @Override
                public void clicked(InputEvent event, float x, float y){

                    if(nombre.getText() == "" || nombre.getText() == null )
                        return;

                    score.setName(nombre.getText()); // aca iria el nombre que ingresa el jugador al finalizar

                    AuxFiles.updateScores(AuxFiles.returnSortedList(), score);
                    gsm.setState(GameStateManager.GameState.MENU);
                }
            });

            table.top();
            table.add(titulo).padTop(80).colspan(2);
            table.row();
            table.add(puntajeString).right().padRight(10).padTop(80);
            table.add(puntaje).left().padLeft(10).padTop(80);
            table.row();
            table.add(descripcion).padTop(80).colspan(2);
            table.row();
            table.add(nombre).width(270).padTop(30).colspan(2);
            table.row().expandY();
            table.add(button).width(224).height(40).top().padTop(30).padBottom(50).colspan(2);
        }else{
            titulo = new Label("¡ TE MORISTE !", skin);
            descripcion = new Label("Sos malisimo", skin);
            descripcion.setFontScale(0.6f);
            image = new Image(skin, "muerte");

            button = new TextButton("Volver al Menu", skin);

            button.addListener(new Evento(this.gsm){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    gsm.setState(GameStateManager.GameState.MENU);
                }
            });

            table.add(titulo);
            table.row();
            table.add(image).padTop(60);
            table.row();
            table.add(descripcion).padTop(60);
            table.row();
            table.add(button).height(40).width(330).padTop(80);
        }
    }

    /*public EndGameState(GameStateManager gsm) {
        this(gsm);
    }*/


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
        /*font.dispose();
        font2.dispose();*/
        stage.dispose();
        skin.dispose();
    }

    public void resize(int width, int height, float scale)
    {
        stage.getViewport().update(width, height, true);
    }

    //metodos para ingresar nombre y guardar score
//    private void generateJSON(){
//
//        Gson gson = new Gson();
//
//        File file = new File("score.json");
//
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
//
//            gson.toJson(score,Score.class,bw);
//
//            bw.close();
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
//
//    }

}
