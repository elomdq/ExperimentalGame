package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.objetos.ItemEquipable;

import java.util.ArrayList;
import java.util.List;

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
}
