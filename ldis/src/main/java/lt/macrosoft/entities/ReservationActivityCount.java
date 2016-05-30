package lt.macrosoft.entities;

import javax.persistence.*;

/**
 * Created by Arnas on 2016-05-30.
 */
@Entity
public class ReservationActivityCount {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    protected Activity activity;

    @ManyToOne
    protected Reservation reservation;

    private Integer numOfActivity;

    public ReservationActivityCount() {
    }

    public ReservationActivityCount(Activity activity, Reservation reservation, Integer numOfActivity) {
        this.activity = activity;
        this.reservation = reservation;
        this.numOfActivity = numOfActivity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Integer getNumOfActivity() {
        return numOfActivity;
    }

    public void setNumOfActivity(Integer numOfActivity) {
        this.numOfActivity = numOfActivity;
    }

    @Override
    public String toString() {
        return "ReservationActivityCount{" +
                "id=" + id +
                ", activity=" + activity +
                ", reservation=" + reservation +
                ", numOfActivity=" + numOfActivity +
                '}';
    }
}
