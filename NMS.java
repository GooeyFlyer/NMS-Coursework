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

    public static void printRoute(List<NetworkDevice> route) {
        for (NetworkDevice device : route) {
            System.out.print(device.getDeviceId());
            if (!(device == route.getLast())){
                System.out.print(" to ");
            }
        }
    }

    // Calculations for weight to be changed later, for now, just returns 1
    public static int calculateWeight(String name1, String name2) {
        return 1;
    }
    
    public static void main(String[] args){

        //make sure you use args instead of inputs

        String devicesFilePath = args[0];
        String connectionsFilePath = args[1];

        String sourceId = args[2];
        String destinationId = args[3];

        // System.out.println("\n" + listOfConnections.toString());
        
        Listener listener = new Listener();
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
        if(path.isEmpty()) {
            System.out.println("No path found between devices");
        }
        else {
            System.out.println(sourceId + " to " + destinationId + ": ");
            printRoute(path);
            System.out.println();
        }

        // Error testing
        //System.out.println(deviceManager.getDeviceById("PC").getDeviceId());

        System.out.println("\n PC1 MAC address test:");
        System.out.println(deviceManager.getDeviceById("PC1").getMAC());

        // ConsoleLogging consoleLogging = new ConsoleLogging(new BaseLogging());
        // consoleLogging.log(level, "console 2");
    }

}
