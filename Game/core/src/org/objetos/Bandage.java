package org.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Bandage extends EquippableItem {

    private int healAmmount;

    public Bandage(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);

        this.healAmmount=1;

    }


    public int getHealAmmount() {
        return healAmmount;
    }
}
