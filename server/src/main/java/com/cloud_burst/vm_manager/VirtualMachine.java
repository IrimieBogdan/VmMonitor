package com.cloud_burst.vm_manager;

import java.io.File;

/**
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
        String scriptPath = geFilePathFromResource("startVm.sh");

        return ExecuteCommand.exec("sh " + scriptPath + " " + name);
    }

    /**
     * Send command to VBoxManage to delete the VM.
     *
     * @return  VBoxManage output.
     */
    public String deleteVm() {
        String scriptPath = geFilePathFromResource("deleteVm.sh");

        return ExecuteCommand.exec("sh " + scriptPath + " " + name);
    }

    /**
     * Send command to VBoxManage to force stop the VM.
     *
     * @return  VBoxManage output.
     */
    public String stopVm() {
        String scriptPath = geFilePathFromResource("stopVm.sh");

        return ExecuteCommand.exec("sh " + scriptPath + " " + name);
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
        String scriptPath = geFilePathFromResource("cloneVm.sh");

        return ExecuteCommand.exec("sh " + scriptPath + " " + typeOfVm.toString() + " " + name);
    }

    /**
     * Build path for files in resources directory.
     *
     * @param fileName the name of the file for which we want the path from resources.
     * @return path from resources directory
     */
    private String geFilePathFromResource(String fileName) {
        File file = new File(
                getClass().getClassLoader().getResource(fileName).getFile()
        );
        return  file.getAbsolutePath();
    }
}
