package com.helloworld.box2dprueba.utils;

import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.radiansToDegrees;
import static com.helloworld.box2dprueba.utils.Constants.PPM;

public final class BeautifulThings {


    //pasa angulo de radianes a grados
    public static float radiansToDegrees(float angle)
    {
        /*float toDegrees = (float) ((float)  180 / Math.PI);*/
        return angle * radiansToDegrees;
    }

    //en grados, no radianes
    public static float OppositeAngle(float angle)
    {
        if(angle >0 )
            return angle - 180;
        else
            return angle + 180;
    }

    //Esto es solo para fines practicos del uso de la linterna
    //evalua si el angulo de la entidad esta dentro del arco dado por angle1 y angle2
    public static boolean faced(float angleTarget, float angle1, float angle2)
    {
        if(OppositeAngle(angleTarget) >= angle1 && OppositeAngle(angleTarget) <= angle2)
            return true;

        return false;
    }

    //Cual es el angulo entre 2 vectores pocision, sobre la absisa
    public static float angleBetweenVectors(Vector2 target, Vector2 referencia)
    {
        double deltaX = target.x - referencia.x;
        double deltaY = target.y - referencia.y;

        double alfa = Math.atan2(deltaY * PPM, deltaX * PPM);

        return (float) alfa;
    }

    //Calcula la hipotenusa(o dsitancia) entre 2 vectores
    public static float distanceBetweenVectors(Vector2 target, Vector2 referencia)
    {
        double deltaX = target.x - referencia.x;
        double deltaY = target.y - referencia.y;

        double Hypo = Math.hypot(deltaX, deltaY);

        return (float) Math.abs(Hypo);
    }
}
