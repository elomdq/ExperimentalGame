package com.helloworld.box2dprueba.utils;

public class Stopwatch {
    private static final long start= System.currentTimeMillis();
    private static double endGame;


    public Stopwatch() {
    }


    public double elapsedTime(){
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

    public static double getEndGame() {
        return endGame;
    }

    public static void setEndGame(double endGame) {
        Stopwatch.endGame = endGame;
    }
}
