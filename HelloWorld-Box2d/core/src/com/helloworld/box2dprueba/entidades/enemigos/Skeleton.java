package com.helloworld.box2dprueba.entidades.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.AI.AIUtils.MathUtils;
import com.helloworld.box2dprueba.entidades.Enemy;
import com.helloworld.box2dprueba.entidades.Player;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public class Skeleton extends Enemy {

    private Player target;
    private Wander<Vector2> wanderBehavior;
    private Arrive<Vector2> arriveBhehavior;
    private Evade<Vector2> evadeBehavior;

    public Skeleton(World world, SpriteBatch batch , Player target, int spawnX, int spawnY) {
        super(world, batch, spawnX, spawnY, 15, 15, false, false, "images/Skeleton.txt", 32, 32, 3, target, 100, Gdx.audio.newMusic(Gdx.files.internal("sounds/Esqueleto_1.mp3")));

        this.target=target;

        this.getSteeringBehavior().setMaxLinearSpeed(157/PPM);
        this.getSteeringBehavior().setMaxAngularSpeed(7/PPM);
        this.getSteeringBehavior().setMaxAngularAcceleration(6/PPM);

        this.wanderBehavior = new Wander<Vector2>(this.getSteeringBehavior())
                .setWanderOffset(10.385f/PPM)
                .setWanderRadius(50.43f/PPM)
                .setWanderRate(200f)
                .setWanderOrientation(-0.330f)
                .setFaceEnabled(false)
                .setTarget(target.getSteeringBehavior());

        this.arriveBhehavior = new Arrive<Vector2>(this.getSteeringBehavior(), this.target.getSteeringBehavior())
                //.setTarget(target.getSteeringEntity())
                .setArrivalTolerance(0.1f)
                .setDecelerationRadius(1)
                .setTimeToTarget(0.001f);



        getSteeringBehavior().setBehavior(getWanderBehavior());
    }

    public Wander<Vector2> getWanderBehavior() {
        return this.wanderBehavior;
    }

    public void update(float delta){

        changeBehavior();

        alterScreamVolume();

        this.getSteeringBehavior().update(delta);
        super.update(delta);

    }

    private void changeBehavior(){
        if(getHealth()>=100){
            if(this.targetisInRange(target)){
                //en rango

                if (!getScream().isPlaying()){
                    getScream().play();
                }
                this.getSteeringBehavior().setMaxAngularSpeed(100000000/PPM);
                this.getSteeringBehavior().setMaxAngularAcceleration(100000000/PPM);
                this.getSteeringBehavior().setMaxLinearAcceleration(15000/PPM);
                this.getSteeringBehavior().setBehavior(this.arriveBhehavior);


            }else {
                //fuera de rango
                this.getSteeringBehavior().setMaxAngularSpeed(10/PPM);
                this.getSteeringBehavior().setMaxAngularAcceleration(15/PPM);
                this.getSteeringBehavior().setMaxLinearAcceleration(10000/PPM);
                this.getSteeringBehavior().setBehavior(this.wanderBehavior);
            }
        } else {

            healthRegen(0.1f);
            this.getSteeringBehavior().setBehavior(getEvadeBehavior());

        }

    }

    public boolean targetisInRange(Player target){
        if (MathUtils.getDistance(target.getBody(), this.getBody()) < 4.25 ){
            return true;
        }else {
            return false;
        }
    }

}
