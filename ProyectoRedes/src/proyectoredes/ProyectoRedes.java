/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes;

import GUI.Client;
import Server.Server;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jdom.JDOMException;

/**
 *
 * @author Romero
 */
public class ProyectoRedes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        try {
            /*
            try {
            Server servidor = new Server(5025);
            servidor.setVisible(true);
            servidor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            servidor.setLocationRelativeTo(null);
            servidor.setResizable(false);
            } catch (JDOMException ex) {
            Logger.getLogger(ProyectoRedes.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
            Client c = new Client();
            c.setVisible(true);
            c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            c.setLocationRelativeTo(null);
            c.setResizable(false);
        } catch (JDOMException ex) {
            Logger.getLogger(ProyectoRedes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
