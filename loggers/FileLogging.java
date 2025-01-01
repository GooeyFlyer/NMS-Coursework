package loggers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.Logger.Level;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogging extends LoggingDecorator{
    public FileLogging(Logging logging) {
        super(logging);
        clearHistory();
    }

    public void clearHistory() {
        // Create a File object pointing to the file to be deleted
        File file = new File("logs.txt");

        // Delete the file
        if (file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    // Helper method to get the current timestamp in a readable format
    private static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

    public void writeToFile(Level level, String message){
        String filePath = "./logs.txt"; // specify the file path

        // Writing content to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String timestamp = getCurrentTimestamp();
            writer.write("[" + timestamp + "] " + level + " " + message);
            writer.newLine();  // Move to the next line after writing the message
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the log file: " + e.getMessage());
        }
    }

    @Override
    public void log(Level level, String message) {
        super.log(level, message);
        System.out.println(message);
        System.out.println("File printing");
        writeToFile(level, message);
    }
}
