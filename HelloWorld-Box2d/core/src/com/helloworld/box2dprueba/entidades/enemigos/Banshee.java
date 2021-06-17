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

public class Banshee extends Enemy {

    private Player target;
    private Wander<Vector2> wanderBehavior;
    private Arrive<Vector2> arriveBhehavior;
    private Evade<Vector2> evadeBehavior;


    public Banshee(World world, SpriteBatch batch, Player target, int spawnX, int spawnY) {
        super(world, batch, spawnX, spawnY, 15, 15, false, false, "images/Banshee.txt", 32, 32, 3, target, 100, Gdx.audio.newMusic(Gdx.files.internal("sounds/FemaleScream_1.mp3")));

        this.target = target;

        this.wanderBehavior = new Wander<Vector2>(this.getSteeringBehavior())
                .setWanderOffset(2.5f / PPM)
                .setWanderRadius(0.275f / PPM)
                .setWanderRate(0.0028f)
                .setWanderOrientation(-1.35f)
                .setFaceEnabled(false)
                .setTarget(target.getSteeringBehavior());

        this.arriveBhehavior = new Arrive<Vector2>(this.getSteeringBehavior(), this.target.getSteeringBehavior())
                //.setTarget(target.getSteeringEntity())
                .setArrivalTolerance(0.2f)
                .setDecelerationRadius(2)
                .setTimeToTarget(0.002f);

        this.evadeBehavior = new Evade<Vector2>(this.getSteeringBehavior(), target.getSteeringBehavior(), 8)
                .setEnabled(true);

        getSteeringBehavior().setBehavior(getWanderBehavior());
    }

    public Wander<Vector2> getWanderBehavior() {
        return this.wanderBehavior;
    }

    public void update(float delta) {

        changeBehavior();
//        this.scream.setPan(-1f, 1/ ((float) MathUtils.getDistance(target.getBody(), this.getBody())/2));

        alterScreamVolume();

        this.getSteeringBehavior().update(delta);
        super.update(delta);

    }

    private void changeBehavior(){

        if(getHealth()>=100){
            if (this.targetisInRange(target)) {
                //en rango

                if (!getScream().isPlaying()) {
                    getScream().play();
                }

                this.getSteeringBehavior().setBehavior(this.arriveBhehavior);
                this.configSteeringBehavior(1000, 5000, 12, 6.75f);

            } else {
                //fuera de rango


                this.getSteeringBehavior().setBehavior(this.wanderBehavior);
                this.configSteeringBehavior(90,10000,50,10);

            }
        } else {

            healthRegen(0.1f);

            this.getSteeringBehavior().setBehavior(getEvadeBehavior());
            this.configSteeringBehavior(130, 4000, 12, 6.75f);
        }

    }

//    public void render(float delta) {
//        this.setStateTime(this.getStateTime() + delta);
//        this.setCurrentFrame();
//    }

    public boolean targetisInRange(Player target) {
        if (MathUtils.getDistance(target.getBody(), this.getBody()) < 6.6) {
            return true;
        } else {
            return false;
        }
    }
}
