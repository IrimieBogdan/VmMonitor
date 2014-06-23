package com.cloud_burst.ui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
     * Combo box with VM types.
     */
    @FXML
    private ComboBox<VmTypes> cbo_vmsType;

    /**
     * Add types of VM to combo box.
     * Remove old values and add values from enum.
     */
    @FXML
    void initialize() {
        cbo_vmsType.getItems().clear();
        cbo_vmsType.getItems().addAll(VmTypes.values());
    }

    private VmManager vmManager = new VmManager();

    /**
     * Start server that listens for connections and give command to create VMs
     *
     * @param actionEvent
     */
    public void startVms(ActionEvent actionEvent) {

        Server server = null;
        try {
            server = Server.createServer(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.startServerAsync();
        timeToStart();
        vmManager.createAndStartVmsAsync(2, VmTypes.DSL);
    }

    /**
     * Time the time needed for a VM to be created and started.
     */
    public void timeToStart() {
        List<MonitoredClient> clientDetails = SystemDetailsReader.getMonitoredClient();

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int oldClientCounter = 0;
                long timer = 0;
                while (true) {
                    int clientCounter = clientDetails.size();

                    // set elapsed time from start command to finish
                    for (int clientIndex = oldClientCounter; clientIndex < clientCounter; clientIndex++) {
                        clientDetails.get(clientIndex).setSecondsToStart(timer);
                    }

                    updateCounter(clientCounter);

                    try {
                        Thread.sleep(1000);
                        timer++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        Thread th = new Thread(task);
        th.start();
    }

    /**
     * Update Ui thread with number of clients started.
     *
     * @param clientsStarted    Number of started clients.
     */
    public void updateCounter(int clientsStarted) {
        Platform.runLater(() -> vmStatus.setText(clientsStarted + " / " + numberOfVms.getText()));
    }
}
