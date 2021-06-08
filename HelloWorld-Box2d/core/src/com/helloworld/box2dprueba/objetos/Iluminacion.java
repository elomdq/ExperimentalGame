package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.helloworld.box2dprueba.entidades.Personaje;

public abstract class Iluminacion extends ItemEquipable {

    private float energiaRestante;
    private float distancia;


    //constructor
    public Iluminacion(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation, float distancia) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.distancia = distancia;
    }


    //Getter & Setter
    public Float getEnergiaRestante() {
        return energiaRestante;
    }

    public void setEnergiaRestante(Float energiaRestante) {
        this.energiaRestante = energiaRestante;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    //otros metodos

    public float getDistance()
    {
        return this.distancia;
    }

    public abstract void equipar(Personaje target);
    public abstract void desequipar(Personaje target);
    public abstract float getDirection();
    public abstract float getConeDegree();

}
