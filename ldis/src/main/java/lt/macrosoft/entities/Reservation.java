package lt.macrosoft.entities;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RESERVATION", uniqueConstraints={
        @UniqueConstraint(columnNames = {"SUMMERHOUSE", "DATE_START", "DATE_END"})
})
@NamedQueries({
        @NamedQuery(name = "Reservation.findBySummerhouseId", query = "SELECT r FROM Reservation r WHERE r.summerhouse.id = :summerhouseId"),
        @NamedQuery(name = "Reservation.findByDate", query = "SELECT r FROM Reservation r WHERE r.dateEnd >= :dateStart AND r.dateEnd <= :dateEnd"),
        @NamedQuery(name = "Reservation.findUnique",
                query = "SELECT r FROM Reservation r " +
                        " WHERE r.member = :member AND r.dateStart = :dateStart AND r.dateEnd = :dateEnd AND r.summerhouse = :summerhouse")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reservation {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
    
    @OneToMany
    protected List<ReservationActivityCount> reservationActivityCounts;

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

    public List<ReservationActivityCount> getReservationActivityCounts() {
        return reservationActivityCounts;
    }

    public void setReservationActivityCounts(List<ReservationActivityCount> reservationActivityCounts) {
        this.reservationActivityCounts = reservationActivityCounts;
    }
}
