package lt.macrosoft.daos;

import com.google.common.base.Optional;
import lt.macrosoft.entities.Reservation;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Arnas on 2016-05-26.
 */
public class ReservationDAOImpl  extends GenericDAOImpl<Reservation, Long> implements ReservationDAO {
    @Inject
    public ReservationDAOImpl(EntityManager em) {
        super(em, Reservation.class);
    }

    @Override
    public Optional<List<Reservation>> findBySummerhouseId(Long summerhouseId) {
        return Optional.fromNullable(
                getEntityManager().createNamedQuery("Reservation.findBySummerhouseId", Reservation.class)
                        .setParameter("summerhouseId", summerhouseId).getResultList()
        );
    }

    @Override
    public Optional<List<Date[]>> findReservedDatesBySummerhouse(Long summerhouseId) {
        Optional<List<Reservation>> reservationList = findBySummerhouseId(summerhouseId);

        if(!reservationList.isPresent()) {
            return Optional.absent();
        }

        List<Date[]> dates = new ArrayList<>();
        Date[] date;
        for (Reservation reservation : reservationList.get()) {
            date = new Date[2];
            date[0] = reservation.getDateStart();
            date[1] = reservation.getDateEnd();

            dates.add(date);
        }
        Optional<List<Date[]>> optional = Optional.fromNullable(dates);
        return optional;
    }

    @Override
    public Reservation save(Reservation reservation) {
        em.persist(reservation);
        System.out.println("persist reservation" + reservation.toString());
        System.out.println(getCount());
        return reservation;
    }
}
