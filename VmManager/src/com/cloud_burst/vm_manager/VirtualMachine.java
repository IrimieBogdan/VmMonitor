package com.cloud_burst.vm_manager;

/**
 * Created by constantin on 08.06.2014.
 */
public class VirtualMachine {
    private String name;

    public VirtualMachine(String name, VmTypes typeOfVm) {
        this.name = name;
        System.out.println(crateVmFromTemplate(typeOfVm));
    }

    public String startVm() {
        return ExecuteCommand.exec("sh startVm.sh " + name);
    }

    public String deleteVm() {
        return ExecuteCommand.exec("sh deleteVm.sh " + name);
    }

    public String stopVm() {
        return ExecuteCommand.exec("sh stopVm.sh " + name);
    }

    public String getName() {
        return name;
    }

    private String crateVmFromTemplate(VmTypes typeOfVm) {
        String command = "sh cloneVm.sh " + typeOfVm.toString() + " " + name;
        System.out.println(command);
        return ExecuteCommand.exec(command);
    }

}
