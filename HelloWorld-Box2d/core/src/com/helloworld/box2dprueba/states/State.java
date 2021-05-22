package com.helloworld.box2dprueba.states;



import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

    //protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStateManager gsm; //Funciona como un stack de states (pila de estados), renderiza el del tope de la pila

    protected State(GameStateManager gsm)
    {
        this.gsm = gsm;

        mouse = new Vector3();
    }

    //metodos

    //para manejar cualquier input ingresado por el usuario
    public abstract void handleInput();

    //parar updetear el state
    public abstract void update(float delta);

    //para renderizar lo necesario del state
    public abstract void render(SpriteBatch sb);

    //para limpiar texturas, audio y otras entidades y facilitar el manejo de memoria
    public abstract void dispose();

    public abstract void resize(int w, int h);
}
