import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
    private int[][] adjMatrix;

    public RouteManager(NetworkDeviceManager deviceManager, int vertexCount) {
        this.deviceManager = deviceManager;
        adjMatrix = new int[vertexCount][vertexCount];
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
        adjMatrix[sourceIndex][destinationIndex] = 1;
        adjMatrix[destinationIndex][sourceIndex] = 1;
    }

    public List<NetworkDevice> getOptimalRoute(NetworkDevice source, NetworkDevice destination) {
        List<NetworkDevice> devices = deviceManager.getDevices();

        int sourceIndex = devices.indexOf(source);
        int destinationIndex = devices.indexOf(destination);

        int n = adjMatrix.length;  // Number of nodes in the graph
        List<NetworkDevice> visitedNodes = new ArrayList<>();
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        // Start with the starting node
        visited[sourceIndex] = true;
        queue.offer(sourceIndex);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            visitedNodes.add(devices.get(currentNode));

            // Stop search when the target node is found
            if (currentNode == destinationIndex) {
                break;
            }

            // Traverse all adjacent nodes
            for (int neighbor = 0; neighbor < n; neighbor++) {
                // Check if there's an edge and if the node has not been visited
                if (adjMatrix[currentNode][neighbor] == 1 && !visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
        return visitedNodes;

    }


    // for testing

    public void printGraph() {
        List<NetworkDevice> devices = deviceManager.getDevices();
        System.out.print("   ");
        for (NetworkDevice device : devices) {
            System.out.print(device.getDeviceId() + " ");
        }
        System.out.println();

        for (int i = 0; i < devices.size(); i++) {
            System.out.print(devices.get(i).getDeviceId() + " ");
            for (int j = 0; j < devices.size(); j++) {
                System.out.print(adjMatrix[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
