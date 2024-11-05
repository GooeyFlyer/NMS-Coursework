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
        
    public static List<Map<String, String>> readDevices() {
        String filePath = "devices.txt"; // Specify the path to your file here
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
    
    public static void main(String[] args){
        List<Map<String, String>> listOfValues = readFiles();

        // System.out.println("\n" + listOfValues.toString());
        
        NetworkDeviceManager deviceManager = new NetworkDeviceManager(deviceCount);

        for (Map<String,String> values : listOfValues) {
            NetworkDevice device = new NetworkDevice(values.get("deviceId"), values.get("name"));
            deviceManager.addDevice(device);
        }
    }

}
