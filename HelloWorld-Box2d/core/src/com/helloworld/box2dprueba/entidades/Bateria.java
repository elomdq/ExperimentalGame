package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.World;

public class Bateria extends ItemEquipable{

    public Bateria(World mundo, float posX, float posY, float largo, float ancho, String textura, boolean estaEquipado) {
        super(mundo, posX, posY, largo, ancho, textura, estaEquipado);
    }

}
