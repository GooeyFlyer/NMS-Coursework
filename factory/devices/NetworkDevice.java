package factory.devices;
public abstract class NetworkDevice {
    private String deviceId;
    private String name;
    private String connectionInterface;
    private String MAC;
    private String IPV4;
    private String subnet;

    public NetworkDevice(String deviceId, String name) {
        this.deviceId = deviceId;
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getName() {
        return name;
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

    public void setConnectionInterface(String connectionInterface) {
        this.connectionInterface = connectionInterface;
    }

    public void setMAC(String mAC) {
        MAC = mAC;
    }

    public void setIPV4(String iPV4) {
        IPV4 = iPV4;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    
}
