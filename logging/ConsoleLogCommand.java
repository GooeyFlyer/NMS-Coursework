package logging;
import java.util.logging.Level;
import java.util.logging.Logger;
import logging.decorating.LogMessage;
import logging.decorating.MessageString;

public class ConsoleLogCommand implements LogCommand{

    @Override
    public void log(Level level, String message, Logger logger) {
        
        // decorates the message
        // in this case does nothing, but allows for easy future edits
        MessageString messageString = new LogMessage(message, level);
        String output = messageString.getMessage();

        logger.log(level, output);
    }
    
}
