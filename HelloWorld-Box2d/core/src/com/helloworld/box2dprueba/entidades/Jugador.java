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
        this.vive = true;
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

    /**
     * Método que retorna la cantidad de llaves que
     * hay en el inventario del jugador
     *
     * @return
     */
    public int getCantidadDeLlaves(){

        int cantidadDeLlaves = 0;

        for(ItemEquipable item : inventario){
            if(item instanceof Llave)
                cantidadDeLlaves++;
        }

        return cantidadDeLlaves;
    }

    /**
     * Método que retorna la cantidad de baterias
     * que hay en el inventario del jugador
     *
     * @return
     */
    public int getCantidadDeBaterias(){

        int cantidadDeBaterias = 0;

        for(ItemEquipable item : inventario){
            if(item instanceof Bateria)
                cantidadDeBaterias++;
        }

        return cantidadDeBaterias;
    }

    /**
     * Método que retorna la cantidad de faroles
     * que hay en el inventario del jugador
     *
     * @return
     */
    public int getCantidadDeFaroles(){

        int cantidadDeFaroles = 0;

        for(ItemEquipable item : inventario){
            if(item instanceof Farol)
                cantidadDeFaroles++;
        }

        return cantidadDeFaroles;
    }

    /**
     * Método utilizado para eliminar las llaves una vez que
     * son utilizadas para abrir la puerta de salida.
     *
     */
    private void useKeys(){
        while(getCantidadDeLlaves() > 0){
            inventario.remove(keyToDelete());
        }
    }

    /**
     * Método busca una llave en el inventario y la retorna.
     *
     */
    private Llave keyToDelete(){

        Llave key = null;

        for(ItemEquipable item : inventario){
            if(item instanceof Llave)
                key = (Llave) item;
        }

        return key;
    }

    @Override
    public void collision(Fixture fixture) {

        //Comportamiento que tendrá con un cofre
        if(fixture.getUserData() instanceof Cofre){

            //Este comportamiento quizas haya que definirlo al finalizar la colision
            //y luego de que el jugador haya "clickeado" en el objeto que contiene.

            inventario.add(((Cofre) fixture.getUserData()).getItem());
            ((Cofre) fixture.getUserData()).setItem(null);

            //Actualizar HUD

        }

        //Comportamiento que tendrá con una puerta
        if(fixture.getUserData() instanceof Puerta){
            if(this.getCantidadDeLlaves() == LLAVES_NECESARIAS){
                useKeys();
            }
        }

        //Comportamiento que tendrá con un farol
        if(fixture.getUserData() instanceof Farol){
            inventario.add((Farol)fixture.getUserData());
        }

        //Comportamiento que tendrá con un enemigo
        if(fixture.getUserData() instanceof Enemigo){
            this.vive = false;
        }
    }
}
