package logging.decorating;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The FileMessage class extends the MessageDecorator class.
 * Decorates a MessageString object, to mimic the look of java's built in logging.
 */
public class FileMessage extends MessageDecorator{

    /**
     * The constructor for the FileMessage class. Passes the MessageString object to the superclass (MessageDecorator)
     * 
     * @param messageString The MessageString object that contains the message String and Level object
     */
    public FileMessage(MessageString messageString) {
        super(messageString);
    }

    /**
     * The getMessage function is where the final message is actually created, then returned.
     * Combines the current timestamp, Level object and message String into a String that mimics java's inbuilt logging.decorating
     * 
     * @return The String of the final message, including the message String, Level object and current timestamp.
     */
    @Override
    public String getMessage() {
        String timestamp = getCurrentTimestamp();
        String message = messageString.getMessage();
        return "\n[" + timestamp + "] " + "logging.FileLogCommand " + "log\n" + messageString.getLevel() + ": " + message;
    }

    /**
     * The function `getCurrentTimestamp` returns the current date and time in the format "yyyy-MM-dd
     * HH:mm:ss".
     * 
     * @return The method getCurrentTimestamp() returns the current date and time in the format
     * "yyyy-MM-dd HH:mm:ss".
     */
    private static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
}
