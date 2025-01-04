package factory;
import java.util.Map;

import factory.devices.NetworkDevice;
public interface Factory {
    NetworkDevice getDevice(Map<String, String> values);
}