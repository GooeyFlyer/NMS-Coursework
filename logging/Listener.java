package logging;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;

import java.util.Scanner;

/**
 * The Listener class is an observer for the NetworkDeviceManager, RouteManager, and the ReadFiles classes.
 * It can create INFO, WARNING, OR SEVERE log events.
 */
public class Listener implements PropertyChangeListener {
    private LoggingManager loggingManager;

    /**
     * The constructor for the Listener class. 
     * 
     * This code allows the programmer to use Console, File or Dual Logging to log the NMS.
     * Currently it is set to always use Console Logging,
     * but can be changed to get the user's choice by switching the commented line.
     * 
     * A command pattern is used to change the function of the loggingManager, depending on the choice.
     * 
     * Also creates a start message for the program.
     */
    public Listener() {
        this.loggingManager = new LoggingManager();
        
        LogCommand logCommand = new ConsoleLogCommand();
        //REMOVE COMMENT TO ALLOW USER CHOICE OF LOGGING: LogCommand logCommand = getLogCommandChoice();
        
        loggingManager.setLogCommand(logCommand);
        loggingManager.logEvent(Level.INFO, "----------Beginning program----------\n");
    }

    /**
     * The getLogCommandChoice method takes a users input to select the logging method.
     * 
     * @return Returns a LogCommand object, depending on the users choice.
     */
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

    /**
     * The `propertyChange` method logs different levels of messages based on the property change event
     * received. Can log INFO, WARNING or SEVERE logs.
     * 
     * @param event The `event` parameter is of type `PropertyChangeEvent`.
     * It represents the event that occurred when a bound property is changed.
     * This event contains information such as the property name, old value, and new value of the
     * property that was changed.
     */
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