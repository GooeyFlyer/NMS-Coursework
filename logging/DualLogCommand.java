package logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The DualLogCommand class implements the LogCommand interface.
 * It combines the functionality of the FileLogCommand and ConsoleLogCommand commands.
 */
public class DualLogCommand implements LogCommand{
    ConsoleLogCommand consoleLogCommand;
    FileLogCommand fileLogCommand;

    /**
     * Constructor for the DualLogCommand class. Creates new objects for the FileLogCommand and ConsoleLogCommand commands.
     */
    public DualLogCommand() {
        consoleLogCommand = new ConsoleLogCommand();
        fileLogCommand = new FileLogCommand();
    }
    
    /**
     * The log method calls the log command from both the FileLogCommand and ConsoleLogCommand commands.
     * 
     * @param level The `level` parameter represents the logging level of the
     * message being logged.
     * @param message The `message` parameter is the message being logged.
     * @param logger An unused paramater, from the implemented instance.
     */
    @Override
    public void log(Level level, String message, Logger logger) {
        consoleLogCommand.log(level, message, logger);
        fileLogCommand.log(level, message, logger);
    }
    
}
