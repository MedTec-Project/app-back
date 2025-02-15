package br.medtec.utils;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.exceptions.MEDExecption;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class ConsultaBuilder {

    private final Consulta instance;

    public ConsultaBuilder(EntityManager entityManager){
        this.instance = new Consulta();
        this.instance.entityManager = entityManager;
    }

    public ConsultaBuilder(EntityManager entityManager, Boolean consultaNativa){
        this.instance = new Consulta();
        this.instance.entityManager = entityManager;
        this.instance.consultaNativa = consultaNativa;
    }

    public ConsultaBuilder select(String sql){
        if (this.instance.sqlSelect.indexOf("SELECT") > 0){
            this.instance.sqlSelect.append(",").append(sql);
        } else {
            this.instance.sqlSelect.append("SELECT ").append(sql);
        }
        return this;
    }
    public ConsultaBuilder from(String sql){
        if (this.instance.sqlFrom.indexOf("FROM") > 0){
            this.instance.sqlFrom.append(" ").append(sql);
        } else {
            this.instance.sqlFrom.append(" FROM ").append(sql);
        }
        return this;
    }
    public ConsultaBuilder where(String sql){
        if (this.instance.sqlWhere.indexOf("WHERE") > 0){
            this.instance.sqlWhere.append(" AND ").append(sql);
        } else {
            this.instance.sqlWhere.append(" WHERE ").append(sql);
        }

        return this;
    }

    public ConsultaBuilder param(String nome, Object param){
        this.instance.sqlParams.put(nome, param);

        return this;
    }

    public ConsultaBuilder offset(Integer offset){
        this.instance.offset = offset;

        return this;
    }

    public ConsultaBuilder limit(Integer limit){
        this.instance.limit = limit;

        return this;
    }


    public <T> List<T> executarConsulta() {
        return (List<T>) createQuery().getResultList();
    }

    public HashMap<String, Object> executarConsultaMap(){
        Query query = createQuery();

        List<Object[]> result = query.getResultList();
        HashMap<String, Object> map = new HashMap<>();
        if (UtilColecao.listaValida(result)) {
            for (Object[] obj : result) {
                map.put(obj[0].toString(), obj[1]);
            }
        }

        return map;
    }

    private Query createQuery() {
        Query query;
        Session session = (Session) this.instance.entityManager.getDelegate();
        if (this.instance.verificarUsuario && !this.instance.consultaNativa) {
            session.enableFilter("usuario").setParameter("oidusuariocriacao", Sessao.getOidUsuario());
        }
            StringBuilder sql = montaSql();

        try {
            if (instance.consultaNativa) {
                query = session.createNativeQuery(sql.toString());
            } else {
                query = session.createQuery(sql.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MEDExecption(e.getMessage());
        }

        if ((instance.limit != null) && (instance.limit >= 1)) {
            query.setMaxResults(instance.limit);
        }

        if ((instance.offset != null) && (instance.offset >= 0)) {
            query.setFirstResult(instance.offset);
        }


        setParams(query);

        return query;
    }


    public Object primeiroRegistro(){
        try {
            limit(1);
            List<?> result = executarConsulta();
            return !UtilColecao.listaValida(result) ? null : result.get(0);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            throw new MEDExecption(e.getMessage());
        }
    }


    public StringBuilder montaSql(){
        this.instance.sql.append(this.instance.sqlSelect);
        this.instance.sql.append(this.instance.sqlFrom);
        this.instance.sql.append(this.instance.sqlWhere);
        this.instance.sql.append(this.instance.sqlGroup);

        return this.instance.sql;
    }

    private void setParams(Query query){
        if (!this.instance.sqlParams.isEmpty()) {
          for (String chave : this.instance.sqlParams.keySet()) {
              query.setParameter(chave, this.instance.sqlParams.get(chave));
          }
        }
    }

    public void verificarUsuario() {
        this.instance.verificarUsuario = true;
    }

    private class Consulta {
        private EntityManager entityManager;
        private final StringBuilder sql = new StringBuilder();
        private final StringBuilder sqlSelect = new StringBuilder();
        private final StringBuilder sqlFrom = new StringBuilder();
        private final StringBuilder sqlWhere = new StringBuilder();
        private final StringBuilder sqlGroup = new StringBuilder();
        private final HashMap<String, Object> sqlParams = new HashMap<>();

        private Boolean consultaNativa = false;
        private Boolean verificarUsuario = false;

        private Integer limit = null;
        private Integer offset;


        private Consulta() {
        }
    }
}
