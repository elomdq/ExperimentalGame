package com.helloworld.box2dprueba.score;

//ToDo: GENERAR UNA FORMA DE PASAR LOS DATOS DEL JUGADOR PARA GENERAR EL PUNTAJE

import com.helloworld.box2dprueba.entidades.Jugador;
import com.helloworld.box2dprueba.utils.Stopwatch;
import java.io.Serializable;
import static com.helloworld.box2dprueba.utils.Constants.*;

public class Score implements Serializable {

    private String name;
    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Score(String name) {
        this.name = name;
        this.score= defineScore(null);
    }

    public Score() {
        this.name = null;
        this.score = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int defineScore(Jugador jugador){

        score = SCORE_MAX - (int)Stopwatch.getEndGame();


        if(score < SCORE_MIN)
            score= SCORE_MIN;


        if(jugador.getVidas()>0){
            score += (jugador.getVidas()* SCORE_LIVES);
        }

        if(jugador.getCantidadDeBaterias()>0){
            score += (jugador.getCantidadDeBaterias()* SCORE_BATTERIES);
        }

        if(jugador.getCantidadDeBandages()>0){
            score += (jugador.getCantidadDeBandages()* SCORE_BANDAGES);
        }

        if(jugador.getCantidadDeFaroles()>0){
            score += (jugador.getCantidadDeFaroles()* SCORE_LANTERNS);
        }

        if(jugador.getVidas() < 1){
            score *= 0.8;
        }

        return score;
    }

}