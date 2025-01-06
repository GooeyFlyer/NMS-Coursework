package logging;
import java.util.logging.Level;
import java.util.logging.Logger;
import logging.decorating.LogMessage;
import logging.decorating.MessageString;

public class ConsoleLogCommand implements LogCommand{

    /**
     * The log method writes a decorated message to the log file. The message is sent through the LogMessage class.
     * 
     * @param level The `level` parameter represents the logging level of the
     * message being logged.
     * @param message The `message` parameter is the message being logged.
     * @param logger An unused paramater, from the implemented instance.
     */
    @Override
    public void log(Level level, String message, Logger logger) {
        
        // decorates the message
        // in this case does nothing, but allows for easy future edits
        MessageString messageString = new LogMessage(message, level);
        String output = messageString.getMessage();

        logger.log(level, output);
    }
    
}
