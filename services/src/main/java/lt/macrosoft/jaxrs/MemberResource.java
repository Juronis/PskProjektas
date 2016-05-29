package lt.macrosoft.jaxrs;

import com.nimbusds.jose.JOSEException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import lt.macrosoft.daos.ParameterDAO;
import lt.macrosoft.enums.Exceptions;
import lt.macrosoft.jaxrs.Error;
import lt.macrosoft.security.Secured;
import lt.macrosoft.utils.AuthUtils;
import com.google.common.base.Optional;
import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.entities.Member;
import lt.macrosoft.utils.PasswordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/members")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemberResource {

	@Inject
	MemberDAO dao;

	@Inject
	ParameterDAO par;

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
	public Response deleteMember(String json, @Context final HttpServletRequest request) throws ParseException, JOSEException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj;
		String password;
		try {
			actualObj = mapper.readTree(json);
			JsonNode jsonNode1 = actualObj.get("password");
			if (jsonNode1 == null) {
				return Response.status(Status.UNAUTHORIZED).build();
			}
			password = jsonNode1.textValue();

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return Response.status(Status.UNAUTHORIZED).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return Response.status(Status.UNAUTHORIZED).build();
		}

		Optional<Member> foundUser = getAuthMember(request);
		if (!foundUser.isPresent()) {
			return Response
					.status(Status.GONE)
					.entity(Error.DB_DELETE).build();
		}
		Member memberToDelete = foundUser.get();
		if (PasswordService.checkPassword(password, memberToDelete.getPassword())) {
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


	@GET
	@Path("byemail")
	public Response checkMemberByEmail(String json, @Context final HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj;
		String email;
		try {
			actualObj = mapper.readTree(json);
			JsonNode jsonNode1 = actualObj.get("email");
			if (jsonNode1 == null) {
				return Response.status(Status.NOT_FOUND).build();
			}
			email = jsonNode1.textValue();

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return Response.status(Status.NOT_FOUND).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return Response.status(Status.NOT_FOUND).build();
		}

		Optional<Member> findMember = dao.findByEmail(email);
		if (!findMember.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.status(Status.OK).build();
	}

	@GET
	@Path("membership")
	public Response buyMembership(@Context final HttpServletRequest request) throws ParseException, JOSEException {
		Optional<Member> foundUser = getAuthMember(request);
		if (!foundUser.isPresent()) {
			return Response
					.status(Status.UNAUTHORIZED).build(); //Nerastas toks member
		}
		Member member = foundUser.get();

		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.YEAR, 1);
		Date nextYear = cal.getTime();

		if (member.getMembership() != null) {
			if (member.getMembership().after(today)) {
				return Response.status(Status.METHOD_NOT_ALLOWED).build(); //Jei dar galioja
			}
		}

		Integer price = Integer.parseInt(par.findParameterValue("MEMBERSHIP_PRICE").get().getPvalue());
		if (member.getCreditAmount() >= price) {
			member.setMembership(nextYear);
			member.setCreditAmount(member.getCreditAmount() - price);
			dao.save(member);
			return Response.status(Status.OK).build();  //pavyko gauti prenumerata
		} else {
			return Response.status(Status.PAYMENT_REQUIRED).build(); //nepakanka credito
		}
	}
	/*
	 * Helper methods
	 */	
	private Optional<Member> getAuthMember(HttpServletRequest request) throws ParseException, JOSEException {
		String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		return dao.getMemberById(Long.parseLong(subject));
	}

}
