package org.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import org.entidades.Entity;

public abstract class EquippableItem extends Entity {

    private boolean isEquipped;

    //Constructor
    public EquippableItem(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.isEquipped = false;
    }

    //Getter & Setter
    public boolean getEquipped() {
        return isEquipped;
    }

    public void setIsEquipped(boolean isEquipped) {
        this.isEquipped = isEquipped;
    }
}
