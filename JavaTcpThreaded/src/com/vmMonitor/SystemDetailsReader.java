package com.vmMonitor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;

public class SystemDetailsReader extends Thread {
    private static LinkedList<MonitoredSystemDetails> messages = new LinkedList<MonitoredSystemDetails>();
    private Socket socket;

    public SystemDetailsReader(Socket socket) {
        System.out.print("New connection established with: "  + socket.getInetAddress());
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String input = null;
            StringBuilder msg = new StringBuilder();
            while ((input = in.readLine()) != null) {
                msg.append(input);
            }

            String systemDetailsJson = msg.toString();
            MonitoredSystemDetails msd =  extractSystemDetails(systemDetailsJson);
            messages.add(msd);
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
            // connection is closed
        }
        System.out.println(messages.size() + " " + messages.get(0).getProcessors());
    }

    public MonitoredSystemDetails extractSystemDetails(String jsonWithSystemSettings) {
        ObjectMapper mapper = new ObjectMapper();
        MonitoredSystemDetails systemDetails;
        try {
            systemDetails = mapper.readValue(jsonWithSystemSettings, MonitoredSystemDetails.class);
            return systemDetails;
        } catch (IOException e) {
            System.err.println("Data received from client is incorrect");
        }

        return null;
    }

}
