package lt.macrosoft.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "SUMMERHOUSE")
@NamedQueries({
        @NamedQuery(name = "Summerhouse.findByDistrict", query = "SELECT s FROM Summerhouse s WHERE s.district = :district"),
})
public class Summerhouse {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
   // @SequenceGenerator(name = "id", sequenceName = "hibernate_sequence")
    protected Long id;

 // tells it is not owner of relationship, and what property maps to it.
    @OneToMany(mappedBy = "summerhouse")
    protected Collection<Reservation> reservations = new ArrayList<>();
    
    @Column(name = "DESCRIPTION", length = 2000)
    private String description; 
    
    @Column(name = "NUMBEROFPLACES")
    private Integer numberOfPlaces;
    
	@Column(name = "IMAGEURL")
    private String imageUrl;

    @Column(name = "DISTRICT")
    private String district;

    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Summerhouse() {
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}

