package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.World;

public class Linterna extends Iluminacion{

    public Linterna(World mundo, float posX, float posY, float largo, float ancho, String textura, boolean estaEquipado, Float energiaRestante) {
        super(mundo, posX, posY, largo, ancho, textura, estaEquipado, energiaRestante);
    }

}
