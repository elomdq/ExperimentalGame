package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Entity;

public abstract class ItemEquipable extends Entity {

    private boolean estaEquipado;

    //Constructor
    public ItemEquipable(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.estaEquipado = false;
    }


    //Getter & Setter
    public boolean getEstaEquipado() {
        return estaEquipado;
    }

    public void setIsEquipped(boolean estaEquipado) {
        this.estaEquipado = estaEquipado;
    }
}
