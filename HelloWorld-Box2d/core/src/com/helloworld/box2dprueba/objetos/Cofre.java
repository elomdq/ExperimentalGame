package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Entidad;
import com.helloworld.box2dprueba.utils.IColision;

public class Cofre extends Entidad implements IColision {

    private ItemEquipable item;


    //Constructor
    public Cofre(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, x, y, width, height, isStatic, fixRotation);
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

    @Override
    public void colisionar(Fixture fixtureColisionada) {

    }
}
