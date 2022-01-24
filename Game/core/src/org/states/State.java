package org.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import org.GameApp;

public abstract class State {

    //protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStateManager gsm; //Funciona como un stack de states (pila de estados), renderiza el del tope de la pila
    protected GameApp app;

    protected SpriteBatch batch;
    protected OrthographicCamera camera;

    public State(GameStateManager gsm)
    {
        this.gsm = gsm;
        this.app = gsm.getApplication();
        batch = app.getBatch();
        camera = app.getCamera();
        mouse = new Vector3();
    }

    //metodos

    public void resize(int w, int h, float scale)
    {
        app.getViewPort().update(w,h,true);
    }

    //para manejar cualquier input ingresado por el usuario
    public void handleInput(){}

    //parar updetear el state
    public abstract void update(float delta);

    //para renderizar lo necesario del state
    public abstract void render();

    //para limpiar texturas, audio y otras entidades y facilitar el manejo de memoria
    public abstract void dispose();

}
