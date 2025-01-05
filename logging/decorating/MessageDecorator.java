package logging.decorating;

import java.util.logging.Level;

/**
 * The abstract class MessageDecorator implements the MessageString interface. This class is extended by the FileMessage class.
 * This class saves the MessageString object (which contains the message and level of the log).
 */
abstract class MessageDecorator implements MessageString {
    protected MessageString messageString;

    /**
     * The constructor for the MessageDecorator. Assignes the MessageString.
     * 
     * @param messageString The MessageString object that contains the saved message and level of the log.
     */
    public MessageDecorator(MessageString messageString) {
        this.messageString = messageString;
    }

    /**
     * The getMessage function gets the message from the MessageString object and returns.
     * 
     * @return returns a String of the message.
     */
    @Override
    public String getMessage() {
        return messageString.getMessage();
    }

    /**
     * The getLevel function gets the level from the MessageString object and returns.
     * 
     * @return returns a Level object of the level
     */
    @Override
    public Level getLevel() {
        return messageString.getLevel();
    }
}
