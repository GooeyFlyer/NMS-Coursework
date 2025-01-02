package logging.decorating;

import java.util.logging.Level;

abstract class MessageDecorator implements MessageString {

    protected LogMessage logMessage;

    public MessageDecorator(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    @Override
    public String getMessage() {
        return logMessage.getMessage();
    }

    @Override
    public Level getLevel() {
        return logMessage.getLevel();
    }
}
