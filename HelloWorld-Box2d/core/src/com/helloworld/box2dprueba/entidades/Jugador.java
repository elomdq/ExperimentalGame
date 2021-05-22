package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.World;

import java.util.List;

public class Jugador extends Personaje{

    private boolean estaVivo;
    private Iluminacion tipoIluminacionEquipada;
    private List<ItemEquipable> mochila;


    //Constructor
    public Jugador(World mundo, float posX, float posY, float largo, float ancho, String textura) {
        //Constructor
        super(mundo, posX, posY, largo, ancho, textura);
    }


    //Getter & Setter
    public boolean isEstaVivo() {
        return estaVivo;
    }

    public void setEstaVivo(boolean estaVivo) {
        this.estaVivo = estaVivo;
    }

    public Iluminacion getTipoIluminacionEquipada() {
        return tipoIluminacionEquipada;
    }

    public void setTipoIluminacionEquipada(Iluminacion tipoIluminacionEquipada) {
        this.tipoIluminacionEquipada = tipoIluminacionEquipada;
    }

    public List<ItemEquipable> getMochila() {
        return mochila;
    }

    public void setMochila(List<ItemEquipable> mochila) {
        this.mochila = mochila;
    }

}
