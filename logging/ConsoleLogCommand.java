package logging;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLogCommand implements LogCommand{

    @Override
    public void log(Level level, String message, Logger logger) {
        logger.log(level, message);
    }
    
}
