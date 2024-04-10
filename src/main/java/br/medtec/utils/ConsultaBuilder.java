package br.medtec.utils;

import java.util.HashMap;

public class ConsultaBuilder {
    public Consulta instance;

    public ConsultaBuilder(){
        this.instance = new Consulta();
    }

    public void select(String sql){
        if (this.instance.sqlSelect.indexOf("SELECT") > 0){
            this.instance.sqlSelect.append(" ").append(sql);
        } else {
            this.instance.sqlSelect.append("SELECT ").append(sql);
        }
    }
    public void from(String sql){
        if (this.instance.sqlFrom.indexOf("FROM") > 0){
            this.instance.sqlFrom.append(" ").append(sql);
        } else {
            this.instance.sqlFrom.append("FROM ").append(sql);
        }
    }
    public void where(){}

    public class Consulta {
        StringBuilder sql;
        StringBuilder sqlSelect;
        StringBuilder sqlFrom;
        StringBuilder sqlWhere;
        HashMap<String, Object> sqlParams;
    }
}
