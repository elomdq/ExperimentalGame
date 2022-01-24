package org.entidades;

import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.entidades.AI.AIUtils.MathUtils;
import org.utils.ICollision;

import static org.utils.BeautifulThings.*;
import static org.utils.Constants.PPM;

public abstract class Enemy extends Character implements ICollision {

    private float distanciaAlTarget;
    private Player target;
    private double health;
    private Music scream;
    private Evade<Vector2> evadeBehavior;

    public Enemy(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames, Player target, double health, Music scream) {
        super(world, batch, x, y, width, height, isStatic, fixRotation, texturePath, frameWidth, frameHeight, frames);
        this.target = target;
        this.scream = scream;
        this.health=health;
        this.evadeBehavior = new Evade<Vector2>(this.getSteeringBehavior(), target.getSteeringBehavior(), 8).setEnabled(true);
    }

    //setter & getters

    public Player getTarget() {
        return target;
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
        this.getAnimation().getCurrentFrame().draw(this.getBatch(), this.getAlpha());
    }

    public float distanceToTarget(Vector2 target)
    {
        double deltaX = this.getBody().getPosition().x - target.x;
        double deltaY = this.getBody().getPosition().y - target.y;

        double hypo = Math.hypot(deltaX , deltaY );

        return (float) Math.abs(hypo);
    }


    public void updateDistancia()
    {
        distanciaAlTarget = distanceToTarget(target.getBody().getPosition());
    }


    public void selectEnemyAnimation(Enemy enemy)
    {
        float angle = radiansToDegrees(enemy.getBody().getAngle());

        float angularVelocity = enemy.getBody().getAngularVelocity();

        if(angularVelocity != 0)
        {
            if(angle>-45 && angle<45)
            {
                enemy.getAnimation().setActualAnimation(enemy.getAnimation().getAnimationRight());
                enemy.getAnimation().getActualAnimation().setFrameDuration(0.1f);
            }
            else if(angle>45 && angle<135)
            {
                enemy.getAnimation().setActualAnimation(enemy.getAnimation().getAnimationUp());
                enemy.getAnimation().getActualAnimation().setFrameDuration(0.1f);
            }
            else if(angle>-135 && angle<-45)
            {
                enemy.getAnimation().setActualAnimation(enemy.getAnimation().getAnimationDown());
                enemy.getAnimation().getActualAnimation().setFrameDuration(0.1f);
            }
            else
            {
                enemy.getAnimation().setActualAnimation(enemy.getAnimation().getAnimationLeft());
                enemy.getAnimation().getActualAnimation().setFrameDuration(0.1f);
            }
        }
        else
        {
            enemy.getAnimation().getActualAnimation().setFrameDuration(0);
        }

    }

    public void updateAlpha(Player target)
    {
        float ratio, coefA, coefB;
        float min=0.35f, max=0.85f;

        coefA = 1 / (min - max);
        coefB =  -1 * max * coefA;
        ratio = this.distanceToTarget(this.getTarget().getBody().getPosition()) / target.getIluminacion().getDistance();

        if(ratio<min)
            this.setAlpha(1);
        else if(ratio>max)
            this.setAlpha(0);
        else
            this.setAlpha(coefA * ratio + coefB);

        if(!faced(radiansToDegrees(angleBetweenVectors(target.getBody().getPosition(), this.getBody().getPosition()))
                , target.getIluminacion().getDirection() - target.getIluminacion().getConeDegree(),
                target.getIluminacion().getDirection() + target.getIluminacion().getConeDegree()))

            this.setAlpha(0);
    }



    public void configSteeringBehavior(float maxLinearSpeed, float maxLinearAcceleration, float maxAngularSpeed, float maxAngularAcceleration){
        this.getSteeringBehavior().setMaxLinearSpeed(maxLinearSpeed/PPM);
        this.getSteeringBehavior().setMaxLinearAcceleration(maxLinearAcceleration/PPM);
        this.getSteeringBehavior().setMaxAngularSpeed(maxAngularSpeed/PPM);
        this.getSteeringBehavior().setMaxAngularAcceleration(maxAngularAcceleration/PPM);
    }

    public double getHealth() {
        return health;
    }

    public Evade<Vector2> getEvadeBehavior() {
        return evadeBehavior;
    }

    protected void healthRegen(float hpf){
        this.health+=hpf;

    }

    protected void alterScreamVolume(){
        this.scream.setVolume(0.5f/ ((float) MathUtils.getDistance(target.getBody(), this.getBody())/1.75f));
    }

    protected Music getScream() {
        return scream;
    }

    @Override
    public void collision(Fixture fixture) {

            this.health=0;

    }

}
