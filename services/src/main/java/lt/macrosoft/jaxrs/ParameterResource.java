package lt.macrosoft.jaxrs;

import java.text.ParseException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;

import lt.macrosoft.daos.ParameterDAO;
import lt.macrosoft.entities.Parameter;

/**
 * Created by Main on 5/29/2016.
 */


@Path("/parameters")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParameterResource {

    @Inject
    ParameterDAO dao;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("get/{parameter}")
    public Response findParameterValue(@PathParam("parameter") String parameter) throws ParseException, JOSEException {
        //String json = "{\"name\":\"MEMBERSHIP_PRICE\"}";



        if(dao.findParameterValue(parameter).isPresent()){
            return Response.ok().entity(dao.findParameterValue(parameter).get()).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("update")
    public Response updateParameterValue(Parameter parameter) throws ParseException, JOSEException {
    //String newval = "{\"name\":\"MEMBERSHIP_PRICE\",\"pvalue\":\"50\"}";
        Optional<Parameter> updateParameter = dao.findParameterValue(parameter.getName());
        if (!updateParameter.isPresent()) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(AuthResource.NOT_FOUND_MSG).build();
        }
        Parameter newParameter = updateParameter.get();
        if (parameter.getPvalue() != null) {
            newParameter.setPvalue(parameter.getPvalue());
            dao.save(newParameter);
        }
        return Response.ok().build();
    }
}
