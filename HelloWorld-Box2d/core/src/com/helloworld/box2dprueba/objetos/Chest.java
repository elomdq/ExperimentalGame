package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Entity;
import com.helloworld.box2dprueba.utils.ICollision;

import static com.helloworld.box2dprueba.utils.Constants.PPM;


//ToDO: - Agregar la animacion de apertura de cofre
//      - Mostrar el elemento contenido por el cofre

public class Chest extends Entity implements ICollision {

    private ItemEquipable item;
    private boolean isClosed;
    //private Animacion animacion;

    private Sprite imagen;
    private TextureAtlas atlas;


    //Constructor
    public Chest(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation){
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.item = null;
        this.isClosed = true;

        //animacion = new Animacion("images/cofre.txt", 26, 27, 4);


        atlas = new TextureAtlas("images/cofre.txt");
        imagen = atlas.createSprite("chest-1");
    }


    //Getter & Setter
    public ItemEquipable getItem() {
        return item;
    }

    public void setItem(ItemEquipable item) {
        this.item = item;
    }


    //Demás Métodos

    public void render()
    {
        imagen.setPosition(this.getBody().getPosition().x * PPM - (26/2), this.getBody().getPosition().y* PPM - (27/2));
        imagen.draw(this.getBatch(), this.getAlpha());
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
            imagen.setRegion(atlas.findRegion("chest-4"));
       }
    }

    private void playSound(){
        if(!this.isClosed){
            //Sonido de apertura de cofre
        }
    }

    @Override
    public void collision(Fixture fixture) {
        if(isClosed){
            this.isClosed = false;
            openAnimation();
            this.item = null;
            //playSound();
            //Se muestra el elemento que contiene
        }
    }

    /**
     * Método que chequea si hay
     * un item dentro del cofre
     *
     * @return
     */
    private boolean hasItem(){
        if(this.item != null)
            return true;
        return false;
    }

    /**
     * Método que chequa si el cofre está
     * en condiciones para ser abierto
     *
     * @param tieneItem
     * @return
     */
    private boolean canBeOpened(boolean tieneItem){
        return (tieneItem && isClosed);
    }

}
