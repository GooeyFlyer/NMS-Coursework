/**
 * This class does the logging of the actions performed by the
 * system. 
 */
public class LoggingManager {
    private Logger logger;

    public LoggingManager() {
        logger = Logger.getLogger(getClass().getName());
    }

    public void logEvent(Level level, String message) {

    }

}
