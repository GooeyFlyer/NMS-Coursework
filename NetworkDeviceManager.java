import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import factory.devices.NetworkDevice;
import logging.Listener;

import java.beans.PropertyChangeSupport;

/**
 * The NetworkDeviceManager class manages all the device objects.
 * It stores the objects in an array of NetworkDevices.
 * A hash map is used, which stores the index of the object in the list, and its deviceId.
 * This allows for O(1) searches of the device list.
 */
public class NetworkDeviceManager {
    private List<NetworkDevice> devices;
    private Map<String, Integer> deviceIndexMap;
    private PropertyChangeSupport support;

    /**
     * The constructor for the NetworkDeviceManager class. Creates the list, hash map, and support.
     * 
     * @param vertexCount The number of devices. Determines the size of the list.
     * @param listener The Listener object, used for logging.
     */
    public NetworkDeviceManager(int vertexCount, Listener listener) {
        devices = new ArrayList<>(vertexCount);
        deviceIndexMap = new HashMap<>(vertexCount);

        support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(listener);
    }

    /**
     * The addDevice method adds a NetworkDevice to the list and hash map.
     * 
     * @param device The NetworkDevice object to be added.
     */
    public void addDevice(NetworkDevice device) {

        // Checks if the device is an Error device (see NetworkDeviceFactory)
        if (!device.getDeviceId().equals("error")) {

            // Adds the device
            devices.add(device);
            deviceIndexMap.put(device.getDeviceId(), devices.size() -1);

            // Logs the change
            support.firePropertyChange("Added Device", null, device.getDeviceId());
        }

        // Error checking
        else {
            String message =  "Cannot create device of type " + device.getName() + ", check devices.txt";

            if (device.getName().equals("")) {
                message = message + " [blank space detected - check devices.txt]";
            }
            
            support.firePropertyChange("error", "", message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * The removeDevice function removes the device from the device list.
     * It then recreates the hash map, with the updated values.
     * 
     * @param deviceId The deviceId of the device to be removed.
     */
    public void removeDevice(String deviceId) {
        Integer index = getDeviceIndexById(deviceId);

        // Remove the device from the list and map
        devices.remove((int) index);
        deviceIndexMap.remove(deviceId);

        // change map
        deviceIndexMap.clear();
        for (int i = 0; i < devices.size(); i++) {
            deviceIndexMap.put(devices.get(i).getDeviceId(), i);
        }
        support.firePropertyChange("Removed Device", deviceId, null);
    }

    public List<NetworkDevice> getDevices() {
        return devices;
    }

    /**
     * The getDeviceIndexMap returns the hash map of all the devices
     * 
     * @return The hash map of all the devices
     */
    public Map<String, Integer> getDeviceIndexMap() {
        return deviceIndexMap;
    }

    /**
     * The getDevicesAsString method returns the list in a readable format.
     * Only gives the deviceId, to improve the user interface.
     * 
     * @return A String of all the deviceIds, taken from the list
     */
    public String getDevicesAsString() {
        String output = "";
        for (NetworkDevice device : devices) {
            output = output + device.getDeviceId() + " ";
        }
        return output;
    }

    /** 
     * The getDeviceByIndex returns the device from that index of the list.
     */
    public NetworkDevice getDeviceByIndex(int index) {
        return devices.get(index);
    }

    /** 
     * The getDeviceById returns the device of that deviceId.
     */
    public NetworkDevice getDeviceById(String deviceId) {
        return devices.get(getDeviceIndexById(deviceId));
    }

    /**
     * The getDeviceIndexById method looks up the value of the deviceId from the hash map.
     * This value is the index of the device in the list of devices.
     * 
     * @param deviceId The deviceId of the device wanted.
     * @return The index of the device in the list
     */
    public int getDeviceIndexById(String deviceId) {

        // Gets the index value.
        Integer index = deviceIndexMap.get(deviceId);

        if (index != null) {
            return index;

        // Error checking if the device index cannot be found.
        } else {
            if (devices.size() == 0) {
                String message = "devices list empty. Check devices.txt or inputs";
                support.firePropertyChange("error", "", message);
                throw new IllegalArgumentException(message);
            }
            else {
                String message = "Device " + deviceId + " not found";

                if (deviceId.equals("")) {
                    message = message + " [blank space detected]";
                }

                support.firePropertyChange("error", "", message);
                throw new IllegalArgumentException(message);
            }

        }
    }

    /**
     * The getDeviceIndexByObject method simply gets the deviceId of the object and calls the getDeviceIndexById method.
     * 
     * @param networkDevice The object to get the index of.
     * @return The index of the device.
     */
    public int getDeviceIndexByObject(NetworkDevice networkDevice){
        return getDeviceIndexById(networkDevice.getDeviceId());
    }
}
