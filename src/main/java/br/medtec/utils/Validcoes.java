package br.medtec.utils;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.exceptions.MEDValidationExecption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Validcoes {

    private final Logger log = LoggerFactory.getLogger(Validcoes.class);

    public List<String> validacoes;

    public Validcoes(){
        this.validacoes = new ArrayList<>();
    }

    public Validcoes(Object object){
        this.validacoes = new ArrayList<>();
        log.error("Validacoes para {}", object.getClass().getName());
    }

    public void add(String message){
        validacoes.add(message);
        log.error(message);
    }

    public void lancaErros(){
        if(!validacoes.isEmpty()){
            throw new MEDBadRequestExecption(this);
        }
    }
}
