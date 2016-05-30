package lt.macrosoft.jaxrs;

import lt.macrosoft.daos.ActivityDAO;
import lt.macrosoft.entities.Activity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Arnas on 2016-05-30.
 */
@Path("/activities")
@Stateless
public class ActivityResource {
    @Inject
    ActivityDAO activityDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivities() {
        return Response.accepted().entity(activityDAO.findAll()).build();
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addActivity(Activity activity) {
        activityDAO.save(activity);
        return Response.status(Response.Status.CREATED).entity(activity).build();
    }
}
