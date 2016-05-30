package lt.macrosoft.daos;

import java.util.List;

import com.google.common.base.Optional;

import lt.macrosoft.entities.Summerhouse;
import lt.macrosoft.entities.Summerhouse.District;
import lt.macrosoft.enums.Exceptions;

public interface SummerhouseDAO extends GenericDAO<Summerhouse, Long> {
    Optional<List<Summerhouse>> findByDistrict(District district);
    Optional<List<Summerhouse>> findAllCustom(District district, double priceMin, int numPlaces);
    Summerhouse saveIfNotExists(Summerhouse summerhouse);
    public Optional<Summerhouse> findByName(String name);
    public Exceptions deleteSummerhouse(Summerhouse summerhouse);
}
