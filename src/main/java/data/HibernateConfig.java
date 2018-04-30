package data;

import data.model.*;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateConfig {
    public Configuration getConfiguration(){
        Configuration configuration = new Configuration();

        //Create Properties, can be read from property files too
        Properties props = new Properties();
        props.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        props.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/riders");
        props.put("hibernate.connection.username", "root");
        props.put("hibernate.connection.password", "");
        props.put("hibernate.current_session_context_class", "thread");

        configuration.setProperties(props);

        //we can set mapping file or class with annotation
        //addClass(Employee1.class) will look for resource
        configuration.addAnnotatedClass(Team.class);
        configuration.addAnnotatedClass(Bike.class);
        configuration.addAnnotatedClass(Rider.class);
        configuration.addAnnotatedClass(RaceTrack.class);
        configuration.addAnnotatedClass(Event.class);
        configuration.addAnnotatedClass(SessionType.class);
        configuration.addAnnotatedClass(Session.class);
        configuration.addAnnotatedClass(Results.class);
        return configuration;
    }
}
