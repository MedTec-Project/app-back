package br.medtec.utils;

import java.util.ArrayList;
import java.util.List;

public class Validcoes {

    public List<Validacao> validacoes;

    public Validcoes(){
        this.validacoes = new ArrayList<>();
    }

    public void add(String message){
        validacoes.add(new Validacao(message));
    }


    public class Validacao {
        String message;

        public Validacao(String message) {
            this.message = message;
        }
    }
}
