package com.helloworld.box2dprueba.objetos;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Entidad;
import com.helloworld.box2dprueba.utils.IColision;

public class Puerta extends Entidad implements IColision {

    private boolean estaAbierta;

    public Puerta(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, x, y, width, height, isStatic, fixRotation);
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

    @Override
    public void colisionar(Fixture fixtureColisionada) {

    }
}
