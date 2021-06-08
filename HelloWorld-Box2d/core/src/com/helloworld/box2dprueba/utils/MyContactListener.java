package com.helloworld.box2dprueba.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.helloworld.box2dprueba.entidades.Enemigo;
import com.helloworld.box2dprueba.entidades.Jugador;
import com.helloworld.box2dprueba.objetos.Cofre;
import com.helloworld.box2dprueba.objetos.Farol;
import com.helloworld.box2dprueba.objetos.Puerta;

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

                    ((Jugador)fixtureA.getUserData()).collision(fixtureB);
                    ((Cofre)fixtureB.getUserData()).collision(fixtureA);

                }else{

                    ((Jugador)fixtureB.getUserData()).collision(fixtureA);
                    ((Cofre)fixtureA.getUserData()).collision(fixtureB);

                }
            }

            //Colisión con una puerta
            if(isDoor(fixtureA,fixtureB)){
                if(isPlayer(fixtureA)){//Chequea que fixrue es la del jugador

                    ((Jugador)fixtureA.getUserData()).collision(fixtureB);
                    ((Puerta)fixtureB.getUserData()).collision(fixtureA);

                }else {

                    ((Jugador) fixtureB.getUserData()).collision(fixtureA);
                    ((Puerta) fixtureA.getUserData()).collision(fixtureB);

                }
            }

            //Colisión con un farol
            if(isLantern(fixtureA,fixtureB)){
                if(isPlayer(fixtureA)){//Chequea que fixrue es la del jugador

                    ((Farol)fixtureB.getUserData()).collision(fixtureA);
                    ((Jugador)fixtureA.getUserData()).collision(fixtureB);

                }else {

                    ((Farol) fixtureA.getUserData()).collision(fixtureB);
                    ((Jugador) fixtureB.getUserData()).collision(fixtureA);

                }
            }

            //Colisión con un enemigo
            if(isEnemy(fixtureA,fixtureB)){
                if(isPlayer(fixtureA)){//Chequea que fixrue es la del jugador

                    ((Jugador)fixtureA.getUserData()).collision(fixtureB);
                    ((Enemigo)fixtureB.getUserData()).collision(fixtureA);

                }else {

                    ((Jugador) fixtureB.getUserData()).collision(fixtureA);
                    ((Enemigo) fixtureA.getUserData()).collision(fixtureB);

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
        if(fa.getUserData() instanceof Jugador || fb.getUserData() instanceof Jugador)
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
        if(fixture.getUserData() instanceof Jugador)
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
        if(fa.getUserData() instanceof Cofre || fb.getUserData() instanceof Cofre)
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
        if(fa.getUserData() instanceof Puerta || fb.getUserData() instanceof Puerta)
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
        if(fa.getUserData() instanceof Farol || fb.getUserData() instanceof Farol)
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
        if(fa.getUserData() instanceof Enemigo || fb.getUserData() instanceof Enemigo)
            return true;
        return false;
    }

}
