import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import factory.devices.NetworkDevice;
import logging.Listener;

import java.beans.PropertyChangeSupport;

/**
 * This class acts as a manager class through which
 * the devices represented by NetworkDevice shall be
 * maintained. This class shall allow adding and
 * removing of devices, and also set configuration
 * for each device added to it. Any other class should use
 * this class to get the latest set of devices maintained
 * by the system.
 * Note: The list of devices should not be held in any
 * other class.
 */
public class NetworkDeviceManager {
    private List<NetworkDevice> devices;
    private Map<String, Integer> deviceIndexMap;
    private PropertyChangeSupport support;

    public NetworkDeviceManager(int vertexCount, Listener listener) {
        devices = new ArrayList<>(vertexCount);
        deviceIndexMap = new HashMap<>(vertexCount);

        support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(listener);
    }

    public void addDevice(NetworkDevice device) {
        devices.add(device);
        deviceIndexMap.put(device.getDeviceId(), devices.size() -1);

        support.firePropertyChange("Added Device", null, device.getDeviceId());
    }

    public void removeDevice(String deviceId) {
        Integer index = getDeviceIndexById(deviceId);

        // Remove the device from the list and map
        devices.remove((int) index);
        deviceIndexMap.remove(deviceId);

        // change map
        deviceIndexMap.clear();
        for (int i = 0; i < devices.size(); i++) {
            deviceIndexMap.put(devices.get(i).getDeviceId(), i);
        }
        support.firePropertyChange("Removed Device", deviceId, null);
    }

    public List<NetworkDevice> getDevices() {
        return devices;
    }

    public Map<String, Integer> getDeviceIndexMap() {
        return deviceIndexMap;
    }

    public String getDevicesAsString() {
        String output = "";
        for (NetworkDevice device : devices) {
            output = output + device.getDeviceId() + " ";
        }
        return output;
    }

    public NetworkDevice getDeviceByIndex(int index) {
        return devices.get(index);
    }

    public NetworkDevice getDeviceById(String deviceId) {
        return devices.get(getDeviceIndexById(deviceId));
    }

    public int getDeviceIndexById(String deviceId) {
        Integer index = deviceIndexMap.get(deviceId);
        if (index != null) {
            return index;
        } else {
            if (devices.size() == 0) {
                String message = "devices list empty. Check devices.txt or inputs";
                support.firePropertyChange("error", "", message);
                throw new IllegalArgumentException(message);
            }
            else {
                String message = "Device " + deviceId + " not found";
                support.firePropertyChange("error", "", message);
                throw new IllegalArgumentException(message);
            }

        }
    }

    public int getDeviceIndexByObject(NetworkDevice networkDevice){
        return getDeviceIndexById(networkDevice.getDeviceId());
    }
}
