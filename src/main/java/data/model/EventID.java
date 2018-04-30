package data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EventID implements Serializable{
    @Column(name = "symbol", length = 6)
    String symbol;
    @Column(name = "year", length = 4)
    int year;

    public EventID() {
    }

    public EventID(String symbol, int year) {
        this.symbol = symbol;
        this.year = year;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventID)) return false;
        EventID that = (EventID) o;
        return Objects.equals(getSymbol(), that.getSymbol()) &&
                Objects.equals(getYear(), that.getYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSymbol(), getYear());
    }
}
