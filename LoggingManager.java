
import java.lang.System.Logger;
import java.lang.System.Logger.Level;

/**
 * This class does the logging of the actions performed by the
 * system. 
 */

// Used by both manager classes
public class LoggingManager {
    private Logger logger;

    public LoggingManager() {
        logger = System.getLogger(getClass().getName());
    }

    public void logEvent(Level level, String message) {
        //logger.log(level, message);
    }
}
