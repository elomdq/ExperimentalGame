package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.objetos.*;

import java.util.ArrayList;
import java.util.List;

import static com.helloworld.box2dprueba.utils.Constants.LLAVES_NECESARIAS;

public class Jugador extends Personaje {

    private boolean vive;
    //private Iluminacion luminaria;
    List<com.helloworld.box2dprueba.objetos.ItemEquipable> inventario;

    public Jugador(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames) {
        super(world, x, y, width, height, isStatic, fixRotation, texturePath, frameWidth, frameHeight, frames);
        inventario = new ArrayList<>();
    }


    //Setters & getters
    public boolean isVive() {
        return vive;
    }

    public void setVive(boolean vive) {
        this.vive = vive;
    }

    /*public Iluminacion getEquipo() {
        return equipo;
    }*/

   /* public void setEquipo(Luminaria equipo) {
        this.equipo = equipo;
    }*/

    public List<com.helloworld.box2dprueba.objetos.ItemEquipable> getInventario() {
        return inventario;
    }

    public void setInventario(List<ItemEquipable> inventario) {
        this.inventario = inventario;
    }

    public void dispose(){
        super.dispose();
        //luminaria.dispose();
    }

    //Otros Métodos

    public int getCantidadDeLlaves(){

        int cantidadDeLlaves = 0;

        for(ItemEquipable item : inventario){
            if(item instanceof Llave)
                cantidadDeLlaves++;
        }

        return cantidadDeLlaves;
    }

    public void emptyInventory(){
        inventario.clear();
        //Abría que equiparle un nuevo farol
    }

    @Override
    public void collision(Fixture fixture) {

        //Comportamiento que tendrá con un cofre
        if(fixture.getUserData() instanceof Cofre){
//            inventario.add(((Cofre) fixtureColisionada.getUserData()).giveItem());
            inventario.add(((Cofre) fixture.getUserData()).getItem());
            ((Cofre) fixture.getUserData()).setItem(null);
        }

        //Comportamiento que tendrá con una puerta
        if(fixture.getUserData() instanceof Puerta){
            if(this.getCantidadDeLlaves() == LLAVES_NECESARIAS);
        }

        //Comportamiento que tendrá con un farol
        if(fixture.getUserData() instanceof Farol){
            inventario.add((Farol)fixture.getUserData());
        }

        //Comportamiento que tendrá con un enemigo
        if(fixture.getUserData() instanceof Enemigo){

        }
    }
}
