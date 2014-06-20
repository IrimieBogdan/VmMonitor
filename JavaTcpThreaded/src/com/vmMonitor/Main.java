package com.vmMonitor;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(8888);

        try {
            while (true) {
                new SystemDetailsReader(listener.accept()).start();
            }
        }
        finally {
            listener.close();
        }
    }
}
