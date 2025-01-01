import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.System.Logger.Level;

import loggers.BaseLogging;
import loggers.ConsoleLogging;

public class Listener implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String message = "Property changed: " + event.getPropertyName() + ", Old Value: " + event.getOldValue() + ", New Value: " + event.getNewValue();
        Level level = Level.INFO;

        ConsoleLogging consoleLogging = new ConsoleLogging(new BaseLogging());
        consoleLogging.log(level, message);
    }
}