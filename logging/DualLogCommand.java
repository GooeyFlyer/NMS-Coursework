package logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DualLogCommand implements LogCommand{
    ConsoleLogCommand consoleLogCommand;
    FileLogCommand fileLogCommand;

    public DualLogCommand() {
        consoleLogCommand = new ConsoleLogCommand();
        fileLogCommand = new FileLogCommand();
    }

    @Override
    public void log(Level level, String message, Logger logger) {
        consoleLogCommand.log(level, message, logger);
        fileLogCommand.log(level, message, logger);
    }
    
}
