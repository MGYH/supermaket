package com.mgyh.supermaket.repository;


import com.alibaba.fastjson.JSONObject;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Repository
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManger;

    public JSONObject commonSearch(String sql, JSONObject object){
        JSONObject result = new JSONObject();
        result.put("list",getList(object, sql));
        return result;
    }

    public List sqlSearch(String sql){
        JSONObject result = new JSONObject();
        return getSQLList(new JSONObject(),sql);
    }

    public JSONObject sqlSearch(String sql, JSONObject object,int pageNo,int pageSize){
        JSONObject result = new JSONObject();
        result.put("list",getSQLList(object, sql,pageNo, pageSize));
        result.put("count",getSQLCount(object, sql));
        return result;
    }

    public JSONObject sqlSearch(String sql, JSONObject object){
        JSONObject result = new JSONObject();
        result.put("list",getSQLList(object, sql));
        return result;
    }
    private List<HashMap<String,String>> getSQLList(JSONObject object, String sql) {
        Query sqlQuery = entityManger.createNativeQuery(sql);
        sqlQuery.unwrap(NativeQuery.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        for (Map.Entry<String, Object> entry : object.entrySet()) {
            sqlQuery.setParameter(entry.getKey(), entry.getValue());
        }
        List<HashMap<String,String>> list = sqlQuery.getResultList();
        return list;
    }


    private List<HashMap<String,String>> getList(JSONObject object, String sql) {
        Query sqlQuery = entityManger.createQuery(sql);
        for (Map.Entry<String, Object> entry : object.entrySet()) {
            sqlQuery.setParameter(entry.getKey(), entry.getValue());
        }
        List<HashMap<String,String>> list = sqlQuery.getResultList();
        return list;
    }

    private List<HashMap<String,String>> getSQLList(JSONObject object, String sql, int pageNo,int pageSize) {
        Query sqlQuery = entityManger.createNativeQuery(sql);
        sqlQuery.setFirstResult((pageNo-1)*pageSize)
                .setMaxResults(pageSize);
        sqlQuery.unwrap(NativeQuery.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        for (Map.Entry<String, Object> entry : object.entrySet()) {
            sqlQuery.setParameter(entry.getKey(), entry.getValue());
        }
        List<HashMap<String,String>> list = sqlQuery.getResultList();
        return list;
    }

    public JSONObject commonSearch(String sql, JSONObject object,int pageNo,int pageSize){
        JSONObject result = new JSONObject();
        result.put("list",getList(object, sql,pageNo, pageSize));
        result.put("count",getCount(object, sql));
        return result;
    }

    private List<HashMap<String,String>> getList(JSONObject object, String sql, int pageNo,int pageSize) {
        Query sqlQuery = entityManger.createQuery(sql);
        sqlQuery.setFirstResult((pageNo-1)*pageSize)
                .setMaxResults(pageSize);
        for (Map.Entry<String, Object> entry : object.entrySet()) {
            sqlQuery.setParameter(entry.getKey(), entry.getValue());
        }
        List<HashMap<String,String>> list = sqlQuery.getResultList();
        return list;
    }

    private int getCount(JSONObject object, String sql) {
        int fromIndex=sql.toUpperCase().indexOf("FROM");
        sql = "select count(*) "+sql.substring(fromIndex);
        Query sqlQuery = entityManger.createQuery(sql);
        for (Map.Entry<String, Object> entry : object.entrySet()) {
            sqlQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return Integer.valueOf(sqlQuery.getSingleResult().toString());
    }

    private int getSQLCount(JSONObject object, String sql) {
        int fromIndex=sql.toUpperCase().indexOf("FROM");
        sql = "select count(*) "+sql.substring(fromIndex);
        Query sqlQuery = entityManger.createNativeQuery(sql);
        for (Map.Entry<String, Object> entry : object.entrySet()) {
            sqlQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return Integer.valueOf(sqlQuery.getSingleResult().toString());
    }
}
