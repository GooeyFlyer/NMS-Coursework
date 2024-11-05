/**
 * This class primarily does the calculation of
 * routes between devices. The actions will be based
 * on the devices added to a particular route.
 * The devices added here should be a subset of the ones
 * added to the NetworkDeviceManager. You shouldn't add
 * a device to the RouteManager if they aren't in the
 * NetworkDeviceManager. 
 */
public class RouteManager {

    public RouteManager() {
    }

    public void addDevice(NetworkDevice device) {
    }

    public void addRoute(NetworkDevice source, NetworkDevice destination, int weight) {
    }

    public List<NetworkDevice> getOptimalRoute(NetworkDevice source, NetworkDevice destination) {
    }
}
