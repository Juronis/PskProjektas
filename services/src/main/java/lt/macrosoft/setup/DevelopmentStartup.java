package lt.macrosoft.setup;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import lt.macrosoft.daos.SummerhouseDAO;
import lt.macrosoft.entities.Summerhouse;
import lt.macrosoft.entities.Summerhouse.District;

/**
 * Initializes the system at start-up for developer testing.
 * @author Ryan Cuprak
 */
@Alternative
public class DevelopmentStartup extends SystemStartup {
    
    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger("DevelopmentStartup");
    
    @Inject
    private  SummerhouseDAO summerhouseDao;
  
    /**
     * Initializes the system
     */
    @Override
    public void init() {
        logger.info("Development startup initialized...");
        Summerhouse firstSummerhouse = new Summerhouse();
        firstSummerhouse.setDescription("Puikiausias vasarnamis");
        firstSummerhouse.setDistrict(District.MOLETAI);
        firstSummerhouse.setPrice(7);
        firstSummerhouse.setNumberOfPlaces(8);
        firstSummerhouse.setName("Ramybe");
        firstSummerhouse.setDateFrom("2016-05-05");
        firstSummerhouse.setDateTo("2016-09-05");
        firstSummerhouse.setImageUrl("http://mstcontractors.com/mstc/images/stories/nice-house-main.jpg");
        summerhouseDao.save(firstSummerhouse);
    }
    
    
    
}
