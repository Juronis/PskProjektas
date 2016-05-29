package lt.macrosoft.setup;

import java.util.logging.Logger;

public class SystemStartup {
    
    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger("SystemStartup");
    
    /**
     * Performs initialization
     */
    public void init() {
        logger.info("Production system startup - initializing the system.");
    }
    
}
