package com.helloworld.box2dprueba.objetos;

import box2dLight.PointLight;
import com.badlogic.gdx.physics.box2d.World;

public class Farol extends Iluminacion {

    private PointLight luz;


    //Constructor
    public Farol(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, x, y, width, height, isStatic, fixRotation);
    }


    //Getter & Setter
    public PointLight getLuz() {
        return luz;
    }

    public void setLuz(PointLight luz) {
        this.luz = luz;
    }

}
