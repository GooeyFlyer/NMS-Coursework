public class DeviceConfiguration {
    String connectionInterface;
    String MAC;
    String IPV4;
    String subnet;
    
    public DeviceConfiguration(String connectionInterface, String mAC, String iPV4, String subnet) {
        this.connectionInterface = connectionInterface;
        this.MAC = mAC;
        this.IPV4 = iPV4;
        this.subnet = subnet;
    }

    public String getConnectionInterface() {
        return connectionInterface;
    }

    public String getMAC() {
        return MAC;
    }

    public String getIPV4() {
        return IPV4;
    }

    public String getSubnet() {
        return subnet;
    }
}
