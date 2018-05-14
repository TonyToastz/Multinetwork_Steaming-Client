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
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class SendStream extends Thread{
    
    String server;
    int port;
    String nic;
    
    public SendStream(String server,int port,String nic){
        this.server=server;
        this.port =port;
        this.nic=nic;
    }
    
    public void send(String filename){
        try {
            System.out.println("Send : "+filename);
            File file = new File(filename);
            Socket socket = new Socket();
            socket.bind(new InetSocketAddress(nic, 0));
            socket.connect(new InetSocketAddress(server, port));

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            FileInputStream fis = new FileInputStream(filename);
            dos.writeLong(file.length());

            byte[] buffer = new byte[4096];
            while (fis.read(buffer) > 0) {
                dos.write(buffer);
            }
            fis.close();
            long x =dis.readLong();
            dos.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(SendStream.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
