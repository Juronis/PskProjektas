package lt.macrosoft.jaxrs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;
import lt.macrosoft.daos.ParameterDAO;
import lt.macrosoft.entities.Parameter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.text.ParseException;

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
    @Path("get")
    public Response findParameterValue(String json) throws ParseException, JOSEException {
        //String json = "{\"name\":\"MEMBERSHIP_PRICE\"}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj;
        String parameter;
        try {
            actualObj = mapper.readTree(json);
            JsonNode jsonNode1 = actualObj.get("name");
            if (jsonNode1 == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            parameter = jsonNode1.textValue();

        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return Response.status(Response.Status.NOT_FOUND).build();
        }


        if(dao.findParameterValue(parameter).isPresent()){
            return Response.ok().entity(dao.findParameterValue(parameter).get()).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("set")
    public Response updateParameterValue(Parameter parameter) throws ParseException, JOSEException {
    //String newval = "{\"name\":\"MEMBERSHIP_PRICE\",\"pvalue\":\"50\"}";
        Optional<Parameter> updateParameter = dao.findParameterValue(parameter.getName());
        if (!updateParameter.isPresent()) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(AuthResource.NOT_FOUND_MSG).build();
        }
        Parameter updatingParameter = updateParameter.get();
        if (parameter.getPvalue() != null) {
            updatingParameter.setPvalue(parameter.getPvalue());
            dao.save(updatingParameter);
        }
        return Response.ok().build();
    }
}
