package com.helloworld.box2dprueba.objetos;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.AI.AIUtils.MathUtils;
import com.helloworld.box2dprueba.entidades.Personaje;
import com.helloworld.box2dprueba.utils.ICollision;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public class Farol extends Iluminacion implements ICollision {

    private PointLight pointLight;
    private TextureRegion texture;

    //Constructor
    public Farol(World world, SpriteBatch batch, int x, int y, int width, int height, boolean isStatic, boolean fixRotation, RayHandler rayHandler, float distancia) {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        pointLight = new PointLight(rayHandler,100,   Color.WHITE, distancia, 0 , 0);
        texture = new TextureRegion(new Texture("images/Lantern.png"));
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
        if(!this.getEstaEquipado() && MathUtils.getDistance(this.getBody(),((Personaje)fixture.getUserData()).getBody()) > 0.5){
            this.equipar((Personaje) fixture.getUserData());
        }
    }

    @Override
    public void equipar(Personaje target){
        pointLight.setDistance(0);
    }

    @Override
    public void desequipar(Personaje target){

        if(target.getAnimacion().getAnimacionActual() == target.getAnimacion().getAnimationUp() ||
           target.getAnimacion().getAnimacionActual() == target.getAnimacion().getAnimationRight()){

            this.getBody().setTransform(target.getBody().getPosition().x-(17/PPM),target.getBody().getPosition().y-(17/PPM),0);

        }else{

            this.getBody().setTransform(target.getBody().getPosition().x+(17/PPM),target.getBody().getPosition().y+(17/PPM),0);

        }

        pointLight.attachToBody(this.getBody());
        pointLight.setDistance(6);
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

    public void update(Personaje target){

        if(MathUtils.getDistance(this.getBody(),target.getBody()) > 0.0000001){

            this.setEstaEquipado(false);

        }else{

            this.setEstaEquipado(true);

        }
    }

    public void render()
    {
        if(!this.getEstaEquipado())
            this.getBatch().draw(texture, this.pointLight.getX(), this.pointLight.getY());
    }
}
