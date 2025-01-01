import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import devices.NetworkDevice;

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

    public NetworkDeviceManager(int vertexCount, PropertyChangeListener listener) {
        devices = new ArrayList<>(vertexCount);
        deviceIndexMap = new HashMap<>(vertexCount);

        support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(listener);
    }

    public void addDevice(NetworkDevice device) {
        List<NetworkDevice> oldDevices = devices;
        devices.add(device);
        deviceIndexMap.put(device.getDeviceId(), devices.size() -1);
        support.firePropertyChange("devices", oldDevices.toString(), devices.toString());
    }

    public void removeDevice(String deviceId) {
        List<NetworkDevice> oldDevices = devices;
        Integer index = deviceIndexMap.get(deviceId);
        if (index == null) {
            System.out.println("Error: Vertex not found.");
            support.firePropertyChange("error", "", "Vertex not found.");
            return;
        }

        // Remove the device from the list and map
        devices.remove((int) index);
        deviceIndexMap.remove(deviceId);

        // change map
        deviceIndexMap.clear();
        for (int i = 0; i < devices.size(); i++) {
            deviceIndexMap.put(devices.get(i).getDeviceId(), i);
        }
        support.firePropertyChange("devices", oldDevices.toString(), devices.toString());
    }

    /* public void configureDevice(String deviceId, DeviceConfiguration config) {
        NetworkDevice device = getDeviceById(deviceId);
        NetworkDevice oldDevice = device;
        device.setConnectionInterface(config.getConnectionInterface());
        device.setIPV4(config.getIPV4());
        device.setMAC(config.getMAC());
        device.setSubnet(config.getSubnet());
    } */

    public List<NetworkDevice> getDevices() {
        return devices;
    }

    public Map<String, Integer> getDeviceIndexMap() {
        return deviceIndexMap;
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
            support.firePropertyChange("error", "", "Device " + deviceId + " not found");
            throw new IllegalArgumentException("Device " + deviceId + " not found");
        }
    }

    public int getDeviceIndexByObject(NetworkDevice networkDevice){
        return getDeviceIndexById(networkDevice.getDeviceId());
    }
}
