package lt.macrosoft.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.google.common.base.Optional;

import lt.macrosoft.entities.Member;
import lt.macrosoft.enums.Exceptions;
import lt.macrosoft.enums.Role;


public class MemberDAOImpl extends GenericDAOImpl<Member, Long> implements MemberDAO {

	@Inject
	public MemberDAOImpl(EntityManager em) {
		super(em, Member.class);
	}

	public Optional<Member> getMemberById(Long id) {
		return Optional.fromNullable(findById(id));
	}

	public Optional<Member> getMemberByToken(String header) {
		String[] headerParts = header.split(" ");
		if (headerParts[1] != null) {
			return Optional.fromNullable(getEntityManager().createNamedQuery("Member.findByToken", Member.class)
				.setParameter("logintoken", headerParts[1]).getSingleResult());
		} else {
			return null;
		}
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
	
	public Optional<Role> findRolesById(Long id){
		String query = "SELECT i.role FROM Member i WHERE i.id = :id";
		TypedQuery<Role> typedQuery = em.createQuery(query , Role.class);
		typedQuery.setParameter("id",id);
		List<Role> results = typedQuery.getResultList();
        if(results.size() == 1) {
            return Optional.fromNullable(results.get(0));
        } else if (results.size() > 1) {
            throw new RuntimeException("Username should be unique.");
        }
        return null;
	}
	public Exceptions deleteMember(Member member) {
		try {
			em.remove(member);
		} catch (OptimisticLockException e) {
			return Exceptions.OPTIMISTIC;
		} catch (PersistenceException e) {
			return Exceptions.PERSISTENCE;
		}
		return Exceptions.SUCCESS;
	}
}