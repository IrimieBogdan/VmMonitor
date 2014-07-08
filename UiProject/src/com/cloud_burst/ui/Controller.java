package com.cloud_burst.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import vm_manager.VmManager;
import vm_manager.VmTypes;
import vm_monitor.MonitoredClient;
import vm_monitor.Server;
import vm_monitor.SystemDetailsReader;

import java.io.IOException;
import java.util.List;

public class Controller {

    /**
     * Shows how many VMs have started.
     */
    public Label vmStatus;

    /**
     * Number of VMs to start.
     */
    public TextField numberOfVms;

    /**
     * Elapsed time from start action
     */
    public Label elapsedTime;

    /**
     * Combo box with VM types.
     */
    @FXML
    private ComboBox<VmTypes> cbo_vmsType;

    /**
     * Stores deta related to started VMs.
     */
    @FXML
    public TableView table_vmDetails;

    private ObservableList<ClientVmDetails> startedVmDetails = FXCollections.observableArrayList();


    /**
     * Add types of VM to combo box.
     * Remove old values and add values from enum.
     */
    @FXML
    void initialize() {
        cbo_vmsType.getItems().clear();
        cbo_vmsType.getItems().addAll(VmTypes.values());

        table_vmDetails.setItems(startedVmDetails);
    }

    private VmManager vmManager = new VmManager();

    /**
     * Start server that listens for connections and give command to create VMs
     *
     * @param actionEvent
     */
    public void startVms(ActionEvent actionEvent) {

        int vmNumber = 0;
        VmTypes vmsType = null;
        Server server = null;

        try {
            server = Server.createServer(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.startServerAsync();

        try{
            vmNumber = Integer.parseInt(numberOfVms.getText());
        }
        catch (NumberFormatException e) {
            System.err.println("Value is not a number");
        }
        vmsType =  cbo_vmsType.getValue();

        if (vmsType != null && vmNumber != 0) {
            timeToStart(vmNumber);
            vmManager.createAndStartVmsAsync(vmNumber, vmsType);
        }
    }

    /**
     * Measure the time needed for a VM to be created and started with maximum of 1 second error.
     */
    public void timeToStart(int totalVmNumber) {
        List<MonitoredClient> clientDetails = SystemDetailsReader.getMonitoredClient();

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int oldStartedVms = 0;
                long timer = 0;
                int startedVms = 0;
                while (startedVms < totalVmNumber) {
                    startedVms = clientDetails.size();

                    // set elapsed time from start command to finish
                    for (int clientIndex = oldStartedVms; clientIndex < startedVms; clientIndex++) {
                        MonitoredClient mc = clientDetails.get(clientIndex);
                        mc.setSecondsToStart(timer);

                        long cpuCount = mc.getMonitoredSystemDetails().getProcessors();
                        long memory = mc.getMonitoredSystemDetails().getMemory();
                        String ip = mc.getIp();
                        long time = mc.getSecondsToStart();
                        startedVmDetails.add(new ClientVmDetails(cpuCount, memory, ip, time));
                    }
                    oldStartedVms = startedVms;

                    updateCounter(startedVms, totalVmNumber);
                    updateTime(timer);

                    try {
                        Thread.sleep(1000);
                        timer++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                return null;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    /**
     * Update Ui thread with number of started clients.
     *
     * @param clientsStarted    Number of started clients.
     */
    public void updateCounter(int clientsStarted, int totalClients) {
        Platform.runLater(() -> vmStatus.setText(clientsStarted + " / " + totalClients + " VMs"));
    }

    /**
     * Update UI thread with elapsed time.
     *
     * @param time Elapsed time
     */
    public void updateTime(long time) {
        Platform.runLater(() -> elapsedTime.setText(Long.toString(time) + "s"));
    }

    /**
     * Delete all vm.
     *
     * @param actionEvent
     */
    public void deleteAllVms(ActionEvent actionEvent) {
        vmManager.deleteAllVmsAsync();
        SystemDetailsReader.getMonitoredClient().clear();
        startedVmDetails.clear();

        updateCounter(0,0);
        updateTime(0);
    }
}
