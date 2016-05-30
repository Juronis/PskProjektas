package lt.macrosoft.daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.google.common.base.Optional;

import lt.macrosoft.entities.Member;
import lt.macrosoft.entities.Reservation;
import lt.macrosoft.entities.Summerhouse;

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
    public Optional<List<Reservation>> findByDate(Date dateStart, Date dateEnd) {
        return Optional.fromNullable(
                getEntityManager().createNamedQuery("Reservation.findByDate", Reservation.class)
                        .setParameter("dateStart", dateStart)
                        .setParameter("dateEnd", dateEnd)
                .getResultList()
        );
    }

    @Override
    public Optional<Reservation> findUnique(Member member, Summerhouse summerhouse, Date dateStart, Date dateEnd) {
        try {
            return Optional.fromNullable(getEntityManager().createNamedQuery("Reservation.findUnique", Reservation.class)
                    .setParameter("dateStart", dateStart)
                    .setParameter("dateEnd", dateEnd)
                    .setParameter("member", member)
                    .setParameter("summerhouse", summerhouse)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.absent();
        }
    }

    @Override
    public Reservation save(Reservation reservation) {
        em.persist(reservation);
        System.out.println("persist reservation" + reservation.toString());
        System.out.println(getCount());
        return reservation;
    }

    @Override
    public boolean saveIfNotExists(Reservation reservation) {
        Optional<Reservation> check= findUnique(
                reservation.getMember(),
                reservation.getSummerhouse(),
                reservation.getDateStart(),
                reservation.getDateEnd()
        );
        if (!check.isPresent()){
            save(reservation);
            return true;
        }
        return false;
    }
}
