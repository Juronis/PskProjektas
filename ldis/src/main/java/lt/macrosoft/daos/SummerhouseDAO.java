package lt.macrosoft.daos;

import com.google.common.base.Optional;

import lt.macrosoft.entities.Approval;
import lt.macrosoft.entities.Summerhouse;
import lt.macrosoft.entities.Summerhouse.District;

import java.util.List;

public interface SummerhouseDAO extends GenericDAO<Summerhouse, Long> {
    Optional<List<Summerhouse>> findByDistrict(District district);
    Optional<List<Summerhouse>> findAllCustom(District district, double priceMin, int numPlaces);
    Summerhouse save(Summerhouse approval);
}
