package lt.macrosoft.daos;


import com.google.common.base.Optional;

import lt.macrosoft.entities.Member;
import lt.macrosoft.enums.Exceptions;
import lt.macrosoft.enums.Role;

import java.util.List;

public interface MemberDAO extends GenericDAO<Member, Long> {

	public Optional<Member> getMemberById(Long id);

	public Member saveIfNotExists(Member member);

	public Member save(Member member);

	public Optional<Member> findByEmail(String email);
	
	public Optional<Member> findByFacebook(String facebookId);

	public Optional<Member> getMemberByToken(String header);

	public Optional<Role> findRolesById(Long id);

	public Exceptions deleteMember(Member member);

	public List<Member> findCandidates();

	public List<Member> findAdminsMembers();

	public Exceptions candidateToMember(Member member);

}
