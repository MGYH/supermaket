package com.mgyh.supermaket.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    // 全局的工厂；
    public static SessionFactory sessionFactory;
    // 静态代码块；
    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    // 当虚拟机停止时释放工厂； addShutdownHook是在虚拟机停止时执行的一些事；
        Runtime.getRuntime().addShutdownHook(new Thread() {
            // 覆盖run方法；
            public void run() {
                sessionFactory.close();
            }
        });
    }

    // 获取session工厂
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // 获取session;与本地线程绑定的session
    public static Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        return session;
    }
}
