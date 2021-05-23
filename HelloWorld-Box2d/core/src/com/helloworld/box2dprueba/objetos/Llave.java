package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.physics.box2d.World;

public class Llave extends ItemEquipable {


    //Constructor
    public Llave(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, x, y, width, height, isStatic, fixRotation);
    }

}
