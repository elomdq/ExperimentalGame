package com.helloworld.box2dprueba.score;


import com.helloworld.box2dprueba.entidades.Jugador;
import com.helloworld.box2dprueba.utils.Stopwatch;
import java.io.Serializable;
import static com.helloworld.box2dprueba.utils.Constants.*;

public class Score implements Serializable, Comparable<Score> {

    private String name;
    private Integer score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Score() {
        this.name = null;
        this.score = 0;
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

    public static int defineScore(Jugador jugador){

        int localScore = SCORE_MAX - (int)Stopwatch.getEndGame();


        if(localScore < SCORE_MIN)
            localScore= SCORE_MIN;


        if(jugador.getVidas()>0){
            localScore += (jugador.getVidas()* SCORE_LIVES);
        }

        if(jugador.getCantidadDeBaterias()>0){
            localScore += (jugador.getCantidadDeBaterias()* SCORE_BATTERIES);
        }

        if(jugador.getCantidadDeBandages()>0){
            localScore += (jugador.getCantidadDeBandages()* SCORE_BANDAGES);
        }

        if(jugador.getCantidadDeFaroles()>0){
            localScore += (jugador.getCantidadDeFaroles()* SCORE_LANTERNS);
        }

        if(jugador.getVidas() < 1){
            localScore *= 0.8;
        }

        return localScore;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Score{");
        sb.append("name='").append(name).append('\'');
        sb.append(", score=").append(score);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Score o) {

        if (score == 0 || o.score == 0){
            return 0;
        }

        return score.compareTo(o.getScore());
    }
}