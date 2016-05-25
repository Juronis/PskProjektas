package lt.macrosoft.daos;

import com.google.common.base.Optional;
import lt.macrosoft.entities.District;

import java.util.List;

/**
 * Created by Arnas on 2016-05-25.
 */
public interface DistrictDAO extends GenericDAO<District, Long> {
    Optional<List<District>> getDistricts();
}
