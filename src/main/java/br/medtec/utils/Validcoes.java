package br.medtec.utils;

import br.medtec.exceptions.MEDValidationExecption;

import java.util.ArrayList;
import java.util.List;

public class Validcoes {

    public List<String> validacoes;

    public Validcoes(){
        this.validacoes = new ArrayList<>();
    }

    public void add(String message){
        validacoes.add(message);
    }

    public void lancaErros(){
        if(!validacoes.isEmpty()){
            throw new MEDValidationExecption(this);
        }
    }
}
