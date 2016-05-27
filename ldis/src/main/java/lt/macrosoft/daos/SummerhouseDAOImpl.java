package lt.macrosoft.daos;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.common.base.Optional;
import lt.macrosoft.entities.Summerhouse;
import lt.macrosoft.entities.Summerhouse.District;

import java.util.List;

public class SummerhouseDAOImpl extends GenericDAOImpl<Summerhouse, Long> implements SummerhouseDAO {

	@Inject
	public SummerhouseDAOImpl(EntityManager em) {
	    super(em, Summerhouse.class);
	}

	@Override
	public Optional<List<Summerhouse>> findByDistrict(District district) {
		return Optional.fromNullable(
				getEntityManager().createNamedQuery("Summerhouse.findByDistrict", Summerhouse.class)
				.setParameter("district", district).getResultList()
		);
	}

	@Override
	public Optional<List<Summerhouse>> findAllCustom(District district, double priceMin, int numPlaces) {
		return Optional.fromNullable(
				getEntityManager().createNamedQuery("Summerhouse.findAllCustom", Summerhouse.class)
				.setParameter("priceMin", priceMin)
				.setParameter("district", district)
				.setParameter("numPlaces", numPlaces)
				.getResultList());
	}
}