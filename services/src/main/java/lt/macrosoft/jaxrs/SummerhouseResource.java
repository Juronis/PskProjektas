package lt.macrosoft.jaxrs;


import com.nimbusds.jose.JOSEException;
import lt.macrosoft.beans.SummerhouseStatelessBean;
import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.daos.SummerhouseDAO;
import lt.macrosoft.entities.Summerhouse.District;
import lt.macrosoft.entities.Member;
import lt.macrosoft.entities.Summerhouse;
import lt.macrosoft.utils.AuthUtils;
import lt.macrosoft.utils.DateUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Stateless
@Path("/summerhouses")
public class SummerhouseResource {
    @EJB
    SummerhouseStatelessBean summerhouseStatelessBean;
    @Inject
    MemberDAO memberDAO;
    @Inject
    SummerhouseDAO summerhouseDAO;

    @Context
    private HttpServletRequest httpRequest;

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

    @POST
    @Path("reserve/{summerhouseId}/{dateStart}/{dateEnd}")
    public Response reserveSummerhouse(@PathParam("summerhouseId") Long id,
                                       @PathParam("dateStart") String dateStartStr,
                                       @PathParam("dateEnd") String dateEndStr) {
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

        Date dateStart = DateUtils.getDate(dateStartStr);
        Date dateEnd = DateUtils.getDate(dateEndStr);

        if(summerhouseStatelessBean.isDateAvailable(summerhouse, dateStart, dateEnd)) {
            if(summerhouseStatelessBean.reserve(summerhouse, member, dateStart, dateEnd)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Error.DB_RESERVATION_PERSIST).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(Error.RESERVATION_DATE_UNAVAILABLE).build();
        }
    }
}
