/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Sony
 */
public class Server extends Thread {

    private int port;
    ServerSocket serverSocket;
    ArrayList<KlijentskaNit> klijenti;

    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        this.klijenti = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                System.out.println("Cekam na klijenta...");
                Socket socket = serverSocket.accept();
                System.out.println("Novi klijent se povezao!");

                KlijentskaNit klijentskaNit = new KlijentskaNit(socket);
                klijenti.add(klijentskaNit);
                klijentskaNit.start();
            }
        } catch (Exception ex) {
            System.out.println("Server je zaustavljen.");
        }
    }

    public void stopServer() throws IOException {
        serverSocket.close();
        for (KlijentskaNit k : klijenti) {
            k.getSocket().close();
        }
    }
}
