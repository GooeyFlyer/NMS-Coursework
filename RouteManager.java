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
    private boolean[][] adjMatrix;

    public RouteManager(NetworkDeviceManager deviceManager, int vertexCount) {
        this.deviceManager = deviceManager;
        adjMatrix = new boolean[vertexCount][vertexCount];
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
        adjMatrix[sourceIndex][destinationIndex] = true;
        adjMatrix[destinationIndex][sourceIndex] = true;
    }

    public List<NetworkDevice> getOptimalRoute(NetworkDevice source, NetworkDevice destination) {
        List<NetworkDevice> devices = deviceManager.getDevices();

        int sourceIndex = devices.indexOf(source);
        int destinationIndex = devices.indexOf(destination);

        

    }


    // for testing

    public void printGraph() {
        List<NetworkDevice> devices = deviceManager.getDevices();
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
