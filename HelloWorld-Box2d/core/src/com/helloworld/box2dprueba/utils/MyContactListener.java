package com.helloworld.box2dprueba.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.helloworld.box2dprueba.entidades.Enemy;
import com.helloworld.box2dprueba.entidades.Player;
import com.helloworld.box2dprueba.objetos.Chest;
import com.helloworld.box2dprueba.objetos.Lantern;
import com.helloworld.box2dprueba.objetos.Door;

/**
 * Esta clase es utilizada por el "mundo" para saber cuándo,
 * dónde y entre qué cuerpos se está dando una colisión y
 * cómo los mismos reaccionan ante este suceso. Solo
 * reaccionarán aquellos objetos que implementen la interfaz
 * "IColision"
 *
 */
public class MyContactListener implements ContactListener {

    /**
     * Método en el cual se define la reacción de objetos
     * cuando comienza el contacto.
     *
     * @param contact
     */
    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(playerInvolved(fixtureA,fixtureB)){

            //Colisión con un cofre
            if(isChest(fixtureA,fixtureB)){
                if(isPlayer(fixtureA)){//Chequea que fixrue es la del jugador

                    ((Player)fixtureA.getUserData()).collision(fixtureB);
                    ((Chest)fixtureB.getUserData()).collision(fixtureA);

                }else{

                    ((Player)fixtureB.getUserData()).collision(fixtureA);
                    ((Chest)fixtureA.getUserData()).collision(fixtureB);

                }
            }

            //Colisión con una puerta
            if(isDoor(fixtureA,fixtureB)){
                if(isPlayer(fixtureA)){//Chequea que fixrue es la del jugador

                    /*((Jugador)fixtureA.getUserData()).collision(fixtureB);*/
                    ((Door)fixtureB.getUserData()).collision(fixtureA);

                }else {

                    /*((Jugador) fixtureB.getUserData()).collision(fixtureA);*/
                    ((Door) fixtureA.getUserData()).collision(fixtureB);

                }
            }

            //Colisión con un farol
            if(isLantern(fixtureA,fixtureB)){
                if(isPlayer(fixtureA)){//Chequea que fixrue es la del jugador

                    ((Lantern)fixtureB.getUserData()).collision(fixtureA);
                    ((Player)fixtureA.getUserData()).collision(fixtureB);

                }else {

                    ((Lantern) fixtureA.getUserData()).collision(fixtureB);
                    ((Player) fixtureB.getUserData()).collision(fixtureA);

                }
            }

            //Colisión con un enemigo
            if(isEnemy(fixtureA,fixtureB)){
                if(isPlayer(fixtureA)){//Chequea que fixrue es la del jugador

                    ((Player)fixtureA.getUserData()).collision(fixtureB);
                    ((Enemy)fixtureB.getUserData()).collision(fixtureA);

                }else {

                    ((Player) fixtureB.getUserData()).collision(fixtureA);
                    ((Enemy) fixtureA.getUserData()).collision(fixtureB);

                }
            }
        }
    }

    /**
     * Método en el cual se define la reacción de objetos
     * cuando finaliza el contacto.
     *
     * @param contact
     */
    @Override
    public void endContact(Contact contact) {
        //De momento no hay comportamiento cuando finaliza un contacto.
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //Este método excede las necesidades de este proyecto.
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //Este método excede las necesidades de este proyecto.
    }


    /**
     * Solo existirán colisiones entre el jugador y cualquier
     * otro objeto que implemente IColisiones. No se contempla
     * ningún otro tipo de colisiones.
     *
     * @return
     */
    private boolean playerInvolved(Fixture fa, Fixture fb){
        if(fa.getUserData() instanceof Player || fb.getUserData() instanceof Player)
            return true;
        return false;
    }


    /**
     * Método que determina si el objeto en cuestión
     * es el Jugador.
     *
     * @param fixture
     * @return
     */
    private boolean isPlayer(Fixture fixture){
        if(fixture.getUserData() instanceof Player)
            return true;
        return false;
    }


    /**
     * Método que chequea que uno de los objetos que están colisionando
     * sea un cofre.
     *
     * @param fa
     * @param fb
     * @return
     */
    private boolean isChest(Fixture fa, Fixture fb){
        if(fa.getUserData() instanceof Chest || fb.getUserData() instanceof Chest)
            return true;
        return false;
    }


    /**
     * Método que chequea que uno de los objeto que están colisionando
     * sea una puerta.
     *
     * @param fa
     * @param fb
     * @return
     */
    private boolean isDoor(Fixture fa, Fixture fb){
        if(fa.getUserData() instanceof Door || fb.getUserData() instanceof Door)
            return true;
        return false;
    }


    /**
     * Método que chequea que uno de los objeto que están colisionando
     * sea un farol.
     *
     * @param fa
     * @param fb
     * @return
     */
    private boolean isLantern(Fixture fa, Fixture fb){
        if(fa.getUserData() instanceof Lantern || fb.getUserData() instanceof Lantern)
            return true;
        return false;
    }


    /**
     * Método que chequea que uno de los objeto que están colisionando
     * sea un enemigo.
     *
     * @param fa
     * @param fb
     * @return
     */
    private boolean isEnemy(Fixture fa, Fixture fb){
        if(fa.getUserData() instanceof Enemy || fb.getUserData() instanceof Enemy)
            return true;
        return false;
    }

}
