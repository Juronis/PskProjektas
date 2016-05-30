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
import lt.macrosoft.enums.Role;
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
	public Response sendMemberByAuth(@Context final HttpServletRequest request) throws ParseException, JOSEException {
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

	@Secured({Role.FULLUSER, Role.CANDIDATE})
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
	@Secured({Role.ADMIN})
	@POST
	@Path("delete/{id}")
	public Response deleteMemberByAdmin(@PathParam("id") Long id, String json, @Context final HttpServletRequest request) throws ParseException, JOSEException {
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

		Optional<Member> adminUser = getAuthMember(request);
		if (!adminUser.isPresent()) {
			return Response
					.status(Status.GONE)
					.entity(Error.DB_DELETE).build();
		}
		Member adminMember = adminUser.get();

		if (adminMember.getId() == id) {
			return Response.status(Status.FORBIDDEN).build();
		}

		if (PasswordService.checkPassword(password, adminMember.getPassword())) {

			Optional<Member> memberToDelete = dao.getMemberById(id);
			if (!memberToDelete.isPresent()) {
				return Response
						.status(Status.GONE)
						.entity(Error.DB_DELETE).build();
			}


			Exceptions result = dao.deleteMember(memberToDelete.get());

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

	@Secured({Role.ADMIN, Role.CANDIDATE, Role.FULLUSER})
	@POST
	@Path("update")
	public Response updateMember(final Member member, @Context final HttpServletRequest request)
			throws ParseException, JOSEException {
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
		if (member.getBirthday() != userToUpdate.getBirthday()) {
			userToUpdate.setBirthday(member.getBirthday());
		}
		dao.save(userToUpdate);
		return Response.ok().build();
	}

	@Secured({Role.ADMIN})
	@POST
	@Path("update/{id}")
	public Response updateMemberByAdmin(final Member member, @PathParam("id") Long id)
			throws ParseException, JOSEException {
		Optional<Member> memberToEdit = dao.getMemberById(id);
		if(!memberToEdit.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Member memberUpdate = memberToEdit.get();
		if (member.getBirthday() != memberUpdate.getBirthday()) { member.setBirthday( memberUpdate.getBirthday());}
		if (member.getEmail() != memberUpdate.getEmail()) { member.setEmail(memberUpdate.getEmail()); }
		if (!PasswordService.checkPassword(member.getPassword(), memberUpdate.getPassword())) {
			memberUpdate.setPassword(PasswordService.hashPassword(member.getPassword()));
		}
		dao.save(memberUpdate);
		return Response.status(Status.OK).build();
	}

	@GET
	@Path("total/candidates")
	public Response sendCandidatesTotal() {
		return Response.ok().entity(dao.findCandidates().size()).build();
	}

	@GET
	@Path("all/candidates")
	public Response sendCandidates() {
		return Response.ok().entity(dao.findCandidates()).build();
	}

	@GET
	@Path("all/adminsmembers")
	public Response sendAdminsMembers() {
		return Response.ok().entity(dao.findAdminsMembers()).build();
	}

	@POST
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

	@Secured({Role.FULLUSER, Role.ADMIN})
	@POST
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

	@Secured({Role.ADMIN})
	@POST
	@Path("addcredit")
	public Response addCredit(String json) throws ParseException, JOSEException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj;
		String email;
		String amount;
		try {
			actualObj = mapper.readTree(json);
			JsonNode emailas = actualObj.get("email");
			JsonNode amountas = actualObj.get("amount");
			if (emailas == null || amountas == null) {
				return Response.status(Status.UNAUTHORIZED).build();
			}
			email = emailas.textValue();
			amount = amountas.textValue();

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return Response.status(Status.UNAUTHORIZED).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return Response.status(Status.UNAUTHORIZED).build();
		}



		Optional<Member> foundUser = dao.findByEmail(email);
		if (!foundUser.isPresent()) {
			return Response
					.status(Status.NOT_FOUND).build(); //Nerastas toks member
		}
		Member member = foundUser.get();
		try {
			member.setCreditAmount(member.getCreditAmount() + Integer.parseInt(amount));
			dao.save(member);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).build();
	}

	/*
	 * Helper methods
	 */	
	private Optional<Member> getAuthMember(HttpServletRequest request) throws ParseException, JOSEException {
				String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
				if (subject == "0") {
					return Optional.absent();
				}
				return dao.getMemberById(Long.parseLong(subject));
	}

}
