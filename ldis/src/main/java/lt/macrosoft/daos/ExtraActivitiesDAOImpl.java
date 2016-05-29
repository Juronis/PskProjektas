package lt.macrosoft.daos;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import lt.macrosoft.entities.Member;


public class ExtraActivitiesDAOImpl extends GenericDAOImpl<Member, Long> implements ExtraActivitiesDAO {

	@Inject
	public ExtraActivitiesDAOImpl(EntityManager em) {
		super(em, Member.class);
	}

}	