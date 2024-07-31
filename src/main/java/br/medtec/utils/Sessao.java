package br.medtec.utils;

import lombok.Setter;

public class Sessao {

    private static Sessao instance;

    @Setter
    private String oidUsuario;

    @Setter
    private String tipoUsuario;

    @Setter
    private String token;


    private Sessao(){

    }

    public static Sessao getInstance(){
        if(instance == null){
            instance = new Sessao();
        }
        return instance;
    }

    public static String getOidUsuario() {
        return getInstance().oidUsuario;
    }

    public static String getTipoUsuario() {
        return getInstance().tipoUsuario;
    }

    public static String getToken() {
        return getInstance().token;
    }
}
