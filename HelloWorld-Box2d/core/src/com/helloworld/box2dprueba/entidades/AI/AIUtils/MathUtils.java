package com.helloworld.box2dprueba.entidades.AI.AIUtils;

import com.badlogic.gdx.physics.box2d.Body;

public class MathUtils {
    public static double getDistance(Body target, Body enemy){

        return Math.hypot(Math.abs(target.getPosition().x- enemy.getPosition().x), Math.abs(target.getPosition().y - enemy.getPosition().y));

    }
}
