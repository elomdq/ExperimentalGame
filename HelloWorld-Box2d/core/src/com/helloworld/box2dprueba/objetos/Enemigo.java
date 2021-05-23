package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.physics.box2d.World;

public class Enemigo extends Personaje {

    public Enemigo(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames) {
        super(world, x, y, width, height, isStatic, fixRotation, texturePath, frameWidth, frameHeight, frames);
    }

}
