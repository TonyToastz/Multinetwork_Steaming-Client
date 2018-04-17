/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

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
public class SendStream {
    
    public SendStream() {
    }

    public void send(String server, int port, String nic, String filename) {
        try {
            File file = new File(filename);
            Socket socket = new Socket();
            socket.bind(new InetSocketAddress(nic, 0));
            socket.connect(new InetSocketAddress(server, port));

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(filename);
            dos.writeLong(file.length());

            byte[] buffer = new byte[4096];
            while (fis.read(buffer) > 0) {
                dos.write(buffer);
            }
            fis.close();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(SendStream.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
