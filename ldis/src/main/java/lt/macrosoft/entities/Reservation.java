package lt.macrosoft.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Reservation {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
   // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
   // @SequenceGenerator(name = "id", sequenceName = "hibernate_sequence")
    protected Long id;

    protected String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    protected Summerhouse summerhouse;
    
    @ManyToOne
    @NotNull
    protected Member member;
    
    public Reservation() {
    }

    public Reservation(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
