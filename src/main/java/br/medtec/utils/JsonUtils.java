package br.medtec.utils;

import com.google.gson.Gson;

public class JsonUtils{

    public static <T> Object fromJson(String json, Class<T> Objectclass){
        return new Gson().fromJson(json, Objectclass);
    }

    public static String toJson(Object object){
        return new Gson().toJson(object);
    }

}
