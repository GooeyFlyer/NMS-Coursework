package logging.decorating;
import java.util.logging.Level;

public class LogMessage implements MessageString{

    String message;
    Level level;
    public LogMessage(String  message, Level level){
        this.message = message;
        this.level = level;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Level getLevel() {
        return level;
    }
}
