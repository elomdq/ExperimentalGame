package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.physics.box2d.World;

public abstract class Iluminacion extends ItemEquipable {

    private Float energiaRestante;

    //constructor
    public Iluminacion(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, x, y, width, height, isStatic, fixRotation);
    }


    //Getter & Setter
    public Float getEnergiaRestante() {
        return energiaRestante;
    }

    public void setEnergiaRestante(Float energiaRestante) {
        this.energiaRestante = energiaRestante;
    }

}
