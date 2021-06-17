package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Entidad;
import com.helloworld.box2dprueba.entidades.Jugador;
import com.helloworld.box2dprueba.utils.ICollision;

import static com.helloworld.box2dprueba.utils.Constants.CANTIDAD_LLAVES;

public class Puerta extends Entidad implements ICollision {

    private boolean estaAbierta;

    //Constructor
    public Puerta(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.estaAbierta = false;
    }

    //Getter & Setter
    public boolean isEstaAbierta() {
        return estaAbierta;
    }

    public void setEstaAbierta(boolean estaAbierta) {
        this.estaAbierta = estaAbierta;
    }

    @Override
    public void collision(Fixture fixture){


        if(((Jugador)fixture.getUserData()).getCantidadDeLlaves() >= CANTIDAD_LLAVES) {

            this.estaAbierta = true;

        }
    }
}
