package com.helloworld.box2dprueba.entidades.AI.AIUtils;

import com.badlogic.gdx.math.Vector2;

public class SteeringUtils {
    public static float vectorToAngle(Vector2 vector2)  {
        float angle = (float) Math.atan2(vector2.y, vector2.x);

        return angle;
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle){
        outVector.x= -(float)Math.sin(angle);
        outVector.y= (float)Math.cos(angle);
        return outVector;
    }
}
