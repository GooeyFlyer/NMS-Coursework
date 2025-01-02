import java.util.logging.*;
import logging.LogCommand;

/**
 * This class does the logging of the actions performed by the
 * system. 
 */

// Used by both manager classes
public class LoggingManager {
    private Logger logger;
    private LogCommand logCommand;

    public LoggingManager() {
        logger = Logger.getLogger(getClass().getName());
    }

    public LogCommand setLogCommand(LogCommand logCommand){
        this.logCommand = logCommand;
        return this.logCommand;
    }

    public void logEvent(Level level, String message) {
        logCommand.log(level, message, logger);
    }
}
