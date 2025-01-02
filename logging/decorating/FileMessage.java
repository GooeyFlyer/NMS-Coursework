package logging.decorating;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileMessage extends MessageDecorator{
    public FileMessage(LogMessage logMessage) {
        super(logMessage);
    }

    @Override
    public String getMessage() {
        String timestamp = getCurrentTimestamp();
        String message = logMessage.getMessage();
        return "\n[" + timestamp + "] " + "FileLogCommand " + "log\n" + logMessage.getLevel() + ": " + message;
    }

    // Helper method to get the current timestamp in a readable format
    private static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
}
