package com.helloworld.box2dprueba.entidades.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.AI.AIUtils.MathUtils;
import com.helloworld.box2dprueba.entidades.Enemigo;
import com.helloworld.box2dprueba.entidades.Jugador;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public class Skeleton extends Enemigo {

    private Jugador target;
    private Wander<Vector2> wanderBehavior;
    private Arrive<Vector2> arriveBhehavior;
    private Evade<Vector2> evadeBehavior;
    private Music scream;

    public Skeleton(World world, SpriteBatch batch , Jugador target, int spawnX, int spawnY) {
        super(world, batch, spawnX, spawnY, 32, 32, false, false, "images/Skeleton.txt", 32, 32, 3, target);

        this.target=target;
        this.scream = Gdx.audio.newMusic(Gdx.files.internal("sounds/FemaleScream_1.mp3"));

        this.getSteeringBehavior().setMaxLinearSpeed(120/PPM);

        this.wanderBehavior = new Wander<Vector2>(this.getSteeringBehavior())
                .setWanderOffset(0.183f/PPM)
                .setWanderRadius(1.5f/PPM)
                .setWanderRate(0.2f)
                .setWanderOrientation(-0.345f)
                .setFaceEnabled(false)
                .setTarget(target.getSteeringBehavior());

        this.arriveBhehavior = new Arrive<Vector2>(this.getSteeringBehavior(), this.target.getSteeringBehavior())
                //.setTarget(target.getSteeringEntity())
                .setArrivalTolerance(0.1f)
                .setDecelerationRadius(1)
                .setTimeToTarget(0.001f);

        this.evadeBehavior = new Evade<Vector2>(this.getSteeringBehavior(), target.getSteeringBehavior(), 8)
                .setEnabled(true);

        getSteeringBehavior().setBehavior(getWanderBehavior());
    }

    public Wander<Vector2> getWanderBehavior() {
        return this.wanderBehavior;
    }

    public void update(float delta){

        if(this.targetisInRange(target)){
            //en rango

            if (!this.scream.isPlaying()){
                this.scream.play();
            }


            this.getSteeringBehavior().setBehavior(this.arriveBhehavior);
        }else {
            //fuera de rango


            this.getSteeringBehavior().setBehavior(this.wanderBehavior);
        }

        this.scream.setVolume(1/ ((float) MathUtils.getDistance(target.getBody(), this.getBody())/2));

        this.getSteeringBehavior().update(delta);
        super.update(delta);

        /**
         *
         * validar colisionh con jugador
         *
         * agregar animaciones
         */


    }

//    public void render(float delta){
//
//    }

    public boolean targetisInRange(Jugador target){
        if (MathUtils.getDistance(target.getBody(), this.getBody()) < 4.25 ){
            return true;
        }else {
            return false;
        }
    }

}