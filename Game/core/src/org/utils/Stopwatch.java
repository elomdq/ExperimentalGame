package org.utils;

public class Stopwatch {
    private static final long start= System.currentTimeMillis();
    private static double endGame;
    private static double minutes;
    private static double seconds;
    private static double stopTime;

    public Stopwatch() {
        minutes = 0;
        seconds = 0;
    }


    public double elapsedTime(){
        long now = System.currentTimeMillis();

        stopTime = (now - start) / 1000.0;
        formateo(stopTime);

        return stopTime;
    }

    public void formateo(double stopTime){
        minutes = Math.floor(stopTime/60);
        seconds = stopTime - minutes *60;
    }

    public static double getMinutes() {
        return minutes;
    }

    public static double getSeconds() {
        return seconds;
    }

    public static double getEndGame() {
        return endGame;
    }

    public static void setEndGame(double endGame) {
        Stopwatch.endGame = endGame;
    }

}
