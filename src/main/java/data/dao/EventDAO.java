package data.dao;

import data.HibernateUtil;
import data.model.Event;
import data.model.EventID;
import org.hibernate.query.Query;

import java.util.List;

public class EventDAO extends BaseDAO{

    public List<Event> getEvents(){
        String sQuery = "select s from Event s inner join s.track";
        List<Event> list=null;
        try {
            org.hibernate.Session session = HibernateUtil.getSessionJavaConfigFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(sQuery);
            list = query.list();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            HibernateUtil.getSessionFactory().close();
        }
        return list;
    }

    public List<Event> getEventsForKey(EventID eventID){
        String sQuery = "select s from Event s inner join s.track where symbol = :symbol and year=:year";
        List<Event> list=null;
        try {
            org.hibernate.Session session = HibernateUtil.getSessionJavaConfigFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(sQuery);
            query.setParameter("symbol", eventID.getSymbol());
            query.setParameter("year", eventID.getYear());
            list = query.list();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            HibernateUtil.getSessionFactory().close();
        }
        return list;
    }
}
