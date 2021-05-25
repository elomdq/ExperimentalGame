package com.helloworld.box2dprueba.entidades.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.audio.Music;
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
    private Music scream;

    public Skeleton(World world, Jugador target, int spawnX, int spawnY) {
        super(world, spawnX, spawnY, 32, 32, false, false, "images/skeleton.png", 32, 32, 3);

        this.target=target;
        this.scream = Gdx.audio.newMusic(Gdx.files.internal("sounds/FemaleScream_1.mp3"));

        this.wanderBehavior = new Wander<Vector2>(this.getSteeringBehavior())
                .setWanderOffset(0.005f/PPM)
                .setWanderRadius(5f/PPM)
                .setWanderRate(2f)
                .setWanderOrientation(5f)
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

        this.getSteeringBehavior().update(delta);

        /**
         *
         * validar colisionh con jugador
         *
         * agregar animaciones
         */


    }

    public void render(float delta){
        this.setStateTime(this.getStateTime() + delta);
        this.setCurrentFrame();
    }

    public boolean targetisInRange(Jugador target){
        if (MathUtils.getDistance(target.getBody(), this.getBody()) < 4.5 ){
            return true;
        }else {
            return false;
        }
    }
}
