package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import static com.helloworld.box2dprueba.utils.CositasLindas.*;
import static com.helloworld.box2dprueba.utils.Constants.PPM;

public abstract class Enemigo extends Personaje {

    private float distanciaAlTarget;
    private Jugador target;

    public Enemigo(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames, Jugador target) {
        super(world, batch, x, y, width, height, isStatic, fixRotation, texturePath, frameWidth, frameHeight, frames);
        this.target = target;
    }

    //setter & getters

    public Jugador getTarget() {
        return target;
    }

    public void setTarget(Jugador target) {
        this.target = target;
    }


    //Otros metodos

    @Override
    public void update(float delta)
    {
        updateDistancia();
        selectEnemyAnimation(this);
        updateAlpha(this.target);
        super.update(delta);
    }

    @Override
    public  void render()
    {
        this.getAnimacion().getCurrentFrame().draw(this.getBatch(), this.getAlpha());
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
        distanciaAlTarget = distanciaAlTarget(target.getBody().getPosition());
    }


    public void selectEnemyAnimation(Enemigo enemigo)
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

    public void updateAlpha(Jugador target)
    {
        float ratio, coefA, coefB;
        float min=0.65f, max=1f;

        coefA = 1 / (min - max);
        coefB =  -1 * max * coefA;
        ratio = this.distanciaAlTarget(this.getTarget().getBody().getPosition()) / target.getIluminacion().getDistance();

        if(ratio<min)
            super.setAlpha(1);
        else if(ratio>max)
            super.setAlpha(0);
        else
            super.setAlpha(coefA * ratio + coefB);

        if(!enfrentados(radiansToDegrees(anguloEntreVectores(target.getBody().getPosition(), this.getBody().getPosition()))
                , target.getIluminacion().getDirection() - target.getIluminacion().getConeDegree(),
                target.getIluminacion().getDirection() + target.getIluminacion().getConeDegree()))
            super.setAlpha(0);
    }

    @Override
    public void collision(Fixture fixture) {
        //Falta definir qué comportamiento tendra el enemigo cuando colisiona con el jugador.
        //Deberia llamar un metodo que muestre un screamer y finalice el nivel.
    }

    public void configSteeringBehavior(float maxLinearSpeed, float maxLinearAcceleration, float maxAngularSpeed, float maxAngularAcceleration){
        this.getSteeringBehavior().setMaxLinearSpeed(maxLinearSpeed/PPM);
        this.getSteeringBehavior().setMaxLinearAcceleration(maxLinearAcceleration/PPM);
        this.getSteeringBehavior().setMaxAngularSpeed(maxAngularSpeed/PPM);
        this.getSteeringBehavior().setMaxAngularAcceleration(maxAngularAcceleration/PPM);
    }

}
