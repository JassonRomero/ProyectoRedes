package GUI;

import Client.HiloClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private JMenuBar jmbBarra;
    private JMenu jmMenu;
    private JMenuItem jmiActualizar;

    private JTable tabla;
    private DefaultTableModel modelo;

    private HiloClient cliente;

    public VentanaPrincipal(HiloClient cliente) {
        super("Cliente FTP");

        this.cliente = cliente;

        this.setLayout(null);
        this.setSize(800, 600);
        init();
    }

    private void init() {
        
        this.modelo = new DefaultTableModel();
        this.modelo.addColumn("Nombre");
        this.modelo.addColumn("Tamaño");
        
        System.out.println(this.modelo.getColumnName(0));
        
        this.tabla = new JTable();
        
        this.add(this.tabla);

        this.jmbBarra = new JMenuBar();
        this.jmMenu = new JMenu("Menú");

        this.jmiActualizar = new JMenuItem("Actualizar");
        this.jmiActualizar.addActionListener(this);

        this.jmMenu.add(this.jmiActualizar);
        this.jmbBarra.add(this.jmMenu);

        setJMenuBar(this.jmbBarra);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.jmiActualizar) {
            //this.cliente.setAccion(Utility.Utility.SOLICITARNOMBRESDEARCHIVOS);
        }
    }
}
