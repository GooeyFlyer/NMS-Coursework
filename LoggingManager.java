
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
    }

    public Logger setLogger(Logger logger){
        this.logger = logger;
        return this.logger;
    }

    public void logEvent(Level level, String message) {
        logger.log(level, message);
    }
}
