import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import factory.devices.NetworkDevice;
import logging.Listener;

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
    private NetworkDeviceManager deviceManager;
    private int[][] adjMatrix;
    private boolean[] visited;
    private PropertyChangeSupport support;

    public RouteManager(NetworkDeviceManager deviceManager, int vertexCount, Listener listener) {
        this.deviceManager = deviceManager;
        this.visited = new boolean[vertexCount];
        adjMatrix = new int[vertexCount][vertexCount];

        support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(listener);
    }

    public void addDevice(NetworkDevice device) {
        // add new column and row to matrix
    }

    public void addRoute(NetworkDevice source, NetworkDevice destination, int weight) {
        int sourceIndex = deviceManager.getDeviceIndexByObject(source);
        int destinationIndex = deviceManager.getDeviceIndexByObject(destination);

        if (sourceIndex >= adjMatrix.length || destinationIndex >= adjMatrix.length) {
            System.out.println("Error: Vertex index out of bounds.");
            support.firePropertyChange("error", "", "Vertex index out of bounds.");
            return;
        }

        int oldValueSourceDestination = adjMatrix[sourceIndex][destinationIndex];

        if ((adjMatrix[sourceIndex][destinationIndex] != 1) || (adjMatrix[destinationIndex][sourceIndex] != 1)){
            adjMatrix[sourceIndex][destinationIndex] = 1;
            adjMatrix[destinationIndex][sourceIndex] = 1;
        }
        support.firePropertyChange("Added route between " + source.getDeviceId() + " and " + destination.getDeviceId(), oldValueSourceDestination, adjMatrix[sourceIndex][destinationIndex]);
    }

    public List<NetworkDevice> getOptimalRoute(NetworkDevice source, NetworkDevice destination) {
        int sourceIndex = deviceManager.getDeviceIndexByObject(source);
        int destinationIndex = deviceManager.getDeviceIndexByObject(destination);

        List<NetworkDevice> path = new ArrayList<>();

        List<Integer> indexPath = bfs(sourceIndex, destinationIndex);

        for (Integer index : indexPath) {
            path.add(deviceManager.getDeviceByIndex(index));
        }
        return path;
    }

    private List<Integer> bfs(int sourceIndex, int destinationIndex) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(sourceIndex);
        visited[sourceIndex] = true;
        List<Integer> stack = new ArrayList<>();

        while (!queue.isEmpty()) {
            int current = queue.poll();
            // System.out.println(deviceManager.getDeviceByIndex(current).getDeviceId());
            stack.add(current);

            if (current == destinationIndex) {
                return stack; // found path to target
            }

            // Explore all adjacent nodes
            boolean children = false;
            for (int i = 0; i < adjMatrix.length; i++) {
                if (adjMatrix[current][i] == 1 && !visited[i]) { // check for an edge and if the node is unvisited
                    queue.add(i);
                    visited[i] = true;
                    children = true;
                }
            }
            // if the current node has no valid children, remove it from the stack, as it wont be in the path
            if (!children) {
                stack.removeLast();
            }

        }

        return new ArrayList<>(); // no path found
    }

    // for testing
    public void printGraph() {
        List<NetworkDevice> devices = deviceManager.getDevices();
        System.out.print("   ");
        for (NetworkDevice device : devices) {
            System.out.print(device.getDeviceId() + " ");
        }
        System.out.println();

        for (int i = 0; i < adjMatrix.length; i++) {
            System.out.print(deviceManager.getDeviceByIndex(i).getDeviceId() + " ");
            for (int j = 0; j < adjMatrix.length; j++) {
                System.out.print(adjMatrix[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
