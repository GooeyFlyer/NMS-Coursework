import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public NetworkDeviceManager(int vertexCount) {
        devices = new ArrayList<>(vertexCount);
        deviceIndexMap = new HashMap<>(vertexCount);
    }

    public void addDevice(NetworkDevice device) {
        devices.add(device);
        deviceIndexMap.put(device.getDeviceId(), devices.size() -1);
    }

    public void removeDevice(String deviceId) {
        Integer index = deviceIndexMap.get(deviceId);
        if (index == null) {
            System.out.println("Error: Vertex not found.");
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
    }

    public void configureDevice(String deviceId, DeviceConfiguration config) {
        NetworkDevice device = getDeviceById(deviceId);
        device.setConnectionInterface(config.getConnectionInterface());
        device.setIPV4(config.getIPV4());
        device.setMAC(config.getMAC());
        device.setSubnet(config.getSubnet());
    }

    public List<NetworkDevice> getDevices() {
        return devices;
    }

    public NetworkDevice getDeviceById(String deviceId) {
        return devices.get(getDeviceIndexById(deviceId));
    }

    public int getDeviceIndexById(String deviceId) {
        Integer index = deviceIndexMap.get(deviceId);
        if (index != null) {
            return index;
        } else {
            throw new IllegalArgumentException("Device ID '" + deviceId + "' not found in deviceIndexMap.");
        }
    }
}
