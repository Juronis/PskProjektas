package lt.macrosoft.jaxrs;
import java.util.Set;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;

import lt.macrosoft.beans.SummerhouseStatelessBean;
import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.daos.SummerhouseDAO;
import lt.macrosoft.entities.Member;
import lt.macrosoft.entities.Summerhouse;
import lt.macrosoft.entities.Summerhouse.District;
import lt.macrosoft.enums.Exceptions;
import lt.macrosoft.interceptors.LoggingIntercept;
import lt.macrosoft.utils.AuthUtils;
import lt.macrosoft.utils.DateUtils;



@Path("/summerhouses")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SummerhouseResource {
	@EJB
	SummerhouseStatelessBean summerhouseStatelessBean;
	@Inject
	MemberDAO memberDAO;
	@Inject
	SummerhouseDAO summerhouseDAO;

	@Context
	private HttpServletRequest httpRequest;

	private final String UPLOADED_FILE_PATH = System.getProperty("jboss.home.dir")+ "\\images\\";
	
	public SummerhouseResource() {
	}

	@GET
	@Path("districts")
	@Produces(MediaType.APPLICATION_JSON)
	public District[] formDistrictList() {
		return District.values();
	}

	@GET
	public List<Summerhouse> getSummerhouses(@QueryParam("district") String district,
			@DefaultValue("0") @QueryParam("priceMin") double priceMin,
			@DefaultValue("0") @QueryParam("numPlaces") int numPlaces) {
		return summerhouseDAO.findAllCustom(District.valueOf(district), priceMin, numPlaces).get();
	}

	/*Realiai sito nereikia, bet kolkas pasilaikykime testavimui*/
	@GET
	@Path("pad")
	@Produces(MediaType.APPLICATION_JSON)
	public Summerhouse getSummerhouse() {
		Summerhouse a = new Summerhouse();
		a.setPrice(7);
		a.setDistrict(District.MOLETAI);
		a.setDescription("puikus vasarnamis");
		a.setNumberOfPlaces(4);
		a.setName("lelija");
		a.setImageUrl("http://s1.15cdn.lt/static/cache/NTgweDMwMCw5NjB4NjM5LDYxNjEzMyxvcmlnaW5hbCwsMzk2MzU1MTE1NA==/15ig20160527gerves2537_result-57487732747d9.jpg");
		return a;
	}

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Summerhouse> getAllSummerhouses() {
		return summerhouseDAO.findAll();
	}
	
	@DELETE
	@Path("delete/{id}")
	public Response deleteSummerhouse(@PathParam("id") Long id) throws ParseException, JOSEException {
		Summerhouse summerhouse = summerhouseDAO.findById(id);
		if (summerhouse == null) {
			return Response
					.status(Status.GONE)
					.entity(Error.DB_DELETE).build();
		}
		Exceptions result = summerhouseDAO.deleteSummerhouse(summerhouse);
		switch (result) {
			case SUCCESS:
				return Response.ok().build();
			case OPTIMISTIC:
				return  Response.status(Status.FORBIDDEN).build();
			case PERSISTENCE:
				return Response.status(Status.REQUEST_TIMEOUT).build();
			default:
				return  Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSummerhouse(Summerhouse summerhouse) throws ParseException, JOSEException{
		try {
			summerhouseDAO.save(summerhouse);
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(Error.DB_SUMMERHOUSE_PERSIST).build();
		}
		return Response.ok().build();
	}

	@POST
	@Path("reserve/{summerhouseId}/{dateStart}/{dateEnd}")
	@Interceptors(LoggingIntercept.class)
	public Response reserveSummerhouse(@PathParam("summerhouseId") Long id, @PathParam("dateStart") String dateStartStr,
			@PathParam("dateEnd") String dateEndStr, @Context final HttpServletRequest request) {
		String userId = null;
		try {
			userId = AuthUtils.getSubject(httpRequest.getHeader(AuthUtils.AUTH_HEADER_KEY));
		} catch (ParseException | JOSEException e) {
			e.printStackTrace();
		}
		if (userId == null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		Member member = memberDAO.findById(Long.getLong(userId));
		if (Member.activeMembership(member))
			return Response.status(Response.Status.BAD_REQUEST).entity(Error.RESERVATION_NOT_POSSIBLE).build();

		Summerhouse summerhouse = summerhouseDAO.findById(id);

		if (member.getCreditAmount() < summerhouse.getPrice()) {
			return Response.status(Response.Status.BAD_REQUEST).entity(Error.MEMBER_NOT_ENOUGH_CREDIT).build();
		}

		Date dateStart = DateUtils.getDate(dateStartStr);
		Date dateEnd = DateUtils.getDate(dateEndStr);

		if (summerhouseStatelessBean.isDateAvailable(summerhouse, dateStart, dateEnd)) {
			if (summerhouseStatelessBean.reserve(summerhouse, member, dateStart, dateEnd)) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Error.DB_RESERVATION_PERSIST)
						.build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity(Error.RESERVATION_DATE_UNAVAILABLE).build();
		}
	}
	
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input) {
		String fileName = "";
		
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

		List<InputPart> inputParts = uploadForm.get("file");
		for (InputPart inputPart : inputParts) {
		 try {
			MultivaluedMap<String, String> header = inputPart.getHeaders();
			fileName = summerhouseStatelessBean.getFileName(header);

			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);
			byte [] bytes = IOUtils.toByteArray(inputStream);
				
			//constructs upload file path
			String fullFileName = UPLOADED_FILE_PATH + fileName;
			summerhouseStatelessBean.writeFile(bytes,fullFileName);
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		}
		String output = "{\"link\":\"/pictures/" + fileName + "\"}";
		return Response.status(200)
		    .entity(output).build();
	}
	
	@GET
	@Path("byid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnMemberById(@PathParam("id") Long id, @Context HttpServletRequest request) throws ParseException, JOSEException {
		Optional<Summerhouse> member = Optional.fromNullable(summerhouseDAO.findById(id));

		if (!member.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(member.get()).build();
	}
}
