package data.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "results")
public class Results {

    @EmbeddedId
    private ResultsID resultsID;
    @Column
    private int position;
    @Column
    private String time;

    @MapsId("sessionid")
    @OneToOne@JoinColumn(name="sessionid",referencedColumnName = "id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Session session;
    @MapsId("riderid")
    @OneToOne
    @JoinColumn(name="riderid",referencedColumnName = "id")
    //@Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Rider rider;
    @OneToOne
    @JoinColumn(name="team",referencedColumnName = "teamname")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Team team;
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @OneToOne@JoinColumn(name="bike",referencedColumnName = "bike")
    private Bike bike;

    public Results() {
        resultsID = new ResultsID();
    }

    public Results(Session session, Rider rider, int position, Team team, Bike bike) {
        resultsID = new ResultsID();
        this.position = position;
        this.rider = rider;
        this.session = session;

        this.team = team;
        this.bike = bike;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }
}
