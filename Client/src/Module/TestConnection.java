/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class TestConnection {

    String server;
    int port;
    Socket socket;
    String nic;

    public TestConnection(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public boolean connect(String nic) {
        this.nic = nic;
        try {
            socket = new Socket();
            socket.bind(new InetSocketAddress(nic, 0));
            socket.connect(new InetSocketAddress(server, port));
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public int getBandwidth() {
        double bandwidth = 0;
        String file = "TestFile.db";
        File testFile = new File(file);
        try {

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(file);
            dos.writeLong(testFile.length());

            byte[] buffer = new byte[4096];
            long start = System.currentTimeMillis();
            while (fis.read(buffer) > 0) {
                dos.write(buffer);
            }
            long finish = dis.readLong();
            finish = System.currentTimeMillis();
            bandwidth = ((double) finish - (double) start) / 1000;
            bandwidth = (100 / bandwidth) * 8;
//            System.out.println(nic + " : " + (int) bandwidth + " Kb/s.");
            dis.close();
            fis.close();
            dos.close();

        } catch (FileNotFoundException ex) { 
            Logger.getLogger(TestConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (int) bandwidth;
    }

}
