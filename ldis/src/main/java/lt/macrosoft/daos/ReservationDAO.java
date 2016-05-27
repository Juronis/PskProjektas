package lt.macrosoft.daos;

import com.google.common.base.Optional;
import lt.macrosoft.entities.Reservation;

import java.util.Date;
import java.util.List;

/**
 * Created by Arnas on 2016-05-26.
 */
public interface ReservationDAO extends GenericDAO<Reservation, Long>  {
    Optional<List<Reservation>> findBySummerhouseId(Long summerhouseId);

    Optional<List<Date[]>> findReservedDatesBySummerhouse(Long summerhouseId);

    Reservation save(Reservation reservation);
}
