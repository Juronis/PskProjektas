package lt.macrosoft.daos;

import com.google.common.base.Optional;
import lt.macrosoft.entities.Summerhouse;

import java.util.List;

public interface SummerhouseDAO extends GenericDAO<Summerhouse, Long> {
    Optional<List<Summerhouse>> findByDistrict(String district);
}
