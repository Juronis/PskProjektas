package lt.macrosoft.jaxrs;


import com.nimbusds.jose.JOSEException;
import lt.macrosoft.daos.DistrictDAO;
import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.daos.SummerhouseDAO;
import lt.macrosoft.entities.District;
import lt.macrosoft.entities.Member;
import lt.macrosoft.entities.Summerhouse;
import lt.macrosoft.utils.AuthUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.List;

@Stateless
@Path("/summerhouses")
public class SummerhouseResource {
    @Inject
    MemberDAO memberDAO;
    @Inject
    SummerhouseDAO summerhouseDAO;
    @Inject
    DistrictDAO districtDAO;

    @Context
    private HttpServletRequest httpRequest;

    public SummerhouseResource() {
    }

    @GET
    @Path("districts")
    @Produces(MediaType.APPLICATION_JSON)
    public List<District> getDistricts() {
        return districtDAO.findAll();
    }

    @GET
    public List<Summerhouse> getSummerhouses(@QueryParam("district") String district,
                                             @DefaultValue("0") @QueryParam("priceMin") double priceMin,
                                             @DefaultValue("0") @QueryParam("numPlaces") int numPlaces) {
        return summerhouseDAO.findAllCustom(district, priceMin, numPlaces).get();
    }

    @POST
    @Path("reserve/{summerhouseId}/{dateStart}/{dateEnd}")
    public Response reserveSummerhouse(@PathParam("summerhouseId") Long id,
                                       @PathParam("dateStart") String dateStart,
                                       @PathParam("dateEnd") String dateEnd) {
        String userId = null;
        try {
            userId = AuthUtils.getSubject(httpRequest.getHeader(AuthUtils.AUTH_HEADER_KEY));
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
        if (userId == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        Member member = memberDAO.findById(Long.getLong(userId));
        Summerhouse summerhouse = summerhouseDAO.findById(id);

        if (member.getCreditAmount() < summerhouse.getPrice()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Error.MEMBER_NOT_ENOUGH_CREDIT).build();
        }

        //TODO
        return Response.ok().build();
    }
}
