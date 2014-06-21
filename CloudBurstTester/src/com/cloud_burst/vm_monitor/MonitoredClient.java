package com.cloud_burst.vm_monitor;


import java.lang.Override;import java.lang.String;public class MonitoredClient {

    /**
     * Keep details related to the internal state of the VM
     */
    private MonitoredSystemDetails msd;
    /**
     * Keep ip from where the VM called
     */
    private String ip;
    /**
     * Keep time in seconds from the moment the start Vm command was initiated
     */
    private long secondsToStart;

    public MonitoredClient(MonitoredSystemDetails msd, String ip) {
        this.msd = msd;
        this.ip = ip;
        secondsToStart = -1;
    }

    public long getSecondsToStart() {
        return secondsToStart;
    }

    public void setSecondsToStart(int secondsToStart) {
        this.secondsToStart = secondsToStart;
    }

    @Override
    public String toString() {
        return "ip: " + ip +  ", processors : " + msd.getProcessors() + ", memory: " + msd.getMemory();
    }

}
