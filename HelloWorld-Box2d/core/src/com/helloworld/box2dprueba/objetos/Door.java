package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Entity;
import com.helloworld.box2dprueba.entidades.Player;
import com.helloworld.box2dprueba.utils.ICollision;

import static com.helloworld.box2dprueba.utils.Constants.AMOUNT_KEYS;

public class Door extends Entity implements ICollision {

    private boolean isOpen;

    //Constructor
    public Door(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.isOpen = false;
    }

    //Getter & Setter
    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    @Override
    public void collision(Fixture fixture){


        if(((Player)fixture.getUserData()).getCantidadDeLlaves() >= AMOUNT_KEYS) {

            this.isOpen = true;

        }
    }
}
