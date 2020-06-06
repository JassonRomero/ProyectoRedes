/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.jdom.JDOMException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Romero
 */
public class Server extends JFrame implements Runnable, ActionListener {

    private int socketPortNumber;

    private JLabel labelIpServidor, labelCliente, labelContrasena;
    JTextField textCliente;
    JPasswordField textContrasena;
    JButton buttonRegistrar;
    private Thread hilo;

    public Server(int socketPortNumber) throws JDOMException, IOException {
        super("Server");
        this.setLayout(null);
        this.setSize(300, 300);
        this.socketPortNumber = socketPortNumber;
        init();
        this.hilo = new Thread(this);
        this.hilo.start();
    }

    public void init() throws JDOMException, IOException {
        this.labelContrasena = new JLabel("Nombre cliente");
        this.labelContrasena.setBounds(10, 10, 150, 30);
        this.add(labelContrasena);

        this.labelCliente = new JLabel("Contraseña cliente ");
        this.labelCliente.setBounds(10, 70, 150, 30);
        this.add(labelCliente);

        this.textContrasena = new JPasswordField();
        this.textContrasena.setBounds(150, 70, 100, 30);
        this.add(textContrasena);

        this.textCliente = new JTextField();
        this.textCliente.setBounds(150, 10, 100, 30);
        this.add(textCliente);

        this.buttonRegistrar = new JButton("Registrar Cliente");
        this.buttonRegistrar.setBounds(50, 120, 150, 30);
        this.buttonRegistrar.addActionListener(this);
        this.add(buttonRegistrar);

        this.labelIpServidor = new JLabel();
        this.labelIpServidor.setBounds(50, 150, 500, 100);
        this.add(this.labelIpServidor);
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.socketPortNumber);
            InetAddress address = InetAddress.getLocalHost();
            this.labelIpServidor.setText(String.valueOf(address));
            do {
                Socket socket = serverSocket.accept();
                HiloServer hiloServer = new HiloServer(socket);
                hiloServer.start();
            } while (true);

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.buttonRegistrar) {
            try {
                Conectar conect = new Conectar();
                Connection conectar = conect.conexion();
                Statement pst = conectar.createStatement();
                ResultSet rs = pst.executeQuery("call validar_Usuario('" + this.textCliente.getText() + "')");
                String i = "";
                while (rs.next()) {

                    i = rs.getString("nombre");

                    System.out.println("nombre = " + i + " contrasena");
                }

                if (i.equals("")) {
                    pst.executeQuery("call insert_Usuario('" + this.textCliente.getText() + "','" + this.textContrasena.getText() + "')");
                    File directorio = new File("Usuarios\\" + this.textCliente.getText());
                    if (!directorio.exists()) {
                        if (directorio.mkdirs()) {
                            System.out.println("Directorio creado");
                        } else {
                            System.out.println("Error al crear directorio");
                        }
                    }
                    this.textCliente.setText("");
                    this.textContrasena.setText("");
                    System.out.println("exito");
                } else {
                    System.out.println("El usuario ya existe");
                }
                conectar.close();
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
