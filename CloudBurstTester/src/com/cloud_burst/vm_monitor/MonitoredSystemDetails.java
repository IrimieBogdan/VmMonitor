package com.cloud_burst.vm_monitor;

/**
 * This class will store details received from the monitored VMs
 */
public class MonitoredSystemDetails {
    /**
     * Keep number of processors available on vm
     */
    private long processors;

    /**
     * Keep amaount of memory available on the vm
     */
    private long memory;

    public long getProcessors() {
        return processors;
    }

    public void setProcessors(long processors) {
        this.processors = processors;
    }

    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

}
