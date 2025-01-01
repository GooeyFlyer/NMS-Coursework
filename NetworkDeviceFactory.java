import devices.Firewall;
import devices.Laptop;
import devices.NetworkDevice;
import devices.PC;
import devices.Router;
import devices.Server;
import devices.Switch;
import devices.WAP;

public class NetworkDeviceFactory {
    public NetworkDevice getDevice(String deviceId, String name) {
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
