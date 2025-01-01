import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.System.Logger.Level;

public class Listener implements PropertyChangeListener {
    LoggingManager loggingManager;

    public Listener() {
        this.loggingManager = new LoggingManager();
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