package logging;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This class does the logging of the actions performed by the
 * system. Used by the Listener.
 */
public class LoggingManager {
    private Logger logger;
    private LogCommand logCommand;

    /**
     * Constructor for the RouteManager class. Assignes a Logger.
     */
    public LoggingManager() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * Sets a LogCommand. This is used to actually log actions.
     * 
     * @param logCommand The LogCommand object to be set.
     */
    public void setLogCommand(LogCommand logCommand){
        this.logCommand = logCommand;
    }


    /**
     * The logEvent function logs a message with a specified log level.
     * Uses the LogCommand object for logging, which is either ConsoleLogCommand, FileLogCommand, or DualLogCommand.
     * 
     * @param level Level represents the severity or importance of the log message. 
     * Includes values like INFO, WARNING, ERROR, etc.
     * @param message The `message` parameter in the `logEvent` method is a string that represents the
     * message to be logged.
     */
    public void logEvent(Level level, String message) {
        logCommand.log(level, message, logger);
    }
}
