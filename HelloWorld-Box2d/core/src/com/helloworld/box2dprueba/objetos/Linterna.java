package com.helloworld.box2dprueba.objetos;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Personaje;

public class Linterna extends Iluminacion {

    private ConeLight light;
    private float sec = 0;


    //Constructor
    // HAY QUE REVISAR EL TEMA DEL ANGULO DE DIRECCION DE LA LUZ QUE AL PRINCIPIO VA A SER LA DEL CUERPO QUE VA A CARGAR ESTA LUZ PERO DSP ES LA DEL PERSONAJE
    public Linterna(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation, RayHandler rayHandler, float distancia) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        light = new ConeLight(rayHandler, 100, Color.WHITE, distancia, x , y, 0, 25);
        light.setSoftnessLength(0f);
    }

    //Getter & Setter
    public ConeLight getLight() {
        return light;
    }

    public void setLight(ConeLight light) {
        this.light = light;
    }

    //otros metodos
    public void dispose()
    {
        light.dispose();
    }

    public void update()
    {
        reduceLight();
        setDistance(light.getDistance()); // tengo que actualizar el valor de distancia que tengo en la clase padre de Linterna
    }

    @Override
    public void equipar(Personaje target)
    {
        light.attachToBody(target.getBody());
    }


    @Override
    public void desequipar(Personaje target)
    {
        light.attachToBody(this.getBody());
    }

    @Override
    public float getDirection()
    {
        return this.light.getDirection();
    }

    @Override
    public float getConeDegree()
    {
        return this.light.getConeDegree();
    }


    private void reduceLight()
    {
        sec += Gdx.graphics.getDeltaTime();

        if(sec >= 1) {
            light.setDistance(light.getDistance() * 0.99f);
            sec = 0;
        }
    }

}
