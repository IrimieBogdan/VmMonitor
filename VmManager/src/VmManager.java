import java.util.LinkedList;
import java.util.Random;

/**
 * Created by constantin on 08.06.2014.
 */
public class VmManager {
    private LinkedList<VirtualMachine> vms;

    public VmManager() {
        vms = new LinkedList<VirtualMachine>();
    }

    public void createtVms(int numberOfNewVms, VmTypes type) {
        Random rand = new Random();

        for (int i = 0; i < numberOfNewVms; i++) {
            String vmName = type.toString() + rand.nextInt();
            vms.add(new VirtualMachine(vmName, type));
        }
    }


    public void startAllVms() {
        for (VirtualMachine vm : vms) {
            vm.startVm();
        }
    }

    public void stopAllVms() {
        for (VirtualMachine vm : vms) {
            vm.stopVm();
        }
    }

    public void createAndStartVms(int numberOfNewVms, VmTypes type) {
        Random rand = new Random();

        for (int i = 0; i < numberOfNewVms; i++) {
            String vmName = type.toString() + rand.nextInt();
            System.out.print("New Vm name is: " + vmName);
            VirtualMachine vm = new VirtualMachine(vmName, type);
            System.out.println(vm.startVm());
            vms.add(vm);
        }
    }

}
