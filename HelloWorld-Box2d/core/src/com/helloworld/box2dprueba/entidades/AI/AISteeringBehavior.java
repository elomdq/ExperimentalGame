package com.helloworld.box2dprueba.entidades.AI;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.helloworld.box2dprueba.entidades.AI.AIUtils.SteeringUtils;
import static com.helloworld.box2dprueba.utils.Constants.PPM;

public class AISteeringBehavior implements Steerable<Vector2> {

    Body body;
    boolean tagged;
    float boundingRadius;
    float maxLinearSpeed, maxLinearAcceleration;
    float maxAngularSpeed, maxAngularAcceleration;

    SteeringBehavior<Vector2> behavior;
    SteeringAcceleration<Vector2> steeringOutput;


    public AISteeringBehavior(Body body, float boundingRadius) {
        this.body = body;
        this.boundingRadius = boundingRadius;

        this.maxLinearSpeed = 153/PPM;
        this.maxLinearAcceleration = 10000/PPM;
        this.maxAngularSpeed = 50/PPM;
        this.maxAngularAcceleration = 10/PPM;

        this.tagged = false;

        this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
        this.body.setUserData(this);
    }

    public void update(float delta) {
        if(behavior!=null){
            behavior.calculateSteering(steeringOutput);
            applySteering(delta);
        }
    }

    public void applySteering(float delta){
        boolean anyAcceleration = false;

        if (!steeringOutput.linear.isZero()){
            Vector2 force = steeringOutput.linear.scl(delta);
            body.applyForceToCenter(force, true);
            anyAcceleration = true;
        }

        if(steeringOutput.angular != 0){
            body.applyTorque(steeringOutput.angular * delta, true);
            anyAcceleration = true;
        }else{
            Vector2 linVel = getLinearVelocity();
            if(!linVel.isZero()){
                float newOrientation = vectorToAngle(linVel);
                body.setAngularVelocity((newOrientation - getAngularVelocity())*delta);
                body.setTransform(body.getPosition(), newOrientation);
            }
        }



        if(anyAcceleration){
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            if(currentSpeedSquare> maxLinearSpeed * maxLinearSpeed){
                body.setLinearVelocity(velocity.scl(maxLinearSpeed/(float) Math.sqrt(currentSpeedSquare)));
            }

            if(body.getAngularVelocity() > maxAngularSpeed){
                body.setAngularVelocity(maxAngularSpeed);
            }
        }
    }

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
        return this.boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return this.tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return this.maxLinearSpeed;
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
        return null;
    }

    public Vector2 newVector2() {
        return new Vector2();
    }

    public Body getBody() {
        return this.body;
    }

    public void setBehavior(SteeringBehavior<Vector2> behavior) {
        this.behavior = behavior;
    }

    public SteeringBehavior<Vector2> getBehavior() {
        return behavior;
    }
}
