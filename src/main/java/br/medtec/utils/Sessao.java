package br.medtec.utils;

import lombok.Getter;
import lombok.Setter;

public class Sessao {

    private static Sessao instance;

    @Setter
    @Getter
    private String oidUsuario;

    @Setter
    @Getter
    private String token;


    private Sessao(){

    }

    public static Sessao getInstance(){
        if(instance == null){
            instance = new Sessao();
        }
        return instance;
    }
}
