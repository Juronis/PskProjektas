package lt.macrosoft.jaxrs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Stateless
@ApplicationPath("/resource")
@Path("persons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SummerhouseResource extends Application {

    @GET
    @Path("{id}")
    public String getPerson(@PathParam("id") Long id) {
        return "blablddsadsdasdaa";
    }
    
    
}
