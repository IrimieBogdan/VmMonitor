package vm_manager;

public class Client {
    public static void main(String[] args) {
        VmManager vmManager = new VmManager();
        vmManager.createAndStartVmsAsync(2, VmTypes.DSL);
    }
}
