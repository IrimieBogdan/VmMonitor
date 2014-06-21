package com.cloud_burst.vm_manager;

import com.cloud_burst.vm_manager.VmManager;
import com.cloud_burst.vm_manager.VmTypes;import java.lang.String;


public class Client {
    public static void main(String[] args) {
        VmManager vmManager = new VmManager();
        vmManager.createAndStartVms(2, VmTypes.DSL);
    }
}
