package data.model;

import javax.persistence.*;

@Entity
@Table(name = "racetrack")
public class RaceTrack {

    @Id
    @Column(name = "name", nullable = false, unique = true, length = 100)
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RaceTrack() {
    }

    public RaceTrack(String name) {
        this.name = name;
    }
}
