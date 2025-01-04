package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

import logging.decorating.*;

public class FileLogCommand implements LogCommand{
    private static final String LOG_FILE = "logfile.txt";  // Specify the log file

    public FileLogCommand() {
        try {
            // Clears the file
            FileWriter fileWriter = new FileWriter(LOG_FILE, false);
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("An error occurred while writing to the log file: " + e.getMessage());
        }
    }

    @Override
    public void log(Level level, String message, Logger logger) { 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {

            // decorates the message
            // in this case, decorates the message to mimic the result of logger.log()
            MessageString messageString = new FileMessage(new LogMessage(message, level));
            String output = messageString.getMessage();

            writer.write(output);
            writer.newLine();  // Move to the next line after writing the message
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the log file: " + e.getMessage());
        }
    }

    
    
}
