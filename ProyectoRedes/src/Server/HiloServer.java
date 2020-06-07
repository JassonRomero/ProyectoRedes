/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Utility.Utility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    private boolean execute;
    private String accion;
    private String filename;
    private String rutaCarpeta;
    private String archivoADescargar;
    private Socket socket;
    private DataOutputStream send;
    private DataInputStream receive;

    public HiloServer(Socket socket) throws JDOMException, IOException {
        this.execute = true;
        this.accion = "";
        this.filename = "";
        this.rutaCarpeta = "";
        this.archivoADescargar = "";
        this.socket = socket;
        this.send = new DataOutputStream(this.socket.getOutputStream());
        this.receive = new DataInputStream(this.socket.getInputStream());
    }

    public HiloServer() throws IOException {
        this.execute = true;
        this.accion = "";
        this.filename = "";
        this.rutaCarpeta = "";
        this.archivoADescargar = "";
        this.socket = new Socket();
        this.send = new DataOutputStream(this.socket.getOutputStream());
        this.receive = new DataInputStream(this.socket.getInputStream());
    }

    @Override
    public void run() {

        try {
            do {
                this.accion = this.receive.readUTF();
                if (this.accion.equalsIgnoreCase(Utility.AVISOLISTAR)) {
                    listarArchivos();
                } else if (this.accion.equalsIgnoreCase(Utility.AVISODESCARGA)) {
                    enviarArchivo();
                } else if (this.accion.equalsIgnoreCase(Utility.AVISOENVIO)) {
                    recibirArchivo();
                    listarArchivos();
                }
            } while (this.execute);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void enviarArchivo() throws FileNotFoundException, IOException {
        this.filename = this.receive.readUTF();
        File archivo = new File(this.rutaCarpeta + "\\" + this.filename);
        if (archivo.exists()) {
            this.send.writeUTF(Utility.CONFIRMADO);
            int lectura;
            BufferedInputStream outputFile = new BufferedInputStream(new FileInputStream(archivo));

            byte byteArray[] = new byte[1024];

            while ((lectura = outputFile.read(byteArray)) != -1) {
                this.send.write(byteArray, 0, lectura);
            }

            this.accion = "";
            this.filename = "";
            outputFile.close();
        } else {
            this.send.writeUTF(Utility.DENEGADO);
        }
    }

    public void recibirArchivo() throws IOException {
        this.filename = this.receive.readUTF();

        byte receivedData[] = new byte[1024];
        int lectura;

        /* Para guardar fichero recibido */
        BufferedOutputStream archivoRecibido = new BufferedOutputStream(new FileOutputStream(new File(this.rutaCarpeta + "\\" + this.filename)));

        while ((lectura = this.receive.read(receivedData)) != -1) {
            archivoRecibido.write(receivedData, 0, lectura);
        }
        this.accion = "";
        this.filename = "";

        archivoRecibido.close();
    }

    public void listarArchivos() throws IOException {
        File carpeta = new File(this.rutaCarpeta);
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
        } else {
            for (int i = 0; i < listado.length; i++) {
                this.send.writeUTF(listado[i]);
                //System.out.println(listado[i]);
            }
        }
    }
}
