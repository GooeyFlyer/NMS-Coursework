package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

import logging.decorating.*;

/**
 * Thie FileLogCommand class implements the LogCommand interface.
 * Writes logs to a file.
 */
public class FileLogCommand implements LogCommand{
    // LOG_FILE - a future development is to allow the user to set this.
    private static final String LOG_FILE = "logfile.txt";  

    /** 
     * The constructor for the FileLogCommand class. This code deletes the contents of any existing log file.
     */
    public FileLogCommand() {
        try {
            // Clears the file
            FileWriter fileWriter = new FileWriter(LOG_FILE, false);
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("An error occurred while clearing the log file: " + e.getMessage());
        }
    }

    /**
     * The log method writes a decorated message to the log file. The message is sent through the LogMessage class.
     * Then it is decorated by the FileMessage class, in order to mimic java's own logging outputs.
     * 
     * @param level The `level` parameter represents the logging level of the
     * message being logged.
     * @param message The `message` parameter is the message being logged.
     * @param logger An unused paramater, from the implemented instance.
     */
    @Override
    public void log(Level level, String message, Logger logger) { 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {

            // decorates the message
            // in this case, decorates the message to mimic the result of logger.log()
            MessageString messageString = new FileMessage(new LogMessage(message, level));
            String output = messageString.getMessage();

            writer.write(output);
            writer.newLine();  // Move to the next line
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the log file: " + e.getMessage());
        }
    }

    
    
}
