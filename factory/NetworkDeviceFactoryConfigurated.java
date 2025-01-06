package factory;
import java.util.Map;

import factory.devices.NetworkDevice;

public class NetworkDeviceFactoryConfigurated implements Factory{

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
