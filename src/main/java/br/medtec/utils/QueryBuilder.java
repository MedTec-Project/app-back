package br.medtec.utils;

import br.medtec.exceptions.MEDExecption;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class QueryBuilder {

    private final QueryInstance instance;

    public QueryBuilder(EntityManager entityManager){
        this.instance = new QueryInstance();
        this.instance.entityManager = entityManager;
    }

    public QueryBuilder(EntityManager entityManager, Boolean nativeQuery){
        this.instance = new QueryInstance();
        this.instance.entityManager = entityManager;
        this.instance.nativeQuery = nativeQuery;
    }

    public QueryBuilder select(String sql){
        if (this.instance.sqlSelect.indexOf("SELECT") > 0){
            this.instance.sqlSelect.append(",").append(sql);
        } else {
            this.instance.sqlSelect.append("SELECT ").append(sql);
        }
        return this;
    }

    public QueryBuilder from(String sql){
        if (this.instance.sqlFrom.indexOf("FROM") > 0){
            this.instance.sqlFrom.append(" ").append(sql);
        } else {
            this.instance.sqlFrom.append(" FROM ").append(sql);
        }
        return this;
    }

    public QueryBuilder where(String sql){
        if (this.instance.sqlWhere.indexOf("WHERE") > 0){
            this.instance.sqlWhere.append(" AND ").append(sql);
        } else {
            this.instance.sqlWhere.append(" WHERE ").append(sql);
        }
        return this;
    }

    public QueryBuilder param(String oid, Object param){
        this.instance.sqlParams.put(oid, param);
        return this;
    }

    public QueryBuilder offset(Integer offset){
        this.instance.offset = offset;
        return this;
    }

    public QueryBuilder limit(Integer limit){
        this.instance.limit = limit;
        return this;
    }

    public <T> List<T> executeQuery() {
        return (List<T>) createQuery().getResultList();
    }

    public HashMap<String, Object> executeQueryMap(){
        Query query = createQuery();
        List<Object[]> result = query.getResultList();
        HashMap<String, Object> map = new HashMap<>();
        if (UtilCollection.isValidList(result)) {
            for (Object[] obj : result) {
                map.put(obj[0].toString(), obj[1]);
            }
        }
        return map;
    }

    private Query createQuery() {
        Query query;
        Session session = (Session) this.instance.entityManager.getDelegate();
        if (this.instance.checkUser && !this.instance.nativeQuery) {
            session.enableFilter("user").setParameter("oidUserCreation", UserSession.getOidUser());
        }
        StringBuilder sql = buildSql();

        try {
            if (instance.nativeQuery) {
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

    public Object firstResult(){
        try {
            limit(1);
            List<?> result = executeQuery();
            return !UtilCollection.isValidList(result) ? null : result.get(0);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            throw new MEDExecption(e.getMessage());
        }
    }

    public StringBuilder buildSql(){
        this.instance.sql.append(this.instance.sqlSelect);
        this.instance.sql.append(this.instance.sqlFrom);
        this.instance.sql.append(this.instance.sqlWhere);
        this.instance.sql.append(this.instance.sqlGroup);
        return this.instance.sql;
    }

    private void setParams(Query query){
        if (!this.instance.sqlParams.isEmpty()) {
            for (String key : this.instance.sqlParams.keySet()) {
                query.setParameter(key, this.instance.sqlParams.get(key));
            }
        }
    }

    public void checkUser() {
        this.instance.checkUser = true;
    }

    private class QueryInstance {
        private EntityManager entityManager;
        private final StringBuilder sql = new StringBuilder();
        private final StringBuilder sqlSelect = new StringBuilder();
        private final StringBuilder sqlFrom = new StringBuilder();
        private final StringBuilder sqlWhere = new StringBuilder();
        private final StringBuilder sqlGroup = new StringBuilder();
        private final HashMap<String, Object> sqlParams = new HashMap<>();

        private Boolean nativeQuery = false;
        private Boolean checkUser = false;

        private Integer limit = null;
        private Integer offset;

        private QueryInstance() {
        }
    }
}
