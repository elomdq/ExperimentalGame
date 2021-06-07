package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Entidad;
import com.helloworld.box2dprueba.entidades.Jugador;
import com.helloworld.box2dprueba.utils.ICollision;

import static com.helloworld.box2dprueba.utils.Constants.CANTIDAD_LLAVES;

public class Puerta extends Entidad implements ICollision {

    private boolean estaAbierta;

    //Constructor
    public Puerta(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.estaAbierta = false;
    }

    //Constructor
    /*public Puerta(World mundo, float posX, float posY, float largo, float ancho, String textura) {
        super(mundo, posX, posY, largo, ancho, textura);
        this.estaAbierta = false;
    }*/


    //Getter & Setter
    public boolean isEstaAbierta() {
        return estaAbierta;
    }

    public void setEstaAbierta(boolean estaAbierta) {
        this.estaAbierta = estaAbierta;
    }

    //Otros Métodos

    private void openAnimation(){
        if(this.estaAbierta){
            //animacion de apertura
        }
    }

    private void playSound(){
        if(this.estaAbierta){
            //Alguna música de 'victoria'
        }
    }

    @Override
    public void collision(Fixture fixture){
        if( canBeOpened( enoughKeys( ((Jugador) fixture.getUserData()).getCantidadDeLlaves() ) ) ){
            if(Gdx.input.isKeyPressed(Input.Keys.E)){
                this.estaAbierta = true;
                openAnimation();
                playSound();
            }
    }
}

    /**
     * Método que chequea si hay suficientes llaves
     * para abrir la puerta
     *
     * @param keysAmount
     * @return
     */
    private boolean enoughKeys(int keysAmount){
        if(keysAmount >= CANTIDAD_LLAVES)
            return true;
        return false;
    }

    /**
     *Método que comprueba si la puerta está en
     * condiciones de abrirse
     *
     * @param enoughKeys
     * @return
     */
    private boolean canBeOpened(boolean enoughKeys){
        return (enoughKeys && !this.estaAbierta);
    }
}
