import java.beans.PropertyChangeSupport;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logging.Listener;


/**
 * The `ReadFiles` class in Java reads data from files, handles line format errors, and provides
 * methods to retrieve device information and connection details.
 */
public class ReadFiles {
    private static PropertyChangeSupport support;
    private static int deviceCount;

    /**
     * Constructor for the ReadFiles class. Assignes a listener.
     * 
     * @param listener The Listener, also used by NMS, to log events. 
     */
    public ReadFiles(Listener listener) {
        support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(listener);
    }

    /**
     * The method `lineFormatErrorCall` logs and throws an error if the line format for files is incorrect.
     * 
     * @param filePath The path of the file. Of type String.
     * @param lineCount The line number in the file that has the issue. Of type int
     * @param line The actual line in the file that has the issue. Of type String
     */
    public void lineFormatErrorCall(String filePath, int lineCount, String line) {
        String message = "Error: "+ filePath +" Line format is incorrect. Line "+lineCount+": " + line;
        support.firePropertyChange("error", "", message);
        throw new IllegalArgumentException(message);
    }
    public void lineFormatWarningCall(String filePath, int lineCount, String line) {
        String message = filePath +" Line format is incorrect. Line "+lineCount+": " + line + "\nProceeding...";
        support.firePropertyChange("warning", "", message);
    }
    
    /**
     * The method `readDevicesAndCount` reads the devices.txt file, extracts the values, then returns a list of devices.
     * Also contains error checking and handling to ensure smooth functionality.
     * 
     * @param filePath The path of the devices.txt file. Of type String.
     * @return A list of devices, each device is a dictionary of values for the device.
     */
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

                if (!(parts.length == 2 || parts.length == 3)) {
                    lineFormatErrorCall(filePath, lineCount, line);
                }
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
                        //System.out.println(pair);
                        String[] keyValue = pair.split("=");
                        //System.out.println(keyValue.length);
                        if (keyValue.length == 2) {
                            values.put(keyValue[0].trim(), keyValue[1].trim()); // Get the value part and trim whitespace
                        }
                        else if(pair.contains("IPV4:")) {
                            System.out.println(pair);
                            keyValue = pair.split(":");
                            values.put(keyValue[0].trim(), keyValue[1].trim()); // Get the value part and trim whitespace
                        }
                        else {
                            lineFormatWarningCall(filePath, lineCount, line);
                        }
                    }
                }
                listOfValues.add(values);
            }
        } catch (IOException e) {
            String message = "An error occurred accessing the file: " + filePath;
            support.firePropertyChange("error", "", message);
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e) {
            String message = "An error occurred with the contents of the file: " + filePath;
            support.firePropertyChange("error", "", message);
            e.printStackTrace();
            throw new ArrayIndexOutOfBoundsException(message);
        }

        deviceCount = lineCount;
        return listOfValues;
    }

    /**
     * The method `readConnections` reads the connections.txt file, extracts the values, then returns a list of connections.
     * Also contains error checking and handling to ensure smooth functionality.
     * 
     * @param filePath The path of the connections.txt file. Of type String.
     * @return A list of connections between devices.
     */
    public List<List<String>> readConnections(String filePath) {
        List<List<String>> listOfLists = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                // Split the line by comma and trim whitespace
                String[] parts = line.split(",");
                if (parts.length == 2) { // Ensure there are two parts
                    List<String> innerList = new ArrayList<>();
                    innerList.add(parts[0].trim()); // First value
                    innerList.add(parts[1].trim()); // Second value
                    listOfLists.add(innerList); // Add the inner list to the outer list
                } else {
                    lineFormatErrorCall(filePath, lineCount, line);
                }
            }
        } catch (IOException e) {
            support.firePropertyChange("error", "", "An error occurred while reading the file: " + filePath);
            e.printStackTrace();
        }

        System.out.println(listOfLists);
        return listOfLists; // Return the list of lists
    }

    /**
     * The function `getDeviceCount` returns the number of devices.
     * 
     * @return Returns the value of the variable `deviceCount`.
     */
    public int getDeviceCount() {
        return deviceCount;
    }
}
