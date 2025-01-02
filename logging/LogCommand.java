package logging;
import java.util.logging.*;

public interface LogCommand {
    void log(Level level, String message, Logger logger);
}
