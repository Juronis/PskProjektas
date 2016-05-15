package lt.macrosoft.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Reservation_Member")
//An immutable entity may not be updated by the application
//@org.hibernate.annotations.Immutable
public class ReservationMember {
	//Encapsulates composite key
    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "MEMBER_ID")
        protected Long memberId;

        @Column(name = "RESERVATION_ID")
        protected Long reservationId;

        public Id() {
        }

        public Id(Long categoryId, Long itemId) {
            this.memberId = categoryId;
            this.reservationId = itemId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof Id) {
                Id that = (Id) o;
                return this.memberId.equals(that.memberId)
                    && this.reservationId.equals(that.reservationId);
            }
            return false;
        }

        public int hashCode() {
            return memberId.hashCode() + reservationId.hashCode();
        }
    }

    @EmbeddedId
    protected Id id = new Id();

    @Column(updatable = false)
    @NotNull
    protected String addedBy;

    @Column(updatable = false)
    @NotNull
    protected Date addedOn = new Date();

    @ManyToOne
    @JoinColumn(
        name = "MEMBER_ID",
        insertable = false, updatable = false)
    protected Member member;

    @ManyToOne
    @JoinColumn(
        name = "RESERVATION_ID",
        insertable = false, updatable = false)
    protected Reservation reservation;


    public ReservationMember() {
    }

    public ReservationMember(String addedByUsername,
                           Member member,
                           Reservation reservation) {

        // Set fields
        this.addedBy = addedByUsername;
        this.reservation = reservation;
        this.member = member;

        // Set identifier values
        this.id.memberId = member.getId();
        this.id.reservationId = reservation.getId();

        // Guarantee referential integrity if made bidirectional
        reservation.getCategorizedItems().add(this);
        member.getReservationItems().add(this);
    }

    public Id getId() {
        return id;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public Member getMember() {
        return member;
    }

    public Reservation getReservation() {
        return reservation;
    }

    // ...
}
