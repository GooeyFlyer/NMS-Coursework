package logging.decorating;
import java.util.logging.Level;


/**
 * The LogMessage class implements from the MessageString interface. Saves the message String and Level used for logging.
 * This class is the base class for decoratoring the message, and is used as a paramater for the FileMessage class.
 */
public class LogMessage implements MessageString{
    protected String message;
    protected Level level;

    /**
     * The constructor for the LogMessage class. Saves the paramaters of the message and level
     * 
     * @param message The message String to be saved
     * @param level The Level object to be saved
     */
    public LogMessage(String  message, Level level){
        this.message = message;
        this.level = level;
    }

    /**
     * The getMessage function returns the message.
     * 
     * @return returns a String of the message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * The getLevel function returns the level.
     * 
     * @return returns a Level object of the level
     */
    @Override
    public Level getLevel() {
        return level;
    }
}
