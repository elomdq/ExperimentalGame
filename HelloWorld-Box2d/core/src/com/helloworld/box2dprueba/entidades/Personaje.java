package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.World;

public abstract class Personaje extends Entidad{

    public Personaje(World mundo, float posX, float posY, float largo, float ancho, String textura) {
        super(mundo, posX, posY, largo, ancho, textura);
    }

}
