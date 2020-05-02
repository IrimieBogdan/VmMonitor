package com.cloud_burst.ui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Model for VM details table
 */
public class ClientVmDetails {

    private final SimpleIntegerProperty vmNumber;
    private final SimpleLongProperty cpuCount;
    private final SimpleLongProperty memory;
    private final SimpleStringProperty ip;
    private final SimpleLongProperty time;

    private static int vmCounter = 0;

    public ClientVmDetails() {
        this(-1, -1, "127.0.0.1", -1);
    }

    public ClientVmDetails(long cpuCount, long memory, String ip, long time) {
        vmCounter++;
        this.vmNumber = new SimpleIntegerProperty(vmCounter);
        this.cpuCount = new SimpleLongProperty(cpuCount);
        this.memory = new SimpleLongProperty(memory);
        this.ip = new SimpleStringProperty(ip);
        this.time = new SimpleLongProperty(time);
    }

    public int getVmNumber() {
        return vmNumber.get();
    }

    public SimpleIntegerProperty vmNumberProperty() {
        return vmNumber;
    }

    public void setVmNumber(int vmNumber) {
        this.vmNumber.set(vmNumber);
    }

    public long getCpuCount() {
        return cpuCount.get();
    }

    public SimpleLongProperty cpuCountProperty() {
        return cpuCount;
    }

    public void setCpuCount(long cpuCount) {
        this.cpuCount.set(cpuCount);
    }

    public long getMemory() {
        return memory.get();
    }

    public SimpleLongProperty memoryProperty() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory.set(memory);
    }

    public String getIp() {
        return ip.get();
    }

    public SimpleStringProperty ipProperty() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public long getTime() {
        return time.get();
    }

    public SimpleLongProperty timeProperty() {
        return time;
    }

    public void setTime(long time) {
        this.time.set(time);
    }

}
