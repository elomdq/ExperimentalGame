package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Entidad;

public class Puerta extends Entidad {

    private boolean estaAbierta;

    //Constructor
    public Puerta(World mundo, float posX, float posY, float largo, float ancho, String textura) {
        super(mundo, posX, posY, largo, ancho, textura);
        this.estaAbierta = false;
    }


    //Getter & Setter
    public boolean isEstaAbierta() {
        return estaAbierta;
    }

    public void setEstaAbierta(boolean estaAbierta) {
        this.estaAbierta = estaAbierta;
    }

}
