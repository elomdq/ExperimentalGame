package com.helloworld.box2dprueba.entidades;


import com.badlogic.gdx.physics.box2d.Fixture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.objetos.*;

import java.util.ArrayList;
import java.util.List;


import static com.helloworld.box2dprueba.utils.Constants.LLAVES_NECESARIAS;

import static com.helloworld.box2dprueba.utils.Constants.PPM;
import static com.helloworld.box2dprueba.utils.CositasLindas.distanciaEntreVectores;
import static com.helloworld.box2dprueba.utils.CositasLindas.radiansToDegrees;


public class Jugador extends Personaje {

    private boolean vive;
    //private Iluminacion luminaria;
    List<ItemEquipable> inventario;

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

    //Otros metodos

    public void update(float delta)
    {
        inputUpdate(delta);
        //updateAnimation();
    }

    public void dispose(){
        super.dispose();
        //luminaria.dispose();
    }

<<<<<<< HEAD
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

    //Se reinicia el inventario cada vez que finaliza un nivel?
    public void emptyInventory(){
        inventario.clear();
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
=======
    public void inputUpdate(float delta)
    {

        int horizontalForce = 0;
        int verticalForce = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            horizontalForce -= 1;
            this.getAnimacion().setAnimacionActual(this.getAnimacion().getAnimationLeft());
            this.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);

        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            horizontalForce += 1;
            this.getAnimacion().setAnimacionActual(this.getAnimacion().getAnimationRight());
            this.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            verticalForce += 1;
            this.getAnimacion().setAnimacionActual(this.getAnimacion().getAnimationUp());
            this.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            verticalForce -= 1;
            this.getAnimacion().setAnimacionActual(this.getAnimacion().getAnimationDown());
            this.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);
        }

        if(!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.W)
                && !Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A))
            this.getAnimacion().getAnimacionActual().setFrameDuration(0);

        this.getBody().setLinearVelocity(horizontalForce * 5, verticalForce * 5);

    }


    /*public void updateAnimation()
    {
        float angle = radiansToDegrees(this.getBody().getAngle());

        float angularVelocity = distanciaEntreVectores(this.getBody().getLinearVelocity(), this.getBody().getPosition());

        System.out.println("X = " + this.getBody().getPosition().x * PPM);
        System.out.println("Y = " + this.getBody().getPosition().y * PPM);

        if(angularVelocity != 0)
        {
            if(angle>-45 && angle<45)
            {
                this.setAnimation(this.getAnimationRight());
                this.getAnimation().setFrameDuration(0.1f);
            }
            else if(angle>45 && angle<135)
            {
                this.setAnimation(this.getAnimationUp());
                this.getAnimation().setFrameDuration(0.1f);
            }
            else if(angle>-135 && angle<-45)
            {
                this.setAnimation(this.getAnimationDown());
                this.getAnimation().setFrameDuration(0.1f);
            }
            else
            {
                this.setAnimation(this.getAnimationLeft());
                this.getAnimation().setFrameDuration(0.1f);
            }
        }
        else
        {
            this.getAnimation().setFrameDuration(0);
        }
    }*/
>>>>>>> claseAnimaciones
}
