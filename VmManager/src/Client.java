/**
 * Created by constantin on 09.06.2014.
 */
public class Client {
    public static void main(String[] args) {
        VmManager vmManager = new VmManager();
        vmManager.createAndStartVms(2, VmTypes.UbuntuMonitored);
    }
}
