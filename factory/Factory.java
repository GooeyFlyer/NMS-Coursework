package factory;
import java.util.Map;

import factory.devices.NetworkDevice;

/**
 * The interface Factory. Implemented by NetworkDeviceFactory and NetworkDeviceFactoryConfigurated.
 */
public interface Factory {

    /**
     * The getDevice method creates a new device object, using values from the NMS.
     * 
     * @param values A dictionary of values relating to a device,
     * including deviceId, name, and configurations if applicable.
     * @return a NetworkDevice object of a certain type, depending on the values paramater, and the Factory being used.
     */
    NetworkDevice getDevice(Map<String, String> values);
}