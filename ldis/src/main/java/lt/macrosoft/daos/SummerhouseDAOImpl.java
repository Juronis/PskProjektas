package lt.macrosoft.daos;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import com.google.common.base.Optional;

import lt.macrosoft.entities.Member;
import lt.macrosoft.entities.Summerhouse;
import lt.macrosoft.entities.Summerhouse.District;
import lt.macrosoft.enums.Exceptions;

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
	
	@Override
    public Summerhouse save(Summerhouse summerhouse){
    	em.persist(summerhouse);
        System.out.println("persist approval" + summerhouse.toString());
        System.out.println(getCount());
        return summerhouse;
    }
    
    public Exceptions deleteSummerhouse(Summerhouse summerhouse) {
		try {
			em.remove(summerhouse);
		} catch (OptimisticLockException e) {
			return Exceptions.OPTIMISTIC;
		} catch (PersistenceException e) {
			return Exceptions.PERSISTENCE;
		}
		return Exceptions.SUCCESS;
	}
    
    @Override
    public Optional<Summerhouse> findByName(String name) {
		try {
			return Optional.fromNullable(getEntityManager().createNamedQuery("Summerhouse.findByName", Summerhouse.class)
					.setParameter("name", name).getSingleResult());
		} catch (NoResultException e) {
			return Optional.absent();
		}
	}
    
	@Override
	public Summerhouse saveIfNotExists(Summerhouse summerhouse) {
		Optional<Summerhouse> check= findByName(summerhouse.getName());
		if (!check.isPresent()){
			save(summerhouse);
		}
		return summerhouse;
	}
}