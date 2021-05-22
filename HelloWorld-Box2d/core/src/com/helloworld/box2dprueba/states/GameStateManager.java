package com.helloworld.box2dprueba.states;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {

    //referencia a la aplicacion
    private final Application app;

    private Stack<State> states;

    public enum State
    {
        SPLASH,
        MENU,
        PLAYGAME
    }

    //constructor
    public GameStateManager(PlayState playState, final Application app)
    {
        this.app = app;
        states = new Stack<State>();
        this.set(State.PLAYGAME);
    }

    //ingresamos un nuevo state a la pila
    public void push(State state)
    {
        states.push(state);
    }

    //sacamos un state de la pila
    public State pop()
    {
        return states.pop();
    }

    //sacamos un state e ingresamos a la vez otro state
    public void set(State state)
    {
        states.pop().dispose();
        states.push(state);
    }



    public Application getApplication()
    {
        return app;
    }


    //Metodos que tienen los ApplicationListeners

    //updeteamos el state que se encuentra en el tope de la pila
    public void update(float delta)
    {
        states.peek().update(delta);
    }

    //renderiza el state que se encuentra en el tope de la pila
    public void render(/*SpriteBatch sb*/)
    {
        states.peek().render(sb);
    }

    public void dispose()
    {
        for (State currentState:
             states) {
            currentState.dispose();
        }
    }

    public void resize(float w, float h)
    {
        states.peek().resize(w, h);
    }

}
