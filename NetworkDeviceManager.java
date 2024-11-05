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

    private boolean[][] adjMatrix;
    private List<NetworkDevice> devices;
    private Map<String, Integer> deviceIndexMap;

    public NetworkDeviceManager(int vertexCount) {
        adjMatrix = new boolean[vertexCount][vertexCount];
        devices = new ArrayList<>(vertexCount);
        deviceIndexMap = new HashMap<>(vertexCount);
    }

    public void addDevice(NetworkDevice device) {
        devices.add(device);
        deviceIndexMap.put(device.getDeviceId(), devices.size() -1);
    }

    public void addEdge(int source, int destination) {
        if (source >= devices.size() || destination >= devices.size()) {
            System.out.println("Error: Vertex index out of bounds.");
            return;
        }
        adjMatrix[source][destination] = true;
        adjMatrix[destination][source] = true;
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

        // Shift rows and columns in adjacency matrix
        for (int i = index; i < devices.size(); i++) {
            for (int j = 0; j < devices.size() + 1; j++) {
                adjMatrix[i][j] = adjMatrix[i + 1][j]; // Shift rows up
            }
        }
        for (int i = 0; i < devices.size(); i++) {
            for (int j = index; j < devices.size(); j++) {
                adjMatrix[i][j] = adjMatrix[i][j + 1]; // Shift columns left
            }
        }

        // Adjust the size of the adjacency matrix
        boolean[][] newMatrix = new boolean[devices.size()][devices.size()];
        for (int i = 0; i < devices.size(); i++) {
            System.arraycopy(adjMatrix[i], 0, newMatrix[i], 0, devices.size());
        }
        adjMatrix = newMatrix;

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
        int deviceIndex = deviceIndexMap.get(deviceId);
        return devices.get(deviceIndex);
    }

    public int getDeviceIndexById(String deviceId){
        return deviceIndexMap.get(deviceId);
    }

    // for testing

    public void printGraph() {
        System.out.print("   ");
        for (NetworkDevice device : devices) {
            System.out.print(device.getDeviceId() + "   ");
        }
        System.out.println();

        for (int i = 0; i < devices.size(); i++) {
            System.out.print(devices.get(i).getDeviceId() + " ");
            for (int j = 0; j < devices.size(); j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
