import java.beans.PropertyChangeEvent;
import java.beans.beancontext.BeanContextSupport;
import java.util.List;
import java.util.Map;

import factory.FactoryControl;
import factory.devices.NetworkDevice;
import logging.Listener;

/**
 * This is the primary class of the system.
 * It will be used to launch the system and perform
 * the necessary actions, like adding devices,
 * removing devices, getting optimal route between
 * devices, filtering and searching for devices,
 * creating alerts etc. 
 * NOTE: DO NOT MOVE THIS CLASS TO ANY PACKAGE.
 *
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

    // Calculations for weight to be changed later, for now, just returns 1
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
    
    public static void main(String[] args){
        
        Listener listener = new Listener();

        String devicesFilePath;
        String connectionsFilePath;

        String sourceId;
        String destinationId;
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
        
        ReadFiles readFiles = new ReadFiles(listener);

        List<Map<String, String>> listOfValues = readFiles.readDevicesAndCount(devicesFilePath);
        List<List<String>> listOfConnections = readFiles.readConnections(connectionsFilePath);
        int deviceCount = readFiles.getDeviceCount();

        NetworkDeviceManager deviceManager = new NetworkDeviceManager(deviceCount, listener);
        RouteManager routeManager = new RouteManager(deviceManager, deviceCount, listener);

        

        // making devices
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

        // making connections
        for (List<String> connection : listOfConnections) {
            NetworkDevice device1 = deviceManager.getDeviceById(connection.get(0));
            NetworkDevice device2 = deviceManager.getDeviceById(connection.get(1));

            routeManager.addRoute(device1, device2, calculateWeight(device1.getName(), device2.getName()));
        }

        // routeManager.printGraph();

        List<NetworkDevice> path = routeManager.getOptimalRoute(deviceManager.getDeviceById(sourceId), deviceManager.getDeviceById(destinationId));
        if(!path.isEmpty()) {
            System.out.println(sourceId + " to " + destinationId + ": ");
            printRoute(path);
            System.out.println();
        }

        // Error testing
        //System.out.println(deviceManager.getDeviceById("PC").getDeviceId());

        System.out.println("\n PC1 test:");
        System.out.println(deviceManager.getDeviceById("PC1").toString());

        System.out.println("\n PC6 test:");
        System.out.println(deviceManager.getDeviceById("PC6").toString());

        // ConsoleLogging consoleLogging = new ConsoleLogging(new BaseLogging());
        // consoleLogging.log(level, "console 2");
    }

}
