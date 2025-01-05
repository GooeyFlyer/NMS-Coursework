import java.beans.PropertyChangeSupport;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logging.Listener;

public class ReadFiles {
    private static PropertyChangeSupport support;
    private static int deviceCount;

    public ReadFiles(Listener listener) {
        support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(listener);
    }
    
    public List<Map<String, String>> readDevicesAndCount(String filePath) {
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
            support.firePropertyChange("error", "", "An error occurred while reading the file: " + filePath);
            e.printStackTrace();
        }

        deviceCount = lineCount;
        return listOfValues;
    }

    public List<List<String>> readConnections(String filePath) {
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
            support.firePropertyChange("error", "", "An error occurred while reading the file: " + filePath);
            e.printStackTrace();
        }

        return listOfLists; // Return the list of lists
    }

    public int getDeviceCount() {
        return deviceCount;
    }
}
