package lt.macrosoft.setup;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class Bootstrap {
    
    private static final Logger logger = Logger.getLogger("Bootstrap");
    
    /**
     * System startup
     */
    @Inject
    private SystemStartup systemStartup;
    
    /**
     * Post-construct
     */
    @PostConstruct
    public void postConstruct() {
        systemStartup.init();
    }
}
