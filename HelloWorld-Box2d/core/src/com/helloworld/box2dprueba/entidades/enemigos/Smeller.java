package com.helloworld.box2dprueba.entidades.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.entidades.AI.AIUtils.MathUtils;
import com.helloworld.box2dprueba.entidades.Enemigo;
import com.helloworld.box2dprueba.entidades.Jugador;


public class Smeller extends Enemigo {


    private Jugador target;
    private Pursue<Vector2> pursueBehavior;
    private Arrive<Vector2> arriveBhehavior;
    private Evade<Vector2> evadeBehavior;



    public Smeller(World world, SpriteBatch batch , Jugador target, int spawnX, int spawnY) {
        super(world, batch, spawnX, spawnY, 15, 15, false, false, "images/Zombie.txt", 32, 32, 3, target, 0, Gdx.audio.newMusic(Gdx.files.internal("sounds/Zombie_1.mp3")));

        this.target = target;

        this.configSteeringBehavior(47, 4000, 1, 2.3f);

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

        trigerZombieScream(20); // dispara el scream del Zombie

        changeBehavior(3.67f);// Cambia el comportamiento del Zombie

        alterScreamVolume(); // cambia el volumen del scream del Zombie

        healthRegen();

        this.getSteeringBehavior().update(delta);
        super.update(delta);

        /**
         *
         * validar colisionh con jugador
         *
         * agregar animaciones
         */
    }



    private void changeBehavior(float range){

        if(this.targetisInRange(target, range)){
            //en rango

            this.getSteeringBehavior().setBehavior(this.arriveBhehavior);

        }else {

            //fuera de rango


            this.getSteeringBehavior().setBehavior(this.pursueBehavior);
        }
    }

    private void trigerZombieScream(float range){
        if (targetisInRange(target, range)){
            if (!getScream().isPlaying()){
                getScream().play();
            }
        } else {
            getScream().stop();
        }
    }

//    public void render(float delta) {
//        this.setStateTime(this.getStateTime() + delta);
//        this.setCurrentFrame();
//    }

    public boolean targetisInRange(Jugador target, float range) {
        if (MathUtils.getDistance(target.getBody(), this.getBody()) < range) {
            return true;
        } else {
            return false;
        }
    }
}
