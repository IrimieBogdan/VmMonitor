package com.cloud_burst.vm_monitor;

/**
 * Created by constantin on 21.06.2014.
 */
public class MonitoredClient {

    private MonitoredSystemDetails msd;
    private String ip;
    private int secondsToStart;

    public MonitoredClient(MonitoredSystemDetails msd, String ip) {
        this.msd = msd;
        this.ip = ip;
        secondsToStart = -1;
    }

    public int getSecondsToStart() {
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
