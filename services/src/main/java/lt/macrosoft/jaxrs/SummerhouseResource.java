package lt.macrosoft.jaxrs;


import lt.macrosoft.daos.DistrictDAO;
import lt.macrosoft.daos.SummerhouseDAO;
import lt.macrosoft.entities.District;
import lt.macrosoft.entities.Summerhouse;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.util.List;

@Path("/summerhouses")
public class SummerhouseResource {
    @Context
    private HttpServletRequest httpRequest;

    @Inject
    SummerhouseDAO summerhouseDAO;
    @Inject
    DistrictDAO districtDAO;

    public SummerhouseResource() {
    }

    @GET
    @Path("districts")
    public List<District> getDistricts() {
        return districtDAO.findAll();
    }

    @GET
    public List<Summerhouse> getSummerhouses(@QueryParam("district") String district,
                                             @DefaultValue("0") @QueryParam("priceMin") double priceMin,
                                             @DefaultValue("0") @QueryParam("numPlaces") int numPlaces) {
        return summerhouseDAO.findAllCustom(district, priceMin, numPlaces).get();
    }
}
