package data.model;



import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name ="riders", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class Rider {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, length = 11)
    private int id;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "surname", length = 50)
    private String surname;
    @Column
    private int number;
    @Column
    private String nation;


    public Rider() {
    }

    public Rider(String name, String surname, int number, String nation) {
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.nation = nation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getNumber() {
        return number;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }



    public void setNumber(int number) {
        this.number = number;
    }
}
