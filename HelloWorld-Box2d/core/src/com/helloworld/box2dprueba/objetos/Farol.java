package com.helloworld.box2dprueba.objetos;

import box2dLight.PointLight;
import com.badlogic.gdx.physics.box2d.World;

public class Farol extends Iluminacion {

    private PointLight luz;


    //Constructor
    public Farol(World mundo, float posX, float posY, float largo, float ancho, String textura, boolean estaEquipado, Float energiaRestante) {
        super(mundo, posX, posY, largo, ancho, textura, estaEquipado, energiaRestante);
    }


    //Getter & Setter
    public PointLight getLuz() {
        return luz;
    }

    public void setLuz(PointLight luz) {
        this.luz = luz;
    }

}
