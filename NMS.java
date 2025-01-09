import java.beans.PropertyChangeEvent;
import java.beans.beancontext.BeanContextSupport;
import java.util.List;
import java.util.Map;

import factory.FactoryControl;
import factory.devices.NetworkDevice;
import logging.Listener;

/**
 * This is the primary class of the application. Used to create various managers,
 * to control the system. Includes device managment, routing operations, and logging.
 */
public class NMS{

    /**
     * The function `printRoute` prints the device IDs in a list of network devices, with "to" between
     * each device except for the last one.
     * 
     * @param route A list of NetworkDevice objects representing the route to be printed.
     */
    public static void printRoute(List<NetworkDevice> route) {
        for (NetworkDevice device : route) {
            System.out.print(device.getDeviceId());
            if (!(device == route.getLast())){
                System.out.print(" to ");
            }
        }
    }

    /**
     * The function testing is used to print values to the command line, to test certain featues work.
     * Testing was used using the connection.txt and devices.txt file provided in the coursework start code
     * 
     * @param deviceManager The NetworkDeviceManager used by the NMS
     * @param routeManager The RouteManager used by the NMS
     */
    public static void testing(NetworkDeviceManager deviceManager, RouteManager routeManager) {
        // Error testing
        System.out.println(deviceManager.getDeviceById("PC").getDeviceId());

        System.out.println("\n PC1 test:");
        System.out.println(deviceManager.getDeviceById("PC1").toString());

        System.out.println("\n PC6 test:");
        System.out.println(deviceManager.getDeviceById("PC6").toString());

        routeManager.printGraph();

        routeManager.printGraph();
    }

    /**
     * The function `calculateWeight` takes two deviceIds and calculates the weight of their connection.
     * For now it returns only the value 1, but can be changed as future developments.
     * 
     * @param deviceId1 The id of the first device
     * @param deviceId2 The id of the second device
     * @return The weight of the connection between devices.
     */
    public static int calculateWeight(String deviceId1, String deviceId2) {
        return 1;
    }
    
    /**
     * The main method. This is the method run when the application starts.
     * 
     * @param args A list of Strings taken from the command line when the application is run.
     * Should be in the form: devices file path, connections file path, source device, destination device.
     */
    public static void main(String[] args){
        
        // Create logging listener
        Listener listener = new Listener();

        // Instantalise values
        String devicesFilePath;
        String connectionsFilePath;

        String sourceId;
        String destinationId;

        // Read args values
        try {
            devicesFilePath = args[0];
            connectionsFilePath = args[1];

            sourceId = args[2];
            destinationId = args[3];
        } catch (Exception e) {
            String message = "Inputs entered incorrectly";
            listener.propertyChange(new PropertyChangeEvent(new BeanContextSupport(), "error", "", message));
            throw new IllegalArgumentException(message);
        }
        
        // Reads all the data from files.
        ReadFiles readFiles = new ReadFiles(listener);
        List<Map<String, String>> listOfValues = readFiles.readDevicesAndCount(devicesFilePath);
        List<List<String>> listOfConnections = readFiles.readConnections(connectionsFilePath);
        int deviceCount = readFiles.getDeviceCount();

        // Creates managers
        NetworkDeviceManager deviceManager = new NetworkDeviceManager(deviceCount, listener);
        RouteManager routeManager = new RouteManager(deviceManager, deviceCount, listener);

        // creating devices
        FactoryControl factoryControl = new FactoryControl();
        for (Map<String,String> values : listOfValues) {
            if (values.containsKey("Subnet")) {
                factoryControl.setFactory(true);
            }
            else {
                factoryControl.setFactory(false);
            }
            deviceManager.addDevice(factoryControl.getDevice(values));
        }

        // adding connections
        for (List<String> connection : listOfConnections) {
            NetworkDevice device1 = deviceManager.getDeviceById(connection.get(0));
            NetworkDevice device2 = deviceManager.getDeviceById(connection.get(1));

            routeManager.addRoute(device1, device2, calculateWeight(device1.getName(), device2.getName()));
        }

        // Find optimal route between the source and destination device, then prints to the console.
        List<NetworkDevice> path = routeManager.getOptimalRoute(deviceManager.getDeviceById(sourceId), deviceManager.getDeviceById(destinationId));
        if(!path.isEmpty()) {
            System.out.println(sourceId + " to " + destinationId + ": ");
            printRoute(path);
            System.out.println();
        }

        // Error testing
        // testing();
    }

}
