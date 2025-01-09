package factory;
import java.util.Map;

import factory.devices.NetworkDevice;

/** The NetworkDeviceFactoryConfigurated class. Implements the Factory interface.
 * This class uses the getDevice method from the other factory first,
 * then adds configuration values to the device it recieves.
*/
public class NetworkDeviceFactoryConfigurated implements Factory{

    /**
     * The getDevice method creates a new device object, using values from the NMS.
     * 
     * @param values A dictionary of values relating to a device,
     * including deviceId, name, and configurations if applicable.
     * @return a NetworkDevice object of a certain type, depending on the values paramater.
     */
    @Override
    public NetworkDevice getDevice(Map<String, String> values) {
        NetworkDevice device = new NetworkDeviceFactory().getDevice(values);

        device.setConnectionInterface(values.get("Interface"));
        device.setIPV4(values.get("IPV4"));
        device.setMAC(values.get("MAC"));
        device.setSubnet(values.get("Subnet"));

        return device;
    }
}
