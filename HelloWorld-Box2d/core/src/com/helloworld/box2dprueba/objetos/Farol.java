package com.helloworld.box2dprueba.objetos;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.AI.AIUtils.MathUtils;
import com.helloworld.box2dprueba.entidades.Personaje;
import com.helloworld.box2dprueba.utils.ICollision;

//ToDO: Pensar una forma para que el farol se renderize una vez que es colocado (quizas implementando una interfaz
//      que sea "Static", que tambien podria usar cofre y puerta, ya que siempre tendrán comportamiento estático).

public class Farol extends Iluminacion implements ICollision {

    private PointLight pointLight;

    //Constructor
    public Farol(World world, SpriteBatch batch, int x, int y, int width, int height, boolean isStatic, boolean fixRotation, RayHandler rayHandler, float distancia) {
        super(world, batch, x, y, width, height, isStatic, fixRotation, distancia);
        pointLight = new PointLight(rayHandler,100,   Color.WHITE, distancia, 0 , 0);
    }


    //Getter & Setter
    public PointLight getLuz() {
        return pointLight;
    }

    public void setLuz(PointLight luz) {
        this.pointLight = luz;
    }

    @Override
    public void collision(Fixture fixture) {
        System.out.println(MathUtils.getDistance(this.getBody(),((Personaje)fixture.getUserData()).getBody()));
        if(!this.isEstaEquipado() && MathUtils.getDistance(this.getBody(),((Personaje)fixture.getUserData()).getBody()) > 0.1){
            this.equipar((Personaje) fixture.getUserData());
        }
    }

    @Override
    public void equipar(Personaje target){
        pointLight.setDistance(0);
        this.setEstaEquipado(true);
    }

    @Override
    public void desequipar(Personaje target) {
        this.getBody().setTransform(target.getBody().getPosition().x,target.getBody().getPosition().y,0);
        pointLight.attachToBody(this.getBody());
        pointLight.setDistance(3);
        this.setEstaEquipado(false);
    }

    @Override
    public float getDirection()
    {
        return this.pointLight.getDirection();
    }

    @Override
    public float getConeDegree()
    {
        return 0f;
    }
}
