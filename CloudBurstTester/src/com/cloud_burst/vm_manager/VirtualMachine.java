package com.cloud_burst.vm_manager;

import java.lang.String;import java.lang.System; /**
 * Manipulate VM, offer support for basic operations.
 */
public class VirtualMachine {
    /**
     * Name of the vm, it must be unique.
     */
    private String name;

    /**
     * Create a new Vm by cloning an existing vm.
     *
     * @param name      Name for the new vm, must be unique.
     * @param typeOfVm  Type of VM, from VmTypes enum.
     */
    public VirtualMachine(String name, VmTypes typeOfVm) {
        this.name = name;
        System.out.println(crateVmFromTemplate(typeOfVm));
    }

    /**
     * Send command to VBoxManage to start the vm.
     *
     * @return  VBoxManage output.
     */
    public String startVm() {
        return ExecuteCommand.exec("sh startVm.sh " + name);
    }

    /**
     * Send command to VBoxManage to delete the VM.
     *
     * @return  VBoxManage output.
     */
    public String deleteVm() {
        return ExecuteCommand.exec("sh deleteVm.sh " + name);
    }

    /**
     * Send command to VBoxManage to force stop the VM.
     *
     * @return  VBoxManage output.
     */
    public String stopVm() {
        return ExecuteCommand.exec("sh stopVm.sh " + name);
    }

    public String getName() {
        return name;
    }

    /**
     * Send command to VBoxManage to clone from an existing type of VM.
     *
     * @param typeOfVm  Type of VM, from VmTypes enum, to be cloned.
     * @return  VBoxManage output.
     */
    private String crateVmFromTemplate(VmTypes typeOfVm) {
        return ExecuteCommand.exec("sh cloneVm.sh " + typeOfVm.toString() + " " + name);
    }

}
