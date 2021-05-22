package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.World;

public abstract class ItemEquipable extends Entidad {

    private boolean estaEquipado;

    public ItemEquipable(World mundo, float posX, float posY, float largo, float ancho, String textura, boolean estaEquipado) {
        super(mundo, posX, posY, largo, ancho, textura);
        this.estaEquipado = estaEquipado;
    }

}
