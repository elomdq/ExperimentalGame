package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Batery extends ItemEquipable {

    private boolean disponibleParaUsar;

    public Batery(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.disponibleParaUsar = true;
    }

    //Getter & Setter

    public boolean isUsable() {
        return disponibleParaUsar;
    }

    public void setUsable(boolean usable) {
        this.disponibleParaUsar = usable;
    }


}

