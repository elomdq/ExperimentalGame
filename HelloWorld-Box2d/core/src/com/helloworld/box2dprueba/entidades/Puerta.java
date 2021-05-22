package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Puerta extends Entidad{

    private boolean estaAbierta;

    public Puerta(World mundo, float posX, float posY, float largo, float ancho, String textura) {
        super(mundo, posX, posY, largo, ancho, textura);
        this.estaAbierta = false;
    }

}
