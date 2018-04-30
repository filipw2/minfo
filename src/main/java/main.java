import data.dao.BaseDAO;
import data.dao.EventDAO;
import data.HibernateUtil;
import data.model.Event;
import data.model.RaceTrack;
import data.model.Rider;
import org.hibernate.Session;

public class main {

    public static void main(String[] args){
        new DataParser();

//        BaseDAO dao = new EventDAO();
//        dao.insert(new Event("ARG","Grand Premio",new RaceTrack("Termas de"),2018));
    }


    private void save(){
    }
}
