package lt.macrosoft.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SUMMERHOUSE", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
        @NamedQuery(name = "Summerhouse.findByDistrict", query = "SELECT s FROM Summerhouse s WHERE s.district = :district"),
        @NamedQuery(name = "Summerhouse.findByName", query = "SELECT s FROM Summerhouse s WHERE s.name = :name"),
        @NamedQuery(name = "Summerhouse.findAllCustom", query = "SELECT s FROM Summerhouse s WHERE s.district = :district AND s.price >= :priceMin AND s.numberOfPlaces >= :numPlaces")
})

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Summerhouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    // @SequenceGenerator(name = "id", sequenceName = "hibernate_sequence")
    protected Long id;

    // tells it is not owner of relationship, and what property maps to it.


    @JsonIgnore
    @OneToMany(mappedBy = "summerhouse", fetch = FetchType.LAZY)
    protected List<Reservation> reservations = new ArrayList<>();
    @OneToOne(
            fetch = FetchType.EAGER,
            optional = true
    )
    @PrimaryKeyJoinColumn
    protected ExtraActivities extraActivities;
    @NotNull
    @Column(name = "NAME", length = 50)
    private String name;
    @Column(name = "DESCRIPTION", length = 2000)
    private String description;
    @Column(name = "NUMBEROFPLACES")
    private Integer numberOfPlaces;
    @Column(name = "IMAGEURL")
    private String imageUrl;
    @NotNull
    @Column(name = "DISTRICT")
    @Enumerated(EnumType.STRING)
    private District district;
    @NotNull
    @Column(name = "DATEFROM", length = 10)
    @Temporal(TemporalType.DATE)
    private Date dateFrom;
    @NotNull
    @Column(name = "DATETO", length = 10)
    @Temporal(TemporalType.DATE)
    private Date dateTo;
    //TODO: Add notnull
    @Column(name = "PRICE")
    private double price;

    public Summerhouse() {
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Reservation> getReservations() {
        return reservations;
    }
    @JsonIgnore
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }


    public ExtraActivities getExtraActivities() {
        return extraActivities;
    }

    public void setExtraActivities(ExtraActivities extraActivities) {
        this.extraActivities = extraActivities;
    }

    public enum District {

        MOLETAI(1),
        UTENA(2),
        SVENCIONYS(3);
        private int id;

        private District(final int district) {
            id = district;
        }

        public int getDistrict() {
            return id;
        }
    }
}

