import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.System.Logger.Level;

public class Listener implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String message = "Property changed: " + event.getPropertyName() + ", Old Value: " + event.getOldValue() + ", New Value: " + event.getNewValue();
        Level level = Level.INFO;

        LoggingManager loggingManager = new LoggingManager();

        loggingManager.logEvent(level, message);
    }
}