package logging.decorating;

import java.util.logging.Level;

/**
 * The interface MessageString. Used by the MessageDecorator and LogMessage classes.
 */
public interface MessageString {
    /**
     * The getMessage function returns the message.
     * 
     * @return The message String that was saved during class initialisation.
     */
    public String getMessage(); 

    /**
     * The getLevel function returns the logging level. This can be INFO, WARNING, or SEVERE.
     * 
     * @return The logging level that was saved during class initialisation.
     */
    public Level getLevel();  
}