package data.model;


import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "event")
public class Event {

    @EmbeddedId
    EventID eventID;

    @Column(name = "title", length = 100)
    String title;
    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE,CascadeType.PERSIST})
    RaceTrack track;


    public Event() {
    }

    public Event(String symbol, String title, RaceTrack raceTrack, int year) {
        this.title = title;
        this.track = raceTrack;
        eventID=new EventID(symbol,year);
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RaceTrack getTrack() {
        return track;
    }

    public void setTrack(RaceTrack track) {
        this.track = track;
    }

    public EventID getEventID() {
        return eventID;
    }

    public void setEventID(EventID eventID) {
        this.eventID = eventID;
    }
}
