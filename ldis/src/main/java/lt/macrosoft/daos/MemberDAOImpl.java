package lt.macrosoft.daos;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.common.base.Optional;

import lt.macrosoft.entities.Member;


public class MemberDAOImpl extends GenericDAOImpl<Member, Long> implements MemberDAO {

	@Inject
	public MemberDAOImpl(EntityManager em) {
		super(em, Member.class);
	}

	public Optional<Member> getMemberById(Long id) {
		return Optional.fromNullable(findById(id));
	}

	public Optional<Member> findByEmail(String email) {

		return Optional.fromNullable(getEntityManager().createNamedQuery("Member.findByEmail", Member.class)
				.setParameter("email", email).getSingleResult());
	}

	public Optional<Member> findByFacebook(String FacebookId) {
		System.out.println("FACEBOOKKID: " + FacebookId);
		return Optional.fromNullable(getEntityManager().createNamedQuery("Member.findByFacebook", Member.class)
				.setParameter("facebookUser", FacebookId).getSingleResult());
	}

	public Member save(Member member) {
		em.persist(member);
		  System.out.println("ciamember" + member.getId());
		  System.out.println(getCount());
		return member;
	}
}