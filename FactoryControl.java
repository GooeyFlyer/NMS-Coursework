import java.util.Map;

import devices.NetworkDevice;

// Invoker class
public class FactoryControl {
    private Factory factory;

    // Sets the command for the button
    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    // Invokes the command
    public NetworkDevice getDevice(Map<String, String> values) {
        return factory.getDevice(values);
    }
}