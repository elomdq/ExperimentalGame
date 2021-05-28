package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.physics.box2d.Fixture;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import static com.helloworld.box2dprueba.utils.CositasLindas.radiansToDegrees;

public class Enemigo extends Personaje {

    private float distancia;
    private Jugador target;

    public Enemigo(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames, Jugador jugador) {
        super(world, x, y, width, height, isStatic, fixRotation, texturePath, frameWidth, frameHeight, frames);
        this.target = jugador;
    }

    //setter & getters

    public Jugador getTarget() {
        return target;
    }

    public void setTarget(Jugador target) {
        this.target = target;
    }


    //Otros metodos

    public void update(float delta)
    {
        updateDistancia();
        updateAnimationEnemy(this);
    }

    public float distanciaAlTarget(Vector2 target)
    {
        double deltaX = this.getBody().getPosition().x - target.x;
        double deltaY = this.getBody().getPosition().y - target.y;

        double hypo = Math.hypot(deltaX , deltaY );

        return (float) Math.abs(hypo);
    }

    public void updateDistancia()
    {
        distancia = distanciaAlTarget(target.getBody().getPosition());
    }


    public void updateAnimationEnemy(Enemigo enemigo)
    {
        float angle = radiansToDegrees(enemigo.getBody().getAngle());

        float angularVelocity = enemigo.getBody().getAngularVelocity();

        if(angularVelocity != 0)
        {
            if(angle>-45 && angle<45)
            {
                enemigo.getAnimacion().setAnimacionActual(enemigo.getAnimacion().getAnimationRight());
                enemigo.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);
            }
            else if(angle>45 && angle<135)
            {
                enemigo.getAnimacion().setAnimacionActual(enemigo.getAnimacion().getAnimationUp());
                enemigo.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);
            }
            else if(angle>-135 && angle<-45)
            {
                enemigo.getAnimacion().setAnimacionActual(enemigo.getAnimacion().getAnimationDown());
                enemigo.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);
            }
            else
            {
                enemigo.getAnimacion().setAnimacionActual(enemigo.getAnimacion().getAnimationLeft());
                enemigo.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);
            }
        }
        else
        {
            enemigo.getAnimacion().getAnimacionActual().setFrameDuration(0);
        }

    }

    @Override
    public void collision(Fixture fixture) {
        //Falta definir qué comportamiento tendra el enemigo cuando colisiona con el jugador.
        //Deberia llamar un metodo que muestre un screamer y finalice el nivel.
    }
}
