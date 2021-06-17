package com.helloworld.box2dprueba.score;


import com.helloworld.box2dprueba.entidades.Player;
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

    public String getScoreString() {
        return String.valueOf(score);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static int defineScore(Player player) {

        int localScore = SCORE_MAX - (int) (Stopwatch.getSeconds()*3.5);


        if (localScore < SCORE_MIN)
            localScore = SCORE_MIN;

        if (player.getHealth() > 0) {
            localScore += (player.getHealth() * SCORE_LIVES);
        }

        if (player.getCantidadDeBaterias() > 0) {
            localScore += Stopwatch.getSeconds()/10 *(player.getCantidadDeBaterias() * SCORE_BATTERIES);
        }

        if (player.getCantidadDeBandages() > 0) {
            localScore += Stopwatch.getSeconds()/10 *(player.getCantidadDeBandages() * SCORE_BANDAGES);
        }

        if (player.getCantidadDeFaroles() > 0) {
            localScore += Stopwatch.getSeconds()/10 *(player.getCantidadDeFaroles() * SCORE_LANTERNS);
        }

        if (player.getHealth() < 1) {
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

        if (score == o.score) {
            return 0;
        } else if (score < o.getScore())
            return 1;

        return -1;
    }
}