package factory.devices;

/**
 * The abstract class NetworkDevice. Extended by all device types.
 * Stores the information of an device.
 */
public abstract class NetworkDevice {
    private String deviceId;
    private String name;
    private String connectionInterface;
    private String MAC;
    private String IPV4;
    private String subnet;

    /**
     * Constructor for the NetworkDevice class. Assigns the name and deviceId
     * 
     * @param deviceId The String representing the deviceId
     * @param name The String representing the name
     */
    public NetworkDevice(String deviceId, String name) {
        this.deviceId = deviceId;
        this.name = name;
    }

    /**
     * The getDeviceId function returns the deviceId value.
     * 
     * @return The `deviceId` variable being returned.
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * The getName function returns the name value.
     * 
     * @return The `name` variable being returned.
     */
    public String getName() {
        return name;
    }

    /**
     * The getConnectionInterface function returns the connectionInterface value.
     * 
     * @return The `connectionInterface` variable being returned.
     */
    public String getConnectionInterface() {
        return connectionInterface;
    }

    /**
     * The getMAC function returns the MAC value.
     * 
     * @return The `MAC` variable being returned.
     */
    public String getMAC() {
        return MAC;
    }

    /**
     * The getIPV4 function returns the IPV4 value.
     * 
     * @return The `IPV4` variable being returned.
     */
    public String getIPV4() {
        return IPV4;
    }

    /**
     * The getSubnet function returns the subnet value.
     * 
     * @return The `subnet` variable being returned.
     */
    public String getSubnet() {
        return subnet;
    }

    /**
     * The setConnectionInterface method assigns a value to the connectionInterface variable
     * @param connectionInterface The String representing the connection interface.
     */
    public void setConnectionInterface(String connectionInterface) {
        this.connectionInterface = connectionInterface;
    }

    /**
     * The setMAC method assigns a value to the MAC variable
     * @param MAC The String representing the MAC address.
     */
    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    /**
     * The setIPV4 method assigns a value to the IPV4 variable
     * @param IPV4 The String representing the IPV4 address.
     */
    public void setIPV4(String IPV4) {
        this.IPV4 = IPV4;
    }

    /**
     * The setSubnet method assigns a value to the subnet variable
     * @param subnet The String representing the subnet.
     */
    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    /**
     * The toString method presents the device information in a readable format
     */
    @Override
    public String toString() {
        return "deviceId: " + deviceId + " name: " + name +  " connectionInterface: " + connectionInterface + " MAC: " + MAC + " IPV4: " + IPV4 + " subnet: " + subnet;
    }
    
}
