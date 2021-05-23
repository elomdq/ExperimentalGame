package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.physics.box2d.World;

public class Bateria extends ItemEquipable {

    int energy;

    public Bateria(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, x, y, width, height, isStatic, fixRotation);
    }

    //setter & getter

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}

