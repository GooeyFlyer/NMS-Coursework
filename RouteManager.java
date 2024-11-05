import java.util.List;

/**
 * This class primarily does the calculation of
 * routes between devices. The actions will be based
 * on the devices added to a particular route.
 * The devices added here should be a subset of the ones
 * added to the NetworkDeviceManager. You shouldn't add
 * a device to the RouteManager if they aren't in the
 * NetworkDeviceManager. 
 */
public class RouteManager {
    NetworkDeviceManager deviceManager;

    public RouteManager(NetworkDeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    public void addDevice(NetworkDevice device) {
    }

    public void addRoute(NetworkDevice source, NetworkDevice destination, int weight) {
        List<NetworkDevice> devices = deviceManager.getDevices();

        int sourceIndex = devices.indexOf(source);
        int destinationIndex = devices.indexOf(destination);

        if (sourceIndex >= devices.size() || destinationIndex >= devices.size()) {
            System.out.println("Error: Vertex index out of bounds.");
            return;
        }
        deviceManager.addEdge(sourceIndex, destinationIndex);
    }

    public List<NetworkDevice> getOptimalRoute(NetworkDevice source, NetworkDevice destination) {
        
    }
}
