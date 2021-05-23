package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.physics.box2d.World;

public class Llave extends ItemEquipable {

    //Constructor
    public Llave(World mundo, float posX, float posY, float largo, float ancho, String textura, boolean estaEquipado) {
        super(mundo, posX, posY, largo, ancho, textura, estaEquipado);
    }

}
