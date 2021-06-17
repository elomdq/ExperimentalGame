package com.helloworld.box2dprueba.states;

import com.helloworld.box2dprueba.JuegoApp;
import com.helloworld.box2dprueba.entidades.Player;

import java.util.Stack;

public class GameStateManager {


    private final JuegoApp app; //referencia a la aplicacion
    private Stack<State> states; //Pila de States del juego

    public enum GameState
    {
        SPLASH,
        MENU,
        PLAY,
        SCORE,
        ENDGAME
    }

     public GameStateManager(final JuegoApp app)
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

   /* public void setState(GameState state, Jugador player)
    {
        if(!states.empty())
            states.pop().dispose();
        states.push(getState(state, player));
    }*/

    public State getState(GameState state)
    {
        switch(state)
        {
            case SPLASH: return new SplashState(this);
            case MENU: return new MenuState(this);
            case PLAY: return new PlayStateGame(this);
            case SCORE: return new ScoreState(this);
            default:
                break;
        }

        return null;
    }


    //Metodos particulares para crear el endState del juego
    public EndGameState getEndState(Player player)
    {
        return new EndGameState(this, player);
    }


    public void setEndState(EndGameState endState)
    {
        if(!states.empty())
            states.pop().dispose();
        states.push(endState);
    }


    public JuegoApp getApplication()
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
