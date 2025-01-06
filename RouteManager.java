import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import factory.devices.NetworkDevice;
import logging.Listener;

/**
 * This class primarily does the calculation of
 * routes between devices. It uses an adjacency matrix to store the graph of network devices.
 * Uses a breadth-first search to find the optimal route between nodes in the graph.
 */
public class RouteManager {
    private NetworkDeviceManager deviceManager;
    private int[][] adjMatrix;
    private boolean adjMatrixChanged = false;
    private boolean[] visited;
    private PropertyChangeSupport support;

    /**
     * Constructor for the RouteManager class. Assignes a deviceManager, and a listener.
     * Additionally, creates the variable `visited` as a list of boolean values, used for the breadth-first search.
     * Creates the adjacency matrix, at the size of the paramater `vertexCount`, used to store the graph.
     * Creates a PropertyChangeSupport variable, used for the listener.
     * 
     * @param deviceManager The NetworkDeviceManager, also used by NMS, to store devices.
     * @param vertexCount The ammount of nodes in the graph.
     * @param listener The Listener, also used by NMS, to log events.
     */
    public RouteManager(NetworkDeviceManager deviceManager, int vertexCount, Listener listener) {
        this.deviceManager = deviceManager;
        visited = new boolean[vertexCount];
        adjMatrix = new int[vertexCount][vertexCount];

        support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(listener);
    }

    /**
     * The addDevice function adds a new network device to the adjacency matrix.
     * 
     * @param device The `device` parameter is an object of type `NetworkDevice` that represents a
     * device to be added to a network.
     */
    public void addDevice(NetworkDevice device) {
        // add new column and row to matrix
    }

    /**
     * The `addRoute` function adds a route between two network devices in a network, updating the
     * adjacency matrix.
     * 
     * @param source The `source` parameter represents the starting point or
     * the source network device from which the route originates. It is of type `NetworkDevice`.
     * @param destination The `destination` parameter represents the
     * destination network device to which a route is being added. It is of type `NetworkDevice`.
     * @param weight The `weight` parameter is used for the weight between the 2 devices, when making the route.
     */
    public void addRoute(NetworkDevice source, NetworkDevice destination, int weight) {
        int sourceIndex = deviceManager.getDeviceIndexByObject(source);
        int destinationIndex = deviceManager.getDeviceIndexByObject(destination);

        if (sourceIndex >= adjMatrix.length || destinationIndex >= adjMatrix.length) {
            System.out.println("Error: Vertex index out of bounds.");
            support.firePropertyChange("error", "", "Vertex index out of bounds.");
            return;
        }

        int oldValueSourceDestination = adjMatrix[sourceIndex][destinationIndex];

        adjMatrix[sourceIndex][destinationIndex] = weight;
        adjMatrix[destinationIndex][sourceIndex] = weight;

        adjMatrixChanged = true;
        support.firePropertyChange("Added route between " + source.getDeviceId() + " and " + destination.getDeviceId(), oldValueSourceDestination, adjMatrix[sourceIndex][destinationIndex]);
    }

    /**
     * The function `getOptimalRoute` retrieves the optimal route between two network devices using
     * breadth-first search and handles cases where no path is found.
     * 
     * @param source The `source` parameter is the starting point or origin device. It is of type `NetworkDevice`.
     * @param destination The `destination` parameter is the starting point or origin device. It is of type `NetworkDevice`.
     * @return Returns a List of NetworkDevice objects representing the
     * optimal route between the source and destination devices.
     */
    public List<NetworkDevice> getOptimalRoute(NetworkDevice source, NetworkDevice destination) {
        int sourceIndex = deviceManager.getDeviceIndexByObject(source);
        int destinationIndex = deviceManager.getDeviceIndexByObject(destination);

        List<NetworkDevice> path = new ArrayList<>();

        List<Integer> indexPath = bfs(sourceIndex, destinationIndex);

        if (indexPath.isEmpty()) {
            if (!adjMatrixChanged) {
                support.firePropertyChange("error", "", "adjMatrix empty. Check connections.txt or inputs");
            }
            else {
                support.firePropertyChange("warn", "", "No path found between devices " + source.getDeviceId() + " and " + destination.getDeviceId());
                System.out.println("No path found between devices " + source.getDeviceId() + " and " + destination.getDeviceId());
            }
        }
        else {
            for (Integer index : indexPath) {
                path.add(deviceManager.getDeviceByIndex(index));
            }
        }

        return path;
    }


    /**
     * The `bfs` function performs a breadth-first search from a source node to a destination node in a
     * graph represented by an adjacency matrix.
     * 
     * @param sourceIndex The `sourceIndex` parameter represents the index of the
     * starting node from which the search algorithm will begin traversing the graph.
     * @param destinationIndex The `destinationIndex` parameter represents the
     * index of the node in a graph that you want to reach starting from the `sourceIndex`.
     * @return The method `bfs` returns a List of integers representing the path from the source node
     * to the destination node. If a path is found, the method returns the list containing the nodes in
     * the path. If no path is found, an empty list is returned.
     */
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

    /**
     * The `printGraph` function prints a graph representation with device IDs and adjacency matrix.
     * ONLY USED FOR TESTING
     */
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
