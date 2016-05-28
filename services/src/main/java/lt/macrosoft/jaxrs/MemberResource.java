package lt.macrosoft.jaxrs;

import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
import java.util.Map;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import lt.macrosoft.enums.Exceptions;
import lt.macrosoft.jaxrs.Error;
import lt.macrosoft.security.Secured;
import lt.macrosoft.utils.AuthUtils;
import com.google.common.base.Optional;
import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.entities.Member;
import lt.macrosoft.utils.PasswordService;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

@Path("/members")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemberResource {

	@Inject
	MemberDAO dao;

	@Context
	SecurityContext securityContext;
	
	public MemberResource(MemberDAO memberDAO) {
		this.dao = memberDAO;
	}
	public MemberResource() {
		dao=null;
	}
	
	@GET
	@Path("byauth")
	public Response sendMemberByAuth(@Context HttpServletRequest request) throws ParseException, JOSEException {
		System.out.println("bent bande");
		String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		Optional<Member> memberis = getAuthMember(request);
		if (!memberis.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok().entity(memberis.get()).build();
		}
	}
	

	@GET
	@Path("all")
	public Response sendAllUMembers() {
		return Response.ok().entity(dao.findAll()).build();
	}


	@GET
	@Path("byid/{id}")
	public Response sendMemberById(@PathParam("id") Long id, @Context HttpServletRequest request) throws ParseException, JOSEException {
		Optional<Member> member = dao.getMemberById(id);

		if (!member.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(member.get()).build();
	}

	@POST
	@Path("delete")
	public Response deleteMember(Member member, @Context final HttpServletRequest request) throws ParseException, JOSEException {
		if (member.getPassword() == null){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		Optional<Member> foundUser = getAuthMember(request);
		if (!foundUser.isPresent()) {
			return Response
					.status(Status.GONE)
					.entity(Error.DB_DELETE).build();
		}
		Member memberToDelete = foundUser.get();
		if (PasswordService.checkPassword(member.getPassword(), memberToDelete.getPassword())) {
		//if (memberToDelete.getPassword() == PasswordService.hashPassword(member.getPassword())) {
			Exceptions result = dao.deleteMember(memberToDelete);
			switch (result) {
				case SUCCESS:
					return Response.ok().build();
				case OPTIMISTIC:
					return Response.status(Status.FORBIDDEN).build();
				case PERSISTENCE:
					return Response.status(Status.REQUEST_TIMEOUT).build();
				default:
					return Response.status(Status.NOT_FOUND).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	@POST
	@Path("update")
	public Response updateMember(final Member member, @Context final HttpServletRequest request) throws ParseException, JOSEException {
		Optional<Member> foundUser = getAuthMember(request);

		if (!foundUser.isPresent()) {
			return Response
					.status(Status.NOT_FOUND)
					.entity(AuthResource.NOT_FOUND_MSG).build();
		}
		Member userToUpdate = foundUser.get();
		//TODO: sukurti metoda passwordo validacijai
		if (member.getPassword() != null) {
			userToUpdate.setPassword(PasswordService.hashPassword(member.getPassword()));
		}
		//TODO: sukurti metoda emailo validacijai
		if (member.getEmail() != userToUpdate.getEmail()) {
			userToUpdate.setEmail(member.getEmail());
		}
		dao.save(userToUpdate);
		return Response.ok().build();
	}

	@GET
	@Path("total")
	public Response sendMembersTotal() {
		return Response.ok().entity(dao.findAll().size()).build();
	}


	@POST
	@Path("byemail")
	public Response checkMemberByEmail(Member member) {
		if(member.getEmail() == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Optional<Member> findMember = dao.findByEmail(member.getEmail());
		if (!findMember.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.status(Status.OK).build();
	}
	/*
	 * Helper methods
	 */	
	private Optional<Member> getAuthMember(HttpServletRequest request) throws ParseException, JOSEException {
		String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		return dao.getMemberById(Long.parseLong(subject));
	}

}
