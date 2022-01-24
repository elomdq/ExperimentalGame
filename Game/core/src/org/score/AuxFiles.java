package org.score;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.google.gson.Gson;

import static org.utils.Constants.*;

public class AuxFiles {


    public AuxFiles() {
    }

    public static ArrayList<Score> returnSortedList() {
        ArrayList<Score> localList = filesToScoreList();

        //System.out.println(localList);

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


    /** ELOY PROBANDO COSAS */

    //metodos para ingresar nombre y guardar score
    public static void generateJSON(Score score){

        Gson gson = new Gson();

        File file = new File("core\\assets\\scores\\scores.json");

        try {
            FileWriter filewriter = new FileWriter(file, true);

            gson.toJson(score,Score.class,filewriter);

            filewriter.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    /*public static String readJsonFile(String path)
    {
        StringBuilder sb = new StringBuilder(); //para construir un string a partir del buffer
        File file = new File(path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while( (line = reader.readLine()) != null)
            {
                sb.append(line);
            };
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }*/

    public static ArrayList<String> readJsonFile(String path)
    {
        StringBuilder sb = new StringBuilder(); //para construir un string a partir del buffer
        File file = new File(path);
        ArrayList<String> jsons = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            sc.useDelimiter("}");

            while(sc.hasNext())
            {
                sb.append(sc.next()).append("}");
                jsons.add(sb.toString());
                sb.delete(0, sb.length());
            };

            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsons;
    }

    public static ArrayList<Score> scoreFromGson(ArrayList<String> jsons)
    {
        ArrayList<Score> scores = new ArrayList<>();
        Gson gson = new Gson();

        for (String json:
             jsons) {
            scores.add(gson.fromJson(json, Score.class));
        }

        orderScoreList(scores);

        return scores;
    }

    public static void orderScoreList(ArrayList<Score> scores)
    {
        Collections.sort(scores);
    }

}
