package com.helloworld.box2dprueba.score;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;

import static com.helloworld.box2dprueba.utils.Constants.*;

public class AuxFiles {


    public AuxFiles() {
    }

    public static ArrayList<Score> returnSortedList() {
        ArrayList<Score> localList = filesToScoreList();

        System.out.println(localList);

        Collections.sort(localList);

        return localList;
    }

    public static ArrayList<Score> filesToScoreList() {
        ArrayList<String> localString = new ArrayList<>();
        ArrayList<Score> localScore = new ArrayList<>();


        for (int i = 0; i < 5; i++) {

            switch (i) {
                case 0:
                    localString.add(AuxFiles.readJson(SCORE_1));
                    break;

                case 1:
                    localString.add(AuxFiles.readJson(SCORE_2));
                    break;

                case 2:
                    localString.add(AuxFiles.readJson(SCORE_3));
                    break;

                case 3:
                    localString.add(AuxFiles.readJson(SCORE_4));
                    break;

                case 4:
                    localString.add(AuxFiles.readJson(SCORE_5));
                    break;
            }
        }

        for (String string :
                localString) {
            Gson gson = new Gson();
            localScore.add(gson.fromJson(string, Score.class));
        }

        return localScore;
    }


    private static String readJson(String path) {
        String jsonString = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            jsonString = reader.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    public static void updateScores(ArrayList<Score> scorelist1, Score score) {

        scorelist1.add(score);

        Collections.sort(scorelist1);

        scorelist1.remove(0);

        System.out.println("scorelist1 = " + scorelist1);
        saveUpdatedInfo(scorelist1);
    }

    public static void saveUpdatedInfo(ArrayList<Score> scoreList) {
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    saveInFile(SCORE_1, scoreList.get(i));
                    break;

                case 1:
                    saveInFile(SCORE_2, scoreList.get(i));
                    break;

                case 2:
                    saveInFile(SCORE_3, scoreList.get(i));
                    break;

                case 3:
                    saveInFile(SCORE_4, scoreList.get(i));
                    break;

                case 4:
                    saveInFile(SCORE_5, scoreList.get(i));
                    break;
            }
        }
    }


    private static void saveInFile(String path, Score score) {

        System.out.println("score = " + score);
        Gson gson= new Gson();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));

            gson.toJson(score, Score.class, bw);

            bw.close();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

}
