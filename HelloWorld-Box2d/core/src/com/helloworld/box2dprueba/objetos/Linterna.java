package com.helloworld.box2dprueba.objetos;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.Personaje;

public class Linterna extends Iluminacion {

    private ConeLight linterna;
    private float sec = 0;


    //Constructor
    // HAY QUE REVISAR EL TEMA DEL ANGULO DE DIRECCION DE LA LUZ QUE AL PRINCIPIO VA A SER LA DEL CUERPO QUE VA A CARGAR ESTA LUZ PERO DSP ES LA DEL PERSONAJE
    public Linterna(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation, RayHandler rayHandler, float distancia) {
        super(world, batch, x, y, width, height, isStatic, fixRotation, distancia);
        linterna = new ConeLight(rayHandler, 100, Color.WHITE, distancia, x , y, 0, 25);
        linterna.setSoftnessLength(0f);
    }

    //Getter & Setter
    public ConeLight getLinterna() {
        return linterna;
    }

    public void setLinterna(ConeLight luz) {
        this.linterna = luz;
    }

    //otros metodos
    public void dispose()
    {
        linterna.dispose();
    }

    public void update()
    {
        sec += Gdx.graphics.getDeltaTime();

        if(sec >= 1) {
            linterna.setDistance(linterna.getDistance() * 0.99f);
            sec = 0;
        }
    }


    @Override
    public void equipar(Personaje target)
    {
        linterna.attachToBody(target.getBody());
    }


    @Override
    public void desequipar(Personaje target)
    {

    }

    @Override
    public float getDirection()
    {
        return this.linterna.getDirection();
    }

    @Override
    public float getConeDegree()
    {
        return this.linterna.getConeDegree();
    }


}
