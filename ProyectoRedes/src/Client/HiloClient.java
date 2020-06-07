package Client;

import Utility.Utility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloClient extends Thread {

    private boolean execute;

    private String accion;
    private String filename;
    private String archivoADescargar;
    private String listaDeArchivos[];

    private Socket socket;

    private DataOutputStream send;
    private DataInputStream receive;

    public HiloClient() throws IOException {
        this.execute = true;
        this.accion = "";
        this.filename = "";
        this.archivoADescargar = "";
        this.listaDeArchivos = null;
        this.socket = new Socket();
        this.send = new DataOutputStream(this.socket.getOutputStream());
        this.receive = new DataInputStream(this.socket.getInputStream());
    }

    public HiloClient(Socket socket) throws IOException {
        this.execute = true;
        this.accion = "";
        this.filename = "";
        this.archivoADescargar = "";
        this.listaDeArchivos = null;
        this.socket = socket;
        this.send = new DataOutputStream(this.socket.getOutputStream());
        this.receive = new DataInputStream(this.socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            do {
                if (this.accion.equalsIgnoreCase(Utility.ENVIARARCHIVO)) {
                    enviarArchivo();
                } else if (this.accion.equalsIgnoreCase(Utility.DESCARGARARCHIVO)) {
                    descargarArchivo();
                }
            } while (this.execute);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void enviarArchivo() throws FileNotFoundException, IOException {
        if (!this.filename.equalsIgnoreCase("")) {
            int lectura;

            BufferedInputStream outputFile = new BufferedInputStream(new FileInputStream(new File(this.filename)));

            byte byteArray[] = new byte[1024];

            /* Avisa al servidor que se le enviara un archivo */
            this.send.writeUTF(Utility.AVISOENVIO);

            /* Se envia el nombre del archivo */
            this.send.writeUTF(this.filename);

            while ((lectura = outputFile.read(byteArray)) != -1) {
                this.send.write(byteArray, 0, lectura);
            }

            this.accion = "";
            this.filename = "";
            outputFile.close();
        }
    }

    public void descargarArchivo() throws IOException {
        this.send.writeUTF(Utility.AVISODESCARGA);
        this.send.writeUTF(this.archivoADescargar);

        String mensaje = this.receive.readUTF();

        if (mensaje.equalsIgnoreCase(Utility.CONFIRMADO)) {
            byte receivedData[] = new byte[1024];
            int lectura;

            /* Para guardar fichero recibido */
            BufferedOutputStream archivoRecibido = new BufferedOutputStream(new FileOutputStream(new File(this.archivoADescargar)));

            while ((lectura = this.receive.read(receivedData)) != -1) {
                archivoRecibido.write(receivedData, 0, lectura);
            }
            archivoRecibido.close();
        } else {
            System.err.println("No existe el archivo en el servidor");
        }
        
        this.accion = "";
        this.archivoADescargar = "";
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getArchivoADescargar() {
        return archivoADescargar;
    }

    public void setArchivoADescargar(String archivoADescargar) {
        this.archivoADescargar = archivoADescargar;
    }

    public String[] getListaDeArchivos() {
        return listaDeArchivos;
    }

    public void setListaDeArchivos(String[] listaDeArchivos) {
        this.listaDeArchivos = listaDeArchivos;
    }
}
