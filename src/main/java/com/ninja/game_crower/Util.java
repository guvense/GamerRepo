package com.ninja.game_crower;

import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Util {


    public static <T> Type getTypeOfWithList(T clazz){

        Type type = new TypeToken<List<T>>() {
        }.getType();



        return type;

    }
}
