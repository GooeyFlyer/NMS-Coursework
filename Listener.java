import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.Scanner;

public class Listener implements PropertyChangeListener {
    LoggingManager loggingManager;

    public Listener() {
        this.loggingManager = new LoggingManager();
        Logger logger = null;

        Scanner scanner = new Scanner(System.in);
        while (logger != null) {
            System.out.print("\nWhat logging type?");
            System.out.print("\n1) Console \n2) File \n3) Both");
            String type = scanner.nextLine();
            scanner.close();

            switch (type) {
                case "1":
                    logger = new ConsoleLogger();
                    
                case "2":
                    logger = new FileLogger();

                case "3":
                    logger = new DualLogger();

                default:
                    System.out.println("Please enter 1, 2, or 3");
            }
        }

        loggingManager.setLogger(logger);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("error".equals(event.getPropertyName())) {
            String message = "Error: " + event.getNewValue();
            loggingManager.logEvent(Level.ERROR, message);
        }

        else {
            String message = event.getPropertyName() + ", Old Value: " + event.getOldValue() + ", New Value: " + event.getNewValue();
            loggingManager.logEvent(Level.INFO, message);
        }
        System.out.println();
    }
}