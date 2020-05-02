package com.mgyh.supermaket.repository;

import net.minidev.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.mgyh.supermaket.util.HibernateUtils.getSession;

@Repository
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManger;

    public List commonSearch(){

        //3.执行操作
        //3.1书写sql语句
        String sql = "select g from Goods g where id = :id";
        //3.2创建sql查询对象
        Query sqlQuery = entityManger.createQuery(sql);
        sqlQuery.setParameter("id", 1);
        //3.3  调用方法查询结果
        List list = sqlQuery.getResultList();
        //sqlQuery.uniqueResult();
        System.out.println(list);
        return list;
    }
}
