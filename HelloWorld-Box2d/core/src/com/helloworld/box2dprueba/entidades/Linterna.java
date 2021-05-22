package com.helloworld.box2dprueba.entidades;

import box2dLight.ConeLight;
import com.badlogic.gdx.physics.box2d.World;

public class Linterna extends Iluminacion{

    private ConeLight luz;

    //Constructor
    public Linterna(World mundo, float posX, float posY, float largo, float ancho, String textura, boolean estaEquipado, Float energiaRestante) {
        super(mundo, posX, posY, largo, ancho, textura, estaEquipado, energiaRestante);
    }


    //Getter & Setter
    public ConeLight getLuz() {
        return luz;
    }

    public void setLuz(ConeLight luz) {
        this.luz = luz;
    }
}
