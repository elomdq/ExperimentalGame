package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Personaje;

public class Enemigo extends Personaje {

    public Enemigo(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames) {
        super(world, x, y, width, height, isStatic, fixRotation, texturePath, frameWidth, frameHeight, frames);
    }

    @Override
    public void collision(Fixture fixture) {
        //Falta definir qué comportamiento tendra el enemigo cuando colisiona con el jugador.
        //Deberia llamar un metodo que muestre un screamer y finalice el nivel.
    }
}
