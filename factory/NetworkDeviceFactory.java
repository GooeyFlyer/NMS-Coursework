package factory;
import java.util.Map;

import factory.devices.*;

public class NetworkDeviceFactory implements Factory {

    @Override
    public NetworkDevice getDevice(Map<String, String> values) {
        String deviceId = values.get("deviceId");
        String name = values.get("name");

        if (name == null) {
            return null;
        }
    
        switch (name.toUpperCase()) {
            case "FIREWALL":
                return new Firewall(deviceId, name);
            case "LAPTOP":
                return new Laptop(deviceId, name);
            case "PC":
                return new PC(deviceId, name);
            case "ROUTER":
                return new Router(deviceId, name);
            case "SERVER":
                return new Server(deviceId, name);
            case "SWITCH":
                return new Switch(deviceId, name);
            case "WAP":
                return new WAP(deviceId, name);
            default:
                throw new IllegalArgumentException("Cannot find device of type " + name);
        }
    }
}
