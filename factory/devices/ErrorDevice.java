package factory.devices;

/**
 * The ErrorDevice class. A special NetworkDevice types used to represent unkown devices.
 */
public class ErrorDevice extends NetworkDevice{

    /**
     * Constructor for the class. Calls the super constructor function
     */
    public ErrorDevice(String deviceId, String name) {
        super(deviceId, name);
    }
}
