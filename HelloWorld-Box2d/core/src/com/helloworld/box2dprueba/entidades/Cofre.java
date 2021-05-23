package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Cofre extends Entidad{

    private ItemEquipable item;


    //Constructor
    public Cofre(World mundo, float posX, float posY, float largo, float ancho, String textura) {
        super(mundo, posX, posY, largo, ancho, textura);
        this.item = agregarItemRandom();
    }


    //Getter & Setter
    public ItemEquipable getItem() {
        return item;
    }

    public void setItem(ItemEquipable item) {
        this.item = item;
    }


    //Demás Métodos
    public ItemEquipable agregarItemRandom(){

        //if(Math.random() % 2 == 0)
            //return new Bateria();

        //return new Llave();

        return null;
    }

}
