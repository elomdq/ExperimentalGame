package org.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class SplashState extends State{

    float timer;

    public SplashState(GameStateManager gsm)
    {
        super(gsm);
        timer = 0;
    }

    @Override
    public void update(float delta) {
        timer += delta;

        if(timer>=1)
        {
            gsm.setState(GameStateManager.GameState.MENU);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1f,1f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {

    }
}
