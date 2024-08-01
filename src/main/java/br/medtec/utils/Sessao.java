package br.medtec.utils;

import lombok.Setter;

@Setter
public class Sessao {

    private static Sessao instance;

    private String oidUsuario;

    private String tipoUsuario;

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
