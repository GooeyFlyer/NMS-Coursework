import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    static int deviceCount;
    static int weight = 1;
            
        public static List<Map<String, String>> readDevices(String filePath) {
            int lineCount = 0;
            List<Map<String, String>> listOfValues = new ArrayList<>();
    
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
    
                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    Map<String, String> values = new HashMap<>();
    
                    // Split the line by comma and trim any whitespace
                    String[] parts = line.split(",");
                    String deviceId = parts[0].trim();
                    String name = parts[1].trim();
                    
                    // Print or process the values
                    // System.out.println("\ndeviceId=" + deviceId);
                    // System.out.println("name=" + name);
                    values.put("deviceId", deviceId);
                    values.put("name", name);
    
                    if (parts.length == 3) {
                        String config = parts[2].trim();
                        String trimmedConfig = config.replace("Config:{", "").replace("}", "");
    
                        // Split by semicolons to get each key-value pair
                        String[] pairs = trimmedConfig.split(";");
    
                        // Extract just the values
                        for (String pair : pairs) {
                            // System.out.println(pair);
                            String[] keyValue = pair.split("=");
                            if (keyValue.length == 2) {
                                values.put(keyValue[0].trim(), keyValue[1].trim()); // Get the value part and trim whitespace
                            }
                        }
                    }
    
                    listOfValues.add(values);
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                e.printStackTrace();
            }
    
            deviceCount = lineCount;
            return listOfValues;
        }
    
        public static List<List<String>> readConnections(String filePath) {
            List<List<String>> listOfLists = new ArrayList<>();
    
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Split the line by comma and trim whitespace
                    String[] parts = line.split(",");
                    if (parts.length == 2) { // Ensure there are two parts
                        List<String> innerList = new ArrayList<>();
                        innerList.add(parts[0].trim()); // First value
                        innerList.add(parts[1].trim()); // Second value
                        listOfLists.add(innerList); // Add the inner list to the outer list
                    } else {
                        System.out.println("Error: Line format is incorrect - " + line);
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                e.printStackTrace();
            }
    
            return listOfLists; // Return the list of lists
        }
        
        public static void main(String[] args){
    
            //make sure you use args instead of inputs
    
            List<Map<String, String>> listOfValues = readDevices("devices.txt");
            List<List<String>> listOfConnections = readConnections("connections.txt");
    
            // System.out.println("\n" + listOfConnections.toString());
            
            NetworkDeviceManager deviceManager = new NetworkDeviceManager(deviceCount);
            RouteManager routeManager = new RouteManager(deviceManager, deviceCount);
    
            for (Map<String,String> values : listOfValues) {
                NetworkDevice device = new NetworkDevice(values.get("deviceId"), values.get("name"));
                deviceManager.addDevice(device);
            }
    
            for (List<String> connection : listOfConnections) {
                NetworkDevice device1 = deviceManager.getDeviceById(connection.get(0));
                NetworkDevice device2 = deviceManager.getDeviceById(connection.get(1));
    
                routeManager.addRoute(device1, device2, weight);
        }

        routeManager.printGraph();
    }

}
