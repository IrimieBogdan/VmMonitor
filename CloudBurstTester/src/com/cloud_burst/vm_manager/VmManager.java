package com.cloud_burst.vm_manager;

import java.lang.String;import java.lang.System;import java.util.LinkedList;
import java.util.Random;

/**
 * Manipulate VMs, offer support for manipulating  bulks of VMs.
 */
public class VmManager {
    /**
     * List of managed VMs
      */
    private LinkedList<VirtualMachine> vms;

    public VmManager() {
        vms = new LinkedList<VirtualMachine>();
    }

    /**
     * Create a number of VMs of a specified type.
     *
     * @param numberOfNewVms    Number of VMs to be created.
     * @param type              Type of VMs to be created.
     */
    public void createtVms(int numberOfNewVms, VmTypes type) {
        Random rand = new Random();

        for (int i = 0; i < numberOfNewVms; i++) {
            String vmName = type.toString() + rand.nextInt();
            vms.add(new VirtualMachine(vmName, type));
        }
    }

    /**
     * Start all VMs from the VmManager list.
     */
    public void startAllVms() {
        for (VirtualMachine vm : vms) {
            vm.startVm();
        }
    }

    /**
     * Stop all VMs from the VmManager list.
     */
    public void stopAllVms() {
        for (VirtualMachine vm : vms) {
            vm.stopVm();
        }
    }

    /**
     * Delete all VMs from the VmManager list.
     */
    public void deleteAllVms() {
        for (VirtualMachine vm : vms) {
            vm.deleteVm();
        }
    }

    /**
     * Create VMs and start every VM immediately after creation.
     *
     * @param numberOfNewVms    Number of Vms to be created.
     * @param type              Type of Vms to be created.
     */
    public void createAndStartVms(int numberOfNewVms, VmTypes type) {
        Random rand = new Random();

        for (int i = 0; i < numberOfNewVms; i++) {
            String vmName = type.toString() + rand.nextInt();
            System.out.print("New VM name is: " + vmName);
            VirtualMachine vm = new VirtualMachine(vmName, type);
            System.out.println(vm.startVm());
            vms.add(vm);
        }
    }

}
