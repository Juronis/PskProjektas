package lt.macrosoft.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "SUMMERHOUSE")
@NamedQueries({
        @NamedQuery(name = "Summerhouse.findByDistrict", query = "SELECT s FROM Summerhouse s WHERE s.district = :district"),
        @NamedQuery(name = "Summerhouse.findAllCustom", query = "SELECT s FROM Summerhouse s WHERE s.district = :district AND s.price >= :priceMin AND s.numberOfPlaces >= :numPlaces")
})
public class Summerhouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    // @SequenceGenerator(name = "id", sequenceName = "hibernate_sequence")
    protected Long id;

    // tells it is not owner of relationship, and what property maps to it.
    @OneToMany(mappedBy = "summerhouse")
    protected Collection<Reservation> reservations = new ArrayList<>();

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "ADDRESS", unique = true)
    private String address;

    @Column(name = "NUMBEROFPLACES")
    private Integer numberOfPlaces;

    @Column(name = "IMAGEURL")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private District district;

    //TODO: Add notnull
    @Column(name = "PRICE")
    private int price;

    public Summerhouse() {
    }

    public Long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Collection<Reservation> getReservation() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(Integer numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}

