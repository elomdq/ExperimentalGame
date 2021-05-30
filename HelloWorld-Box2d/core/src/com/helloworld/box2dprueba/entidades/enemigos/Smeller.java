package com.helloworld.box2dprueba.entidades.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.AI.AIUtils.MathUtils;
import com.helloworld.box2dprueba.entidades.Enemigo;
import com.helloworld.box2dprueba.entidades.Jugador;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public class Smeller extends Enemigo {


    private Jugador target;
    private Pursue<Vector2> pursueBehavior;
    private Arrive<Vector2> arriveBhehavior;
    private Evade<Vector2> evadeBehavior;
    private Music scream;

    public Smeller(World world, Jugador target, int spawnX, int spawnY) {
        super(world, spawnX, spawnY, 32, 32, false, false, "images/Banshee.png", 32, 32, 3);

        this.target = target;
        this.scream = Gdx.audio.newMusic(Gdx.files.internal("sounds/FemaleScream_1.mp3"));

        this.configSteeringBehavior(47, 4000, 10, 8);

        this.arriveBhehavior = new Arrive<Vector2>(this.getSteeringBehavior(), this.target.getSteeringBehavior())
                //.setTarget(target.getSteeringEntity())
                .setArrivalTolerance(0.1f)
                .setDecelerationRadius(1)
                .setTimeToTarget(0.001f);

        this.pursueBehavior = new Pursue<Vector2>(this.getSteeringBehavior(), target.getSteeringBehavior())
                .setEnabled(true)
                .setMaxPredictionTime(7.5f);

        this.evadeBehavior = new Evade<Vector2>(this.getSteeringBehavior(), target.getSteeringBehavior(), 8)
        .setEnabled(true);


        getSteeringBehavior().setBehavior(getPursueBehavior());
    }

    public Pursue<Vector2> getPursueBehavior() {
        return this.pursueBehavior;
    }

    public void update(float delta) {

        if(this.targetisInRange(target)){
            //en rango

            if (!this.scream.isPlaying()){
                this.scream.play();
            }


            this.getSteeringBehavior().setBehavior(this.arriveBhehavior);
        }else {

            //fuera de rango


            this.getSteeringBehavior().setBehavior(this.pursueBehavior);
        }


        this.scream.setVolume(1/ ((float) MathUtils.getDistance(target.getBody(), this.getBody())/2));

        this.getSteeringBehavior().update(delta);

        /**
         *
         * validar colisionh con jugador
         *
         * agregar animaciones
         */
    }

//    public void render(float delta) {
//        this.setStateTime(this.getStateTime() + delta);
//        this.setCurrentFrame();
//    }

    public boolean targetisInRange(Jugador target) {
        if (MathUtils.getDistance(target.getBody(), this.getBody()) < 3.67) {
            return true;
        } else {
            return false;
        }
    }
}