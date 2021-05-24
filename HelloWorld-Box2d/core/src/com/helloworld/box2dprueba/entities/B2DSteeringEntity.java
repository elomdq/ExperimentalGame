package com.helloworld.box2dprueba.entities;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.helloworld.box2dprueba.utils.SteeringUtils;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public class B2DSteeringEntity implements Steerable<Vector2> {

    Body body;
    boolean tagged;
    float boundingRadius;
    float maxLinearSpeed, maxLinearAcceleration;
    float maxAngularSpeed, maxAngularAcceleration;

    SteeringBehavior<Vector2> behavior; //El tipo de comportamiento que queremos que esta entidad de IA tenga
    SteeringAcceleration<Vector2> steerOutput; //Todos los calculos de la IA de como moverse, que tan rapido, que haci que direccion se volcaran en este objeto

    public B2DSteeringEntity(Body body, float boundingRadius)
    {
        this.body = body;
        this.boundingRadius = boundingRadius;

        this.maxLinearSpeed = 160/PPM;
        this.maxLinearAcceleration = 10000/PPM;
        this.maxAngularSpeed = 50/PPM;
        this.maxAngularAcceleration = 10/PPM;

        this.tagged = false;

        this.steerOutput = new SteeringAcceleration<>(new Vector2());
        this.body.setUserData(this);
    }

    public void update(float delta)
    {
        if(behavior != null)
        {
            behavior.calculateSteering(steerOutput);
            applySteering(delta);
        }
    }

    private void applySteering(float delta)
    {
        //Chequear si hay cualquier tipo de aceleracion (angular o linear) aplicada por el steerOutput
        boolean anyAccelerations = false;

        if(!steerOutput.linear.isZero()){
            Vector2 force = steerOutput.linear.scl(delta);
            body.applyForceToCenter(force, true);
            anyAccelerations = true;
        }

        if(steerOutput.angular != 0)
        {
            body.applyTorque(steerOutput.angular * delta, true);
            anyAccelerations = true;
        } else
        {
            Vector2 linVel = getLinearVelocity();
            if(!linVel.isZero())
            {
                float newOrientation = vectorToAngle(linVel);
                body.setAngularVelocity((newOrientation - getAngularVelocity()) * delta);
                body.setTransform(body.getPosition(), newOrientation);
            }
        }

        if(anyAccelerations){

            //Linear
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            if(currentSpeedSquare > maxLinearSpeed * maxLinearSpeed){
                body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }

            //Angular
            if(body.getAngularVelocity() > maxAngularSpeed)
            {
                body.setAngularVelocity(maxAngularSpeed);
            }
        }

    }


    //Interface Steerable
    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    //Interface LIMITER
    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }


    //Interface LOCATION
    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        body.setTransform(body.getPosition().x, body.getPosition().y, orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return new Box2dLocation();
    }

    //OTROS METODOS

    public Body getBody()
    {
        return this.body;
    }

    public void setBehavior(SteeringBehavior<Vector2> behavior)
    {
        this.behavior = behavior;
    }

    public SteeringBehavior<Vector2> getBehavior()
    {
        return this.behavior;
    }

}
