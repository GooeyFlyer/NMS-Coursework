package loggers;

import java.lang.System.Logger.Level;

public class BaseLogging implements Logging{
    @Override
    public void log(Level level, String message) {
        System.out.print("\n");
        LoggingManager loggingManager = new LoggingManager();
        loggingManager.logEvent(level, message);
    }
}
