package org.objetos;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import org.entidades.AI.AIUtils.MathUtils;
import org.entidades.Character;
import org.utils.ICollision;

import static org.utils.Constants.PPM;

public class Lantern extends Illumination implements ICollision {

    private PointLight pointLight;
    private TextureRegion texture;

    //Constructor
    public Lantern(World world, SpriteBatch batch, int x, int y, int width, int height, boolean isStatic, boolean fixRotation, RayHandler rayHandler, float distancia) {
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
        if(!this.getEquipped() && MathUtils.getDistance(this.getBody(),((Character)fixture.getUserData()).getBody()) > 0.5){
            this.equip((Character) fixture.getUserData());
        }
    }

    @Override
    public void equip(Character target){
        pointLight.setDistance(0);
    }

    @Override
    public void unequip(Character target){

        if(target.getAnimation().getActualAnimation() == target.getAnimation().getAnimationUp() ||
           target.getAnimation().getActualAnimation() == target.getAnimation().getAnimationRight()){

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

    public void update(Character target){

        if(MathUtils.getDistance(this.getBody(),target.getBody()) > 0.0000001){

            this.setIsEquipped(false);

        }else{

            this.setIsEquipped(true);

        }
    }

    public void render()
    {
        if(!this.getEquipped())
            this.getBatch().draw(texture, this.pointLight.getX(), this.pointLight.getY());
    }
}
