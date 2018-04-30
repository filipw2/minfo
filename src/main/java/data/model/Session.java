package data.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "session",uniqueConstraints = {@UniqueConstraint(columnNames = "ID"), @UniqueConstraint(columnNames = {"tname","symbol","year"})})
public class Session {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToOne
    @JoinColumn(name="tname",referencedColumnName = "tname")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    SessionType tname;
    @OneToOne
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @JoinColumns({@JoinColumn( name ="symbol", referencedColumnName = "symbol"),@JoinColumn(name = "year",referencedColumnName = "year")})
    Event event;
    String category;

    public Session() {
    }

    public Session(SessionType sessiontype, Event event, String category) {
        this.tname = sessiontype;
        this.event = event;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SessionType getTname() {
        return tname;
    }

    public void setTname(SessionType tname) {
        this.tname = tname;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
