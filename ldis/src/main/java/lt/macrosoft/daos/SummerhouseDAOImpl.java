package lt.macrosoft.daos;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import lt.macrosoft.entities.Summerhouse;

public class SummerhouseDAOImpl extends GenericDAOImpl<Summerhouse, Long> implements SummerhouseDAO {

	@Inject
	public SummerhouseDAOImpl(EntityManager em) {
	    super(em, Summerhouse.class);
	}
}