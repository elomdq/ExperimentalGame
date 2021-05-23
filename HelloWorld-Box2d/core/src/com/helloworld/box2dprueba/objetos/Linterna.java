package com.helloworld.box2dprueba.objetos;

import box2dLight.ConeLight;
import com.badlogic.gdx.physics.box2d.World;

public class Linterna extends Iluminacion {

    private ConeLight luz;


    //Constructor
    public Linterna(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, x, y, width, height, isStatic, fixRotation);
    }

    //Getter & Setter
    public ConeLight getLuz() {
        return luz;
    }

    public void setLuz(ConeLight luz) {
        this.luz = luz;
    }
}
