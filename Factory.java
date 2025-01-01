import java.util.Map;

import devices.NetworkDevice;
public interface Factory {
    NetworkDevice getDevice(Map<String, String> values);
}