package lt.macrosoft.daos;

import lt.macrosoft.entities.Activity;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by Arnas on 2016-05-30.
 */
public class ActivityDAOImpl extends GenericDAOImpl<Activity, Long>implements ActivityDAO  {
    @Inject
    public ActivityDAOImpl(EntityManager em) {
        super(em, Activity.class);
    }
}
