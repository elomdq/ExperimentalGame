package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Entidad;

public abstract class ItemEquipable extends Entidad {

    private boolean estaEquipado;

    //Constructor
    public ItemEquipable(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, x, y, width, height, isStatic, fixRotation);
        this.estaEquipado = false;
    }


    //Getter & Setter
    public boolean isEstaEquipado() {
        return estaEquipado;
    }

    public void setEstaEquipado(boolean estaEquipado) {
        this.estaEquipado = estaEquipado;
    }
}
