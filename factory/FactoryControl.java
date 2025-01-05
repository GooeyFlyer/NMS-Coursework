package factory;
import java.util.Map;
import factory.devices.NetworkDevice;

// Invoker class
public class FactoryControl {
    private Factory factory;

    // Sets the command for the button
    public void setFactory(Boolean configuration) {
        if (configuration) {
            this.factory = new NetworkDeviceFactoryConfigurated();
        }
        else {
            this.factory = new NetworkDeviceFactory();
        }
    }

    // Invokes the command
    public NetworkDevice getDevice(Map<String, String> values) {
        return factory.getDevice(values);
    }
}