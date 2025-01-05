package logging;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;

import java.util.Scanner;

public class Listener implements PropertyChangeListener {
    private LoggingManager loggingManager;

    public Listener() {
        this.loggingManager = new LoggingManager();

        // This code allows the programmer to use Console, File or Dual Logging to log the NMS.
        // Currently it is set to always use Console Logging,
        // but can be changed to get the user's choice by switching the commented line.
        
        LogCommand logCommand = new ConsoleLogCommand();
        //REMOVE COMMENT TO ALLOW USER CHOICE OF LOGGING: LogCommand logCommand = getLogCommandChoice();
        
        loggingManager.setLogCommand(logCommand);
        loggingManager.logEvent(Level.INFO, "----------Beginning program----------\n");
    }

    // Method to get user input for logging type.
    public LogCommand getLogCommandChoice() {
        LogCommand logCommand = null;
        Scanner scanner = new Scanner(System.in);
        while (logCommand == null) {
            System.out.print("\nWhat logging type?");
            System.out.print("\n1) Console \n2) File \n3) Both\n");
            String type = scanner.nextLine();

            switch (type) {
                case "1":
                    logCommand = new ConsoleLogCommand();
                    break;
                case "2":
                    System.out.println("Case 2");
                    logCommand = new FileLogCommand();
                    break;
                case "3":
                    logCommand = new DualLogCommand();
                    break;
                default:
                    System.out.println("Please enter 1, 2, or 3");
                    break;
            }
        }
        scanner.close();
        return logCommand;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("error".equals(event.getPropertyName())) {
            String message = "Error: " + event.getNewValue();
            loggingManager.logEvent(Level.SEVERE, message);
        }

        else if ("warn".equals(event.getPropertyName())) {
            String message = "" + event.getNewValue();
            loggingManager.logEvent(Level.WARNING, message);
        }

        else {
            String message = event.getPropertyName() + ", Old Value: " + event.getOldValue() + ", New Value: " + event.getNewValue();
            loggingManager.logEvent(Level.INFO, message);
        }
        System.out.println();
    }
}