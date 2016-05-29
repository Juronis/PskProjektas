package lt.macrosoft.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SUMMERHOUSE", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
        @NamedQuery(name = "Summerhouse.findByDistrict", query = "SELECT s FROM Summerhouse s WHERE s.district = :district"),
        @NamedQuery(name = "Summerhouse.findByName", query = "SELECT s FROM Summerhouse s WHERE s.name = :name"),
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
    private String dateFrom;
    
    @NotNull
	@Column(name = "DATETO",length = 10)
    private String dateTo; 
    
    @OneToOne(
		fetch = FetchType.LAZY,
		optional = true
	)
	@PrimaryKeyJoinColumn
	protected ExtraActivities extraActivities;
    
    public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	//TODO: Add notnull
    @Column(name = "PRICE")
    private double price;

    public Summerhouse() {
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
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

