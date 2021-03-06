package lt.macrosoft.daos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.*;
import javax.ws.rs.core.Response;

import com.google.common.base.Optional;

import lt.macrosoft.entities.*;
import lt.macrosoft.enums.Exceptions;
import lt.macrosoft.enums.Role;


public class MemberDAOImpl extends GenericDAOImpl<Member, Long> implements MemberDAO {

	@Inject
	public MemberDAOImpl(EntityManager em) {
		super(em, Member.class);
	}

	@Inject
	ParameterDAO par;

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
		try {
			return Optional.fromNullable(getEntityManager().createNamedQuery("Member.findByEmail", Member.class)
					.setParameter("email", email).getSingleResult());
		} catch (NoResultException e) {
			return Optional.absent();
		}
	}

	public Optional<Member> findByFacebook(String facebookId) {
		System.out.println("FACEBOOKKID: " + facebookId);
		try {
			return Optional.fromNullable(getEntityManager().createNamedQuery("Member.findByFacebook", Member.class)
					.setParameter("facebookUser", facebookId).getSingleResult());
		} catch (NoResultException e) {
			return Optional.absent();
		}
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


	@Override
	public Member saveIfNotExists(Member member) {
		Optional<Member> check = findByEmail(member.getEmail());
		if (!check.isPresent()){
			save(member);
		}
		return member;
	}

	public List<Member> findCandidates(){
		return getEntityManager().createNamedQuery("Member.findByRole", Member.class)
				.setParameter("role", Role.CANDIDATE).getResultList();
	}

	public List<Member> findAdminsMembers(){
		return getEntityManager().createNamedQuery("Member.findByRoles", Member.class)
				.setParameter("role", Role.ADMIN).setParameter("role2", Role.FULLUSER).getResultList();
	}

	public Exceptions candidateToMember(Member member) {
		if (findAll().size() >= Integer.parseInt(par.findParameterValue("MAX_MEMBERS").get().getPvalue())) {
			return Exceptions.FORBIDDEN; //Kai max memberiu skaicius pasiektas
		}
		if (member.getRole() == Role.CANDIDATE) {
			member.setRole(Role.FULLUSER);
			save(member);
			return Exceptions.SUCCESS; //Kai viskas pavyskta
		}
		return Exceptions.METHOD_NOT_ALLOWED; //Kai Memberis yra ne kandidatas, t.y. Adminas arba fullmemeberis
	}

	public Boolean activeMembership(Member member){
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.YEAR, 1);

		if (member.getMembership() != null) {
			if (member.getMembership().before(today)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

}