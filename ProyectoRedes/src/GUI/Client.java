package GUI;

import Client.HiloClient;
import Server.Conectar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.jdom.JDOMException;

public class Client extends JFrame implements Runnable, ActionListener {

    private Thread hilo;

    private JLabel labelIp;
    private JLabel labelId;
    private JLabel labelPassword;

    private JTextField textIp;
    private JTextField textId;
    private JPasswordField textPassword;

    private JButton botonIniciar;

    private HiloClient cliente;

    public Client() throws JDOMException, IOException {
        super("Client");

        this.setLayout(null);
        this.setSize(250, 250);

        init();
        
        this.cliente = new HiloClient(new Socket("192.168.1.1", Utility.Utility.SOCKETNUMBER));
        
        this.hilo = new Thread(this);
        this.hilo.start();
    }

    private void init() throws JDOMException, IOException {
        this.labelIp = new JLabel("Ip:");
        this.labelIp.setBounds(10, 10, 75, 30);
        this.add(this.labelIp);

        this.labelId = new JLabel("Nombre:");
        this.labelId.setBounds(10, 60, 75, 30);
        this.add(this.labelId);

        this.labelPassword = new JLabel("Contraseña");
        this.labelPassword.setBounds(10, 110, 100, 30);
        this.add(this.labelPassword);

        this.textIp = new JTextField();
        this.textIp.setBounds(100, 10, 100, 30);
        this.add(this.textIp);

        this.textId = new JTextField();
        this.textId.setBounds(100, 60, 100, 30);
        this.add(this.textId);

        this.textPassword = new JPasswordField();
        this.textPassword.setBounds(100, 110, 100, 30);
        this.add(this.textPassword);

        this.botonIniciar = new JButton("Iniciar");
        this.botonIniciar.setBounds(50, 150, 100, 30);
        this.botonIniciar.addActionListener(this);
        this.add(this.botonIniciar);
    }

    @Override
    public void run() {

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.botonIniciar) {
            /*
            Conectar conect = new Conectar();
            Connection conectar = conect.conexion();
            PreparedStatement pst = conectar.prepareStatement("");
            pst.setString(1, this.textId.getText());
            pst.setString(2, this.textPassword.getText());
            if (0 < pst.executeUpdate()) {
            this.textId.setText("");
            this.textIp.setText("");
            this.textPassword.setText("");
            this.dispose();
            }
             */ 
            this.dispose();
            VentanaPrincipal ventana = new VentanaPrincipal(this.cliente);
            ventana.setVisible(true);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLocationRelativeTo(null);
            ventana.setResizable(false);
        }
    }
}
