package com.helloworld.box2dprueba.states;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreState extends State{

    private Stage stage;
    private Table table;
    private Skin skin;

    public ScoreState(GameStateManager gsm) {
        super(gsm);
    }


    //parar updetear el state
    public void update(float delta){}

    //para renderizar lo necesario del state
    public void render(){}

    //para limpiar texturas, audio y otras entidades y facilitar el manejo de memoria
    public void dispose(){}

    //metodos para levantar los score del archivo
    public ArrayList<Score> jsonToArray(){
        ArrayList<Score> localArray = new ArrayList<>();
        File file = new File("score.json");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            Gson localGson = new Gson();

            while (localArray.add(localGson.fromJson(reader, Score.class))!=null);


        } catch (FileNotFoundException e) {

            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }



        return localArray=top10Scores(localArray);
    }

    protected ArrayList<Score> top10Scores(ArrayList<Score> totalScores){
        Collections.sort(totalScores);

        for(int i = 10; i < totalScores.size(); i++) {
            if(totalScores.remove(i)==null){break;}
        }

        return totalScores;
    }
}
