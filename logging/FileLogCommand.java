package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

public class FileLogCommand implements LogCommand{
    private static final String LOG_FILE = "logfile.txt";  // Specify the log file

    public FileLogCommand() {
        try {
            // Create a FileWriter object in non-append mode (overwrites the file)
            FileWriter fileWriter = new FileWriter(LOG_FILE, false);

            // Closing the FileWriter immediately truncates the file to zero length
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the log file: " + e.getMessage());
        }
    }

    @Override
    public void log(Level level, String message, Logger logger) { 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = getCurrentTimestamp();
            writer.write("\n[" + timestamp + "] " + "FileLogCommand " + "log\n" + level + ": " + message);
            writer.newLine();  // Move to the next line after writing the message
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the log file: " + e.getMessage());
        }
    }

    // Helper method to get the current timestamp in a readable format
    private static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
    
}
