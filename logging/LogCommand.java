package logging;
import java.util.logging.Logger;
import java.util.logging.Level;

public interface LogCommand {
    void log(Level level, String message, Logger logger);
}
