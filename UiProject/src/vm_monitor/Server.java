package vm_monitor;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Manages a server that will listen for incoming connections and start a thread for every new connection
 */
public class Server {

    /**
     * Used to create a singleton
     */
    private static Server instance = null;
    /**
     * Server socket
     */
    private static ServerSocket listener;
    /**
     * Keep the state of the server (running / non_running)
     */
    private static boolean serverRunning;

    private Server(int port) throws IOException {
        listener = new ServerSocket(port);
        serverRunning = false;
    }

    /**
     * Singleton, Ensure only one instance is created
     *
     * @param port          Port to listen on for new connections
     * @return              A server instance
     * @throws java.io.IOException
     */
    public static Server createServer(int port) throws IOException {
        if (instance == null) {
            return new Server(port);
        }
        return instance;
    }

    /**
     * Start server if it is not already started
     */
    public void startServerAsync() {
        if (serverRunning == false) {
            this.startListening();
            serverRunning = true;
        }
    }

    /**
     * Stop server by closing the server socket
     */
    public void stopServer() {
        try {
            listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Non blocking wait for client connections, for every new client starts a new thread
     */
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

    /**
     * Local test for Server class
     * @param args  Not used
     * @throws      java.io.IOException  If server creation encounter errors
     */
    public static void main(String[] args) throws IOException {
        Server server = Server.createServer(8888);
        server.startServerAsync();
        /*
        try {
            Thread.sleep(10000);
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace();
        }
        server.stopServer();
        System.out.println(SystemDetailsReader.getMonitoredClient().size());

        for (MonitoredClient mc : SystemDetailsReader.getMonitoredClient()) {
            System.out.println(mc.toString());
        }
        */
    }

}
