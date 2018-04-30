package data.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ResultsID implements Serializable{
    @Column(name= "sessionid")
    int sessionid;
    @Column(name = "riderid")
    int riderid;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResultsID)) return false;
        ResultsID that = (ResultsID) o;
        return Objects.equals(sessionid, that.sessionid) &&
                Objects.equals(riderid, that.riderid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionid, riderid);
    }
}
