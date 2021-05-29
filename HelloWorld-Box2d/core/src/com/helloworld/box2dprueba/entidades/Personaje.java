package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import com.helloworld.box2dprueba.utils.ICollision;

import com.helloworld.box2dprueba.animaciones.Animacion;
import com.helloworld.box2dprueba.entities.B2DSteeringEntity;

import java.util.ArrayList;
import java.util.List;

import static com.helloworld.box2dprueba.utils.Constants.PPM;
import static com.helloworld.box2dprueba.utils.CositasLindas.*;


public abstract class Personaje extends Entidad implements ICollision {

    private Animacion animacion;
    private float alpha;

    private B2DSteeringEntity entity;


    //constructor
    public Personaje(World world, float x, float y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames)
    {
        super(world,x, y, width, height, isStatic, fixRotation);
        this.animacion = new Animacion(texturePath, frameWidth, frameHeight, frames);
        entity = new B2DSteeringEntity(this.getBody(), 1);
        alpha = 1f;
    }

    //setters & getters

    public Animacion getAnimacion() {
        return animacion;
    }

    public void setAnimacion(Animacion animacion) {
        this.animacion = animacion;
    }

    public void setAlpha(float alpha)
    {
        this.alpha = alpha;
    }

    public float getAlpha(){return this.alpha;}


    //otros metodos

    public void updateAnimation(float delta)
    {
        this.animacion.updateStateTime(delta);
        this.animacion.setCurrentFrame();
    }

    //Tomo la posicion del body de la entidad y actualizo la posicion de renderizado del Sprite frame de la animacion
    public void updateFramePosition()
    {
        this.animacion.getCurrentFrame().setPosition(this.getBody().getPosition().x * PPM - (32/2),this.getBody().getPosition().y * PPM - (32/2));
    }

    //Con este metodo actualizamos todoo lo referente al personaje sea Jugador o Enemigo
    public void update(float delta)
    {
        updateAnimation(delta);
        updateFramePosition();
    }

    //Con este metodo renderizo el frame preciso actualizado
    public void render()
    {

    }

    public void dispose()
    {
        super.dispose();
        animacion.dispose();
    }

}
