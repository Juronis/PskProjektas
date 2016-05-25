package lt.macrosoft.daos;

import com.google.common.base.Optional;
import lt.macrosoft.entities.District;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Arnas on 2016-05-25.
 */
public class DistrictDaoImpl extends GenericDAOImpl<District, Long> implements DistrictDAO {
    @Inject
    public DistrictDaoImpl(EntityManager em) {
        super(em, District.class);
    }

    @Override
    public Optional<List<District>> getDistricts() {
        return Optional.fromNullable(
                getEntityManager().createNamedQuery("District.findAll", District.class)
                .getResultList()
        );
    }
}
