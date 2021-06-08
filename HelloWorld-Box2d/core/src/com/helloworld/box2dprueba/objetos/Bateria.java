package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Bateria extends ItemEquipable {

    private boolean disponibleParaUsar;

    public Bateria(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.disponibleParaUsar = true;
    }

    //Getter & Setter

    public boolean isDisponibleParaUsar() {
        return disponibleParaUsar;
    }

    public void setDisponibleParaUsar(boolean disponibleParaUsar) {
        this.disponibleParaUsar = disponibleParaUsar;
    }


}

