package loggers;

import java.lang.System.Logger.Level;

public abstract class LoggingDecorator implements Logging{
    protected Logging logging;
    
    public LoggingDecorator(Logging logging) {
        this.logging = logging;
    }
    
    public void log(Level level, String message) {
        this.logging.log(level, message);
    }
}
