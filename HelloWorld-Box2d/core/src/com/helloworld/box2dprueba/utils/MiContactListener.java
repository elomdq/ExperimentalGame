package com.helloworld.box2dprueba.utils;

import com.badlogic.gdx.physics.box2d.*;
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
public class MiContactListener implements ContactListener {

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

        if(intervieneElJugador(fixtureA,fixtureB)){

            //Colisión con un cofre
            if(esCofre(fixtureA,fixtureB)){
                if(esJugador(fixtureA)){//Chequea que fixrue es la del jugador
                    ((Cofre)fixtureA.getUserData()).colisionar(fixtureA);
                    ((Jugador)fixtureA.getUserData()).colisionar(fixtureB);
                }else{
                    ((Jugador)fixtureA.getUserData()).colisionar(fixtureB);
                    ((Cofre)fixtureA.getUserData()).colisionar(fixtureA);
                }
            }

            //Colisión con una puerta
            if(esPuerta(fixtureA,fixtureB)){
                if(esJugador(fixtureA)){//Chequea que fixrue es la del jugador
                    ((Puerta)fixtureA.getUserData()).colisionar(fixtureA);
                    ((Jugador)fixtureA.getUserData()).colisionar(fixtureB);
                }else {
                    ((Jugador) fixtureA.getUserData()).colisionar(fixtureB);
                    ((Puerta) fixtureA.getUserData()).colisionar(fixtureA);
                }
            }

            //Colisión con un farol
            if(esFarol(fixtureA,fixtureB)){
                if(esJugador(fixtureA)){//Chequea que fixrue es la del jugador
                    ((Farol)fixtureA.getUserData()).colisionar(fixtureA);
                    ((Jugador)fixtureA.getUserData()).colisionar(fixtureB);
                }else {
                    ((Jugador) fixtureA.getUserData()).colisionar(fixtureB);
                    ((Farol) fixtureA.getUserData()).colisionar(fixtureA);
                }
            }

            //Colisión con un enemigo
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

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(intervieneElJugador(fixtureA,fixtureB)){

            if(esCofre(fixtureA,fixtureB)){
                if(esJugador(fixtureA)){

                }else{

                }
            }

        }


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
    private boolean intervieneElJugador(Fixture fa, Fixture fb){
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
    private boolean esJugador(Fixture fixture){
        if(fixture.getUserData() instanceof Cofre)
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
    private boolean esCofre(Fixture fa, Fixture fb){
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
    private boolean esPuerta(Fixture fa, Fixture fb){
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
    private boolean esFarol(Fixture fa, Fixture fb){
        if(fa.getUserData() instanceof Farol || fb.getUserData() instanceof Farol)
            return true;
        return false;
    }

}
