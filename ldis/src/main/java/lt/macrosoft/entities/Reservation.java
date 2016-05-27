package lt.macrosoft.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RESERVATION", uniqueConstraints={
        @UniqueConstraint(columnNames = {"SUMMERHOUSE", "DATE_START", "DATE_END"})
})
@NamedQueries({
        @NamedQuery(name = "Reservation.findBySummerhouseId", query = "SELECT r FROM Reservation r WHERE r.summerhouse.id = :summerhouseId")
})
public class Reservation {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
   // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
   // @SequenceGenerator(name = "id", sequenceName = "hibernate_sequence")
    protected Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "SUMMERHOUSE")
    protected Summerhouse summerhouse;
    
    @ManyToOne
    @NotNull
    protected Member member;

    @Column(name = "DATE_START")
    @Temporal(TemporalType.DATE)
    protected Date dateStart;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_END")
    protected Date dateEnd;
    
    public Reservation() {
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getId() {
        return id;
    }

    public Summerhouse getSummerhouse() {
        return summerhouse;
    }

    public void setSummerhouse(Summerhouse summerhouse) {
        this.summerhouse = summerhouse;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
