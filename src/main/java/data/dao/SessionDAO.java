package data.dao;

import data.HibernateUtil;
import data.model.Session;
import data.model.SessionType;
import org.hibernate.query.Query;

import java.util.List;

public class SessionDAO extends BaseDAO{


    public Session getSession(String session_type, String race_symbol, int year){
        Session msession=null;
        String sQuery = "select s from Session s inner join s.tname where s.tname = :session_type and symbol= :race_symbol and year=:year";

        try {
            org.hibernate.Session session = HibernateUtil.getSessionJavaConfigFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(sQuery);
            query.setParameter("session_type", new SessionType(session_type));
            query.setParameter("race_symbol", race_symbol);
            query.setParameter("year", year);
            List<Session> list = query.list();
            msession=(Session)list.get(0);
            session.close();
            System.out.println(msession);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            HibernateUtil.getSessionFactory().close();
        }
        return msession;
    }
}
