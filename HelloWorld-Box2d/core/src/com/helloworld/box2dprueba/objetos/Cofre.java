package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Entidad;
import com.helloworld.box2dprueba.utils.ICollision;


//ToDO: - Agregar la animacion de apertura de cofre
//      - Mostrar el elemento contenido por el cofre
//      - Hacer funcion que agregue un item random

public class Cofre extends Entidad implements ICollision {

    private ItemEquipable item;
    private boolean isClosed;


    //Constructor
    public Cofre(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, x, y, width, height, isStatic, fixRotation);
        this.item = addItemRandom();
        this.isClosed = true;
    }


    //Getter & Setter
    public ItemEquipable getItem() {
        return item;
    }

    public void setItem(ItemEquipable item) {
        this.item = item;
    }


    //Demás Métodos
    public ItemEquipable addItemRandom(){
        //Posible solucion:
        //if(Math.random() % 2 == 0)
            //return new Bateria();

        //return new Llave();

        return null;
    }

//    public ItemEquipable giveItem(){
//
//        this.item = null;
//
//        return item;
//    }

    private void openAnimation(){
        if(!this.isClosed){
            //Animacion de apertura de cofre
        }
    }

    private void playSound(){
        if(!this.isClosed){
            //Sonido de apertura de cofre
        }
    }

    @Override
    public void collision(Fixture fixture) {
        if(canBeOpened(hasItem())){
            this.isClosed = false;
            openAnimation();
            playSound();
            //Se muestra el elemento que contiene
        }else{
            this.item = null;
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
