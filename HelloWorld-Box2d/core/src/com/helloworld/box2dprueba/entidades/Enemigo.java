package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Personaje;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public abstract class Enemigo extends Personaje {

    public Enemigo(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames) {
        super(world, x, y, width, height, isStatic, fixRotation, texturePath, frameWidth, frameHeight, frames);
    }

    public void configSteeringBehavior(float maxLinearSpeed, float maxLinearAcceleration, float maxAngularSpeed, float maxAngularAcceleration){
        this.getSteeringBehavior().setMaxLinearSpeed(maxLinearSpeed/PPM);
        this.getSteeringBehavior().setMaxLinearAcceleration(maxLinearAcceleration/PPM);
        this.getSteeringBehavior().setMaxAngularSpeed(maxAngularSpeed/PPM);
        this.getSteeringBehavior().setMaxAngularAcceleration(maxAngularAcceleration/PPM);
    };

}
