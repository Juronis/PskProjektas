package lt.macrosoft.daos;

import lt.macrosoft.entities.ReservationActivityCount;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by Arnas on 2016-05-30.
 */
public class ReservationActivityCountDAOImpl extends GenericDAOImpl<ReservationActivityCount, Long> implements ReservationActivityCountDAO {
    @Inject
    public ReservationActivityCountDAOImpl(EntityManager em) {
        super(em, ReservationActivityCount.class);
    }
}
