package org.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Battery extends EquippableItem {

    private boolean isUsable;

    public Battery(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.isUsable = true;
    }

    //Getter & Setter

    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        this.isUsable = usable;
    }


}

