package data.dao;


import data.HibernateUtil;
import data.model.Rider;
import org.hibernate.query.Query;

import java.util.List;

public class RiderDAO extends BaseDAO{

        public Rider getRiderIDifNotExistsInsert(Rider rider){
            String sQuery = "select r from Rider r where name = :name and surname= :surname and nation=:nation" ;

            try {
                org.hibernate.Session session = HibernateUtil.getSessionJavaConfigFactory().getCurrentSession();
                if(!session.getTransaction().isActive())
                session.beginTransaction();
                Query query = session.createQuery(sQuery);
                query.setParameter("name", rider.getName());
                query.setParameter("surname", rider.getSurname());
                query.setParameter("nation", rider.getNation());
                List<Rider> list = query.list();
                if(list.isEmpty())
                    super.insert(rider);
                else
                    rider=(Rider) list.get(0);
                session.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                HibernateUtil.getSessionFactory().close();
            }
            return rider;
        }

}
