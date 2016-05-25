package lt.macrosoft.daos;


import com.google.common.base.Optional;

import lt.macrosoft.entities.Member;

public interface MemberDAO extends GenericDAO<Member, Long> {

	public Optional<Member> getMemberById(Long id);

	public Member save(Member member);

	public Optional<Member> findByEmail(String email);
	
	public Optional<Member> findByFacebook(String email);

}
