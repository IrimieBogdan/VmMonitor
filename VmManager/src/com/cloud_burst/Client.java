package com.cloud_burst;

import com.cloud_burst.vm_manager.VmManager;
import com.cloud_burst.vm_manager.VmTypes;


public class Client {
    public static void main(String[] args) {
        VmManager vmManager = new VmManager();
        vmManager.createAndStartVms(2, VmTypes.DSL);
    }
}
