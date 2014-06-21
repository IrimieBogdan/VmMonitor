package com.cloud_burst.vm_monitor;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private static Server instance = null;
    private static ServerSocket listener;
    private static boolean serverRunning;

    private Server(int port) throws IOException {
        listener = new ServerSocket(port);
        serverRunning = false;
    }

    public static Server createServer(int port) throws IOException {
        if (instance == null) {
            return new Server(port);
        }
        return instance;
    }

    public void startServer() {
        if (serverRunning == false) {
            this.startListening();
            serverRunning = true;
        }
    }


    public void stopServer() {
        try {
            listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startListening() {
        System.out.println("Server is waiting for connections...");
        Thread thread = new Thread() {
            @Override
            public void run(){
                try {
                    while (true) {
                        new SystemDetailsReader(listener.accept()).start();
                    }
                } catch (IOException e) {
                    System.err.println("ServerSocket was closed!");
                } finally {
                    serverRunning = false;
                    try {
                        listener.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public static void main(String[] args) throws IOException {
        Server server = Server.createServer(8888);
        server.startServer();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.stopServer();
        System.out.println(SystemDetailsReader.getMonitoredClient().size());

        for (MonitoredClient mc : SystemDetailsReader.getMonitoredClient()) {
            System.out.println(mc.toString());
        }
    }

}
