package br.medtec.utils;

import br.medtec.exceptions.MEDExecption;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.Session;

import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
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
            if (this.instance.sqlSelect.toString().endsWith("(")) {
                this.instance.sqlSelect.append(sql);
            } else {
                this.instance.sqlSelect.append(",").append(sql);
            }
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
        Query query = createQuery();
        List<?> raw = query.getResultList();
        if (instance.dtoClass != null && instance.nativeQuery) {
            return (List<T>) mapToDto(raw);
        }
        return (List<T>) raw;
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
        if (BooleanUtils.isNotTrue(this.instance.notCheckUser) && BooleanUtils.isNotTrue(this.instance.nativeQuery)) {
            session.enableFilter("user").setParameterList("oidUserCreation", List.of(UserSession.getOidUser(), "user"));
        }
        StringBuilder sql = buildSql();

        try {
            if (instance.nativeQuery) {
                if (instance.dtoClass != null) {
                    query = session.createNativeQuery(sql.toString());
                } else {
                    query = session.createNativeQuery(sql.toString());
                }
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
        this.instance.sql.append(this.instance.sqlOrderBy);
        return this.instance.sql;
    }

    private void setParams(Query query){
        if (!this.instance.sqlParams.isEmpty()) {
            for (String key : this.instance.sqlParams.keySet()) {
                query.setParameter(key, this.instance.sqlParams.get(key));
            }
        }
    }

    public QueryBuilder transformDTO(Class<?> dtoClass){
        this.instance.dtoClass = dtoClass;
        return this;
    }

    private List<Object> mapToDto(List<?> raw) {
        return raw.stream().map(row -> {
            try {


                Object[] cols = (Object[]) row;
                for (int i = 0; i < cols.length; i++) {
                    for (Object col : cols) {
                        log.debug("Valor: {} Tipo: {}", col, col != null ? col.getClass() : "null");
                    }
                }
                if (instance.dtoClass.getConstructors().length > 1) {
                    Constructor<?> constructor = Arrays.stream(instance.dtoClass.getConstructors()).filter(c -> c.getParameterCount() == cols.length).findFirst().get();
                    return constructor.newInstance(cols);
                } else {
                    return instance.dtoClass.getConstructors()[0].newInstance(cols);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao mapear DTO: " + e.getMessage(), e);
            }
        }).collect(Collectors.toList());
    }

    public void notCheckUser() {
        this.instance.notCheckUser = true;
    }

    public QueryBuilder orderBy(String sql){
        this.instance.sqlOrderBy.append(" ORDER BY ").append(sql);
        return this;
    }

    private class QueryInstance {
        private EntityManager entityManager;
        private final StringBuilder sql = new StringBuilder();
        private final StringBuilder sqlSelect = new StringBuilder();
        private final StringBuilder sqlFrom = new StringBuilder();
        private final StringBuilder sqlWhere = new StringBuilder();
        private final StringBuilder sqlGroup = new StringBuilder();
        private final StringBuilder sqlOrderBy = new StringBuilder();
        private final HashMap<String, Object> sqlParams = new HashMap<>();

        private Boolean nativeQuery = false;
        private Boolean notCheckUser = false;

        private Integer limit = null;
        private Integer offset;

        private Class<?> dtoClass;

        private QueryInstance() {
        }
    }
}
