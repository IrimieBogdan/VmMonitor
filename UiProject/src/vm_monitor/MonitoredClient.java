package vm_monitor;


public class MonitoredClient {

    /**
     * Keep details related to the internal state of the VM
     */
    private MonitoredSystemDetails monitoredSystemDetails;
    /**
     * Keep ip from where the VM called
     */
    private String ip;

    /**
     * Keep time in seconds from the moment the start Vm command was initiated
     */

    private long secondsToStart;

    public MonitoredClient(MonitoredSystemDetails monitoredSystemDetails, String ip) {
        this.monitoredSystemDetails = monitoredSystemDetails;
        this.ip = ip;
        secondsToStart = -1;
    }

    public long getSecondsToStart() {
        return secondsToStart;
    }

    public void setSecondsToStart(long secondsToStart) {
        this.secondsToStart = secondsToStart;
    }

    public MonitoredSystemDetails getMonitoredSystemDetails() {
        return monitoredSystemDetails;
    }

    public void setMonitoredSystemDetails(MonitoredSystemDetails monitoredSystemDetails) {
        this.monitoredSystemDetails = monitoredSystemDetails;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "ip: " + ip +  ", processors : " + monitoredSystemDetails.getProcessors() + ", memory: " + monitoredSystemDetails.getMemory();
    }

}
