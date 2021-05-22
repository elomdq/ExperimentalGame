package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.World;

public abstract class Iluminacion extends ItemEquipable{

    private Float energiaRestante;


    //Constructor
    public Iluminacion(World mundo, float posX, float posY, float largo, float ancho, String textura, boolean estaEquipado, Float energiaRestante) {
        super(mundo, posX, posY, largo, ancho, textura, estaEquipado);
        this.energiaRestante = energiaRestante;
    }


    //Getter & Setter
    public Float getEnergiaRestante() {
        return energiaRestante;
    }

    public void setEnergiaRestante(Float energiaRestante) {
        this.energiaRestante = energiaRestante;
    }

}
