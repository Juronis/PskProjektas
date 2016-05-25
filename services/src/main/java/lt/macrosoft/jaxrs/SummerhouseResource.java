package lt.macrosoft.jaxrs;


import lt.macrosoft.daos.SummerhouseDAO;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/summerhouses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SummerhouseResource {
    @Inject
    SummerhouseDAO dao;


}
