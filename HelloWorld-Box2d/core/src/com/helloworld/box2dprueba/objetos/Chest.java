package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Entity;
import com.helloworld.box2dprueba.utils.ICollision;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public class Chest extends Entity implements ICollision {

    private EquippableItem item;
    private boolean isClosed;

    private Sprite image;
    private TextureAtlas atlas;


    //Constructor
    public Chest(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation){
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.item = null;
        this.isClosed = true;
        atlas = new TextureAtlas("images/cofre.txt");
        image = atlas.createSprite("chest-1");
    }


    //Getter & Setter
    public EquippableItem getItem() {
        return item;
    }

    public void setItem(EquippableItem item) {
        this.item = item;
    }


    //Demás Métodos

    public void render()
    {
        image.setPosition(this.getBody().getPosition().x * PPM - (26/2), this.getBody().getPosition().y* PPM - (27/2));
        image.draw(this.getBatch(), this.getAlpha());
    }

    public void update(float delta)
    {
        //imagen.setPosition(this.getBody().getPosition().x * PPM - (26/2), this.getBody().getPosition().y* PPM - (27/2));
    }

    /*private Sprite spriteRenderizado()
    {
        if(isClosed)
        {
           return imagen;
        }
        else
        {
           *//* if(animacion.getStateTime() <= 0.5f)
            {
                return animacion.getCurrentFrame();
            }
            else
            {*//*
                return imagen;
            *//*}*//*
        }
    }*/

    private void openAnimation(){
       if(!this.isClosed){
            image.setRegion(atlas.findRegion("chest-4"));
       }
    }

    @Override
    public void collision(Fixture fixture) {
        if(isClosed){
            this.isClosed = false;
            openAnimation();
            this.item = null;
        }
    }
}
