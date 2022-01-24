package org.utils;

import com.badlogic.gdx.math.Vector2;

import static org.utils.Constants.PPM;

public class SteeringUtils {

    public static float vectorToAngle(Vector2 vector)
    {
        float angulo = (float) Math.atan2(vector.y, vector.x);

        return angulo;
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = (float) Math.cos(angle);
        outVector.y = (float) Math.sin(angle);
        return outVector;
    }

}
