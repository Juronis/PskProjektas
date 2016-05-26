package lt.macrosoft.jaxrs;

import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import lt.macrosoft.security.Secured;
import lt.macrosoft.utils.AuthUtils;
import com.google.common.base.Optional;
import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.entities.Member;

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
		Optional<Member> memberis = getAuthUser(request);
		if (!memberis.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok().entity(memberis.get()).build();
		}
	}
	
	// for testing
	@GET
	@Path("all")
	public Response getAllUsers() {
		return Response.ok().entity(dao.findAll()).build();
	}

	@PUT
	public Response updateUser(@Valid Member member, @Context HttpServletRequest request) throws ParseException, JOSEException {
		Optional<Member> foundUser = getAuthUser(request);
		
		if (!foundUser.isPresent()) {
			return Response
					.status(Status.NOT_FOUND)
					.entity(AuthResource.NOT_FOUND_MSG).build();
		}
		
		Member userToUpdate = foundUser.get();
		userToUpdate.setName(member.getName());
		userToUpdate.setEmail(member.getEmail());
		dao.save(userToUpdate);

		return Response.ok().build();
	}


	@GET
	@Path("/byid/{id}")
	public Response sendMemberById(@PathParam("id") Long id, @Context HttpServletRequest request) throws ParseException, JOSEException {
		Optional<Member> member = dao.getMemberById(id);

		if (!member.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(member.get()).build();
	}


	
	/*
	 * Helper methods
	 */	
	private Optional<Member> getAuthUser(HttpServletRequest request) throws ParseException, JOSEException {
		String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		return dao.getMemberById(Long.parseLong(subject));
	}

}
