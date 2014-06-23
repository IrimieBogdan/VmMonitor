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

    public Label vmStatus;
    public TextField numberOfVms;

    @FXML
    private ComboBox<VmTypes> cbo_vmsType;

    @FXML
    void initialize() {
        // remove old values and add values from enum
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

    public void timeToStart() {
        List<MonitoredClient> clientDetails = SystemDetailsReader.getMonitoredClient();

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    int clientCounter = clientDetails.size();

                    // update ui thread
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            vmStatus.setText(clientCounter + " / " + numberOfVms.getText());
                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        };
        
        Thread th = new Thread(task);
        th.start();
    }
}
