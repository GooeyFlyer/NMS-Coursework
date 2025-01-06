package logging;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * The interface LogCommand. Used in the logging command pattern. Implemented by different types of LogCommands.
 */
public interface LogCommand {
    /**
     * This function logs a message at a specified level using a given logger.
     * 
     * @param level The `Level` parameter represents the logging level of the message. It indicates the
     * severity or importance of the message being logged, such as INFO, WARNING, ERROR, etc.
     * @param message The `message` parameter is a string that represents the message to be logged.
     * @param logger The `logger` parameter is an object of type `Logger`. It is used to specify the
     * logger instance that will be used to log the message at the specified level. 
     */
    void log(Level level, String message, Logger logger);
}
