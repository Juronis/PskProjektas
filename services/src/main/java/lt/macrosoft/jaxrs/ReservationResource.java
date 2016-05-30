package lt.macrosoft.jaxrs;

import com.google.common.base.Optional;
import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.daos.ReservationDAO;
import lt.macrosoft.entities.Member;
import lt.macrosoft.entities.Reservation;
import lt.macrosoft.utils.DateUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Arnas on 2016-05-26.
 */

@Path("/reservations")
public class ReservationResource {
    @Inject
    ReservationDAO reservationDAO;
    @Inject
    MemberDAO memberDAO;

    @Path("dates/{summerhouseId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSummerhouseReservationDates(@PathParam("summerhouseId") Long summerhouseId) {
        Optional<List<Date[]>> list = reservationDAO.findReservedDatesBySummerhouse(summerhouseId);
        if(!list.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(list.get()).build();
        }
    }

    //Testing
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reservation> getAllReservations() {
        return reservationDAO.findAll();
    }

    @Path("membersByDate/{dateStart}/{dateEnd}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMembersByReservationDate(@PathParam("dateStart") String dateStart,
                                                    @PathParam("dateEnd") String dateEnd) {
        Optional<List<Reservation>> reservations = reservationDAO.findByDate(DateUtils.getDate(dateStart), DateUtils.getDate(dateEnd));
        if(reservations.isPresent()) {
            List<Member> memberList = new ArrayList<>();
            for(Reservation reservation : reservations.get()) {
                memberList.add(reservation.getMember());
            }
            return Response.ok().entity(memberList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Error.RESERVATION_RESERVATIONS_NOT_FOUND).build();
        }
    }
}
