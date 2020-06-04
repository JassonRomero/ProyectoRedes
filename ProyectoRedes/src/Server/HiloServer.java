/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;

/**
 *
 * @author Jasson
 */
public class HiloServer extends Thread {

    private Socket socket;

    public HiloServer(Socket socket) throws JDOMException, IOException {
        this.socket = socket;
    }

    public HiloServer() {
        this.socket = new Socket();
    }

    @Override
    public void run() {

        try {
            PrintStream send = new PrintStream(this.socket.getOutputStream());
            BufferedReader receive = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            do {
                if (receive.ready()) {
                    String respuesta = receive.readLine();
                    System.out.println(respuesta);
                    switch (respuesta) {
                        
                    }
                }
            } while (true);
        } catch (IOException ex) {
            Logger.getLogger(HiloServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
