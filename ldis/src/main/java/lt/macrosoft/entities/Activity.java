package lt.macrosoft.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "EXTRAACTIVITIES")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    protected Integer price;

    @ManyToMany(mappedBy = "activityList")
    private List<Summerhouse> summerhouseList;

    @OneToMany
    protected List<ReservationActivityCount> reservationActivityCounts;

    public Activity() {
    }

    public Activity(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
