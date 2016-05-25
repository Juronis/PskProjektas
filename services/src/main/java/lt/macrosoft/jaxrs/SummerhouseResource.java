package lt.macrosoft.jaxrs;



import com.google.common.base.Optional;
import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.daos.MemberDAOImpl;
import lt.macrosoft.daos.SummerhouseDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import lt.macrosoft.entities.Member;

import java.util.List;

import static lt.macrosoft.daos.MemberDAO.*;

@Stateless
@ApplicationPath("/resource")
@Path("persons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)



public class SummerhouseResource extends Application {

    @Inject
    MemberDAO dao;

    @GET
    @Path("{id}")
    public String getPerson(@PathParam("id") Long id) {
        String membername = "";
    Optional<Member> member = dao.getMemberById(id);
        if (member.isPresent()) {
            membername = member.get().getEmail();
        } else {
            membername = "Nerasta";
        }

        //return "{\"testas\":\"" + id + "blablddsadsdasdaa\"}";
        return membername;
    }
    
    
}
