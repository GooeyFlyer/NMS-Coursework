package factory;
import java.util.Map;
import factory.devices.NetworkDevice;

/**
 * The FactoryContol class acts as the invoker class to set the Factory implementation, used by the NMS.
 */
public class FactoryControl {
    private Factory factory;

    /**
     * The setFactory method sets the factory implementation that will be used to get the device.
     * 
     * @param configuration A boolean value to determine if the configuration factory will be used.
     */
    public void setFactory(Boolean configuration) {
        if (configuration) {
            this.factory = new NetworkDeviceFactoryConfigurated();
        }
        else {
            this.factory = new NetworkDeviceFactory();
        }
    }

    /**
     * The getDevice method calls the getDevice method of the factory implementation.
     * 
     * @param values A dictionary of values relating to a device,
     * including deviceId, name, and configurations if applicable
     * @return@return a NetworkDevice object of a certain type, depending on the values paramater, and the Factory being used.
     */
    public NetworkDevice getDevice(Map<String, String> values) {
        return factory.getDevice(values);
    }
}