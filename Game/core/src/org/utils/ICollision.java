package org.utils;

import com.badlogic.gdx.physics.box2d.Fixture;

public interface ICollision {

    /**
     * Método que será implementado por una serie de clases
     * las cuales tendrán definida una reacción ante una
     * colisión entre instancias.
     *
     * @param fixture
     */
    public void collision(Fixture fixture);
}
