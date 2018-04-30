package data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sessiontype")
public class SessionType {

    @Id
    @Column(name = "tname")
    String tname;

    public SessionType() {
    }

    public SessionType(String name) {
        this.tname = name;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }
}
