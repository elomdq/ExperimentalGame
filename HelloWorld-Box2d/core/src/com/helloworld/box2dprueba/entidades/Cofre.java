package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Cofre extends Entidad{

    private ItemEquipable item;

    public Cofre(World mundo, float posX, float posY, float largo, float ancho, String textura) {
        super(mundo, posX, posY, largo, ancho, textura);
        this.item = agregarItemRandom();
    }

    public ItemEquipable agregarItemRandom(){

        //if(Math.random() % 2 == 0)
            //return new Bateria();

        //return new Llave();

        return null;
    }

}
