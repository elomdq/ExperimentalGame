package com.helloworld.box2dprueba.states;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ScoreState extends State{

    private Stage stage;
    private Table table;
    private Skin skin;

    public ScoreState(GameStateManager gsm) {
        super(gsm);
    }


    //parar updetear el state
    public void update(float delta){}

    //para renderizar lo necesario del state
    public void render(){}

    //para limpiar texturas, audio y otras entidades y facilitar el manejo de memoria
    public void dispose(){}

    //metodos para levantar los score del archivo

}
