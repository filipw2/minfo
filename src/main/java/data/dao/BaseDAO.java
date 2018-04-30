package data.dao;

import data.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class BaseDAO {

    public <T>T  insert(T t){
        try {

            Session session = HibernateUtil.getSessionJavaConfigFactory().getCurrentSession();
            if(!session.getTransaction().isActive())
            session.beginTransaction();
            session.saveOrUpdate(t);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            HibernateUtil.getSessionFactory().close();
        }

        return t;
    }

    public <T> void insertMultiple(List<T> t){
        try {
            Session session = HibernateUtil.getSessionJavaConfigFactory().getCurrentSession();
            session.beginTransaction();

            for (T t1 : t) {
                session.saveOrUpdate(t1);
            }

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            HibernateUtil.getSessionFactory().close();
        }
    }
}
