package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Character;


public abstract class Iluminacion extends ItemEquipable {

    private float distance;
    private float energiaRestante;


    //constructor
    public Iluminacion(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
    }


    //Getter & Setter
    public Float getEnergiaRestante() {
        return energiaRestante;
    }

    public void setEnergiaRestante(Float energiaRestante) {
        this.energiaRestante = energiaRestante;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distancia) {
        this.distance = distancia;
    }


    //otros metodos


    public abstract void equipar(Character target);
    public abstract void desequipar(Character target);
    public abstract float getDirection();
    public abstract float getConeDegree();

}
