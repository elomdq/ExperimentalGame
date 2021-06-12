package com.helloworld.box2dprueba.utils;

public class Stopwatch {
    private static final long start= System.currentTimeMillis();
    private static double endGame;
    private static double minutos;
    private static double segundos;
    private static double stopTime;

    public Stopwatch() {
        minutos = 0;
        segundos = 0;
    }


    public double elapsedTime(){
        long now = System.currentTimeMillis();

        stopTime = (now - start) / 1000.0;
        formateo(stopTime);

        return stopTime;
    }

    public void formateo(double stopTime){
        minutos = Math.floor(stopTime/60);
        segundos = stopTime - minutos*60;
    }

    public static double getMinutos() {
        return minutos;
    }

    public static double getSegundos() {
        return segundos;
    }

    public static double getEndGame() {
        return endGame;
    }

    public static void setEndGame(double endGame) {
        Stopwatch.endGame = endGame;
    }

    //public static int getMinutos(){return minutos;}
}
