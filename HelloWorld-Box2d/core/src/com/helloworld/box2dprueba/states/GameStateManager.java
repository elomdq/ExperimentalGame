package com.helloworld.box2dprueba.states;

import com.helloworld.box2dprueba.JuegoPrueba;

import java.util.Stack;

public class GameStateManager {

    //referencia a la aplicacion
    private final JuegoPrueba app;

    private Stack<State> states;

    public enum GameState
    {
        SPLASH,
        MENU,
        PLAY
    }

     public GameStateManager(final JuegoPrueba app)
    {
        this.app = app;
        states = new Stack<State>();
        this.setState(GameState.SPLASH);
    }


    public void push(State state)
    {
        states.push(state);
    }


    public State pop()
    {
        return states.pop();
    }


    //sacamos un state e ingresamos a la vez otro state
    public void setState(GameState state)
    {
        if(!states.empty())
            states.pop().dispose();
        states.push(getState(state));
    }

    public State getState(GameState state)
    {
        switch(state)
        {
            case SPLASH: return new SplashState(this);
            case MENU:
                break;
            case PLAY: return new PlayStateGame(this);
            default:
                break;
        }

        return null;
    }


    public JuegoPrueba getApplication()
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
    public void render()
    {
        states.peek().render();
    }

    public void dispose()
    {
        for (State currentState:
             states) {
            currentState.dispose();
        }
    }

    public void resize(int w, int h, float scale)
    {
        states.peek().resize(w, h, scale);
    }

}
