package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.objetos.ItemEquipable;

import java.util.ArrayList;
import java.util.List;

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
}