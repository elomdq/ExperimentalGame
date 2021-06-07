package com.helloworld.box2dprueba.entidades.AI.AIUtils;

import java.util.HashMap;

public enum Health {

    DAMAGE(){
        HashMap<String, Float> damageValues= new HashMap<>();

    },
    REGEN(){
        HashMap<String, Float> values= new HashMap<>();
    };
}
