package com.cloud_burst.vm_monitor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;

public class SystemDetailsReader extends Thread {

    private static LinkedList<MonitoredClient> monitoredClient = new LinkedList<MonitoredClient>();
    private Socket socket;

    public SystemDetailsReader(Socket socket) {
        System.out.println("New connection established with: "  + socket.getInetAddress());
        this.socket = socket;
    }

    public static LinkedList<MonitoredClient> getMonitoredClient() {
        return monitoredClient;
    }

    public static void setMonitoredClient(LinkedList<MonitoredClient> monitoredClient) {
        SystemDetailsReader.monitoredClient = monitoredClient;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String input = null;
            StringBuilder msg = new StringBuilder();
            while ((input = in.readLine()) != null) {
                msg.append(input);
            }

            String systemDetailsJson = msg.toString();
            MonitoredSystemDetails msd =  stringToMonitoredSystemDetails(systemDetailsJson);
            String ip = socket.getInetAddress().toString();
            MonitoredClient mc = new MonitoredClient(msd, ip);
            monitoredClient.add(mc);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public MonitoredSystemDetails stringToMonitoredSystemDetails(String jsonWithSystemSettings) {
        ObjectMapper mapper = new ObjectMapper();
        MonitoredSystemDetails systemDetails;
        try {
            systemDetails = mapper.readValue(jsonWithSystemSettings, MonitoredSystemDetails.class);
            return systemDetails;
        } catch (IOException e) {
            System.err.println("Data received from client is incorrect, " +
                    "not in Json format or not containing correct attributes!");
        }
        return null;
    }

}
