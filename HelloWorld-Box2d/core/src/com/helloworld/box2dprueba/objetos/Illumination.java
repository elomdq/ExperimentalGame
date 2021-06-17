package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Character;


public abstract class Illumination extends EquippableItem {

    private float distance;
    private float remainingEnergy;


    //constructor
    public Illumination(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
    }


    //Getter & Setter
    public Float getRemainingEnergy() {
        return remainingEnergy;
    }

    public void setRemainingEnergy(Float remainingEnergy) {
        this.remainingEnergy = remainingEnergy;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }


    //otros metodos

    public abstract void equip(Character target);
    public abstract void unequip(Character target);
    public abstract float getDirection();
    public abstract float getConeDegree();

}
