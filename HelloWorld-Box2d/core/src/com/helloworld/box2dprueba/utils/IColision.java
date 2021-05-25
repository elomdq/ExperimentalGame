package com.helloworld.box2dprueba.utils;

import com.badlogic.gdx.physics.box2d.Fixture;

public interface IColision {

    /**
     * Método que será implementado por una serie de clases
     * las cuales tendrán definida una reacción ante una
     * colisión entre instancias.
     *
     * @param fixtureColisionada
     */
    public void colisionar(Fixture fixtureColisionada);
}
