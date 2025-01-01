package loggers;

import java.lang.System.Logger.Level;

public class ConsoleLogging extends LoggingDecorator{
    public ConsoleLogging(Logging logging) {
        super(logging);
    }

    @Override
    public void log(Level level, String message) {
        super.log(level, message);
    }
}