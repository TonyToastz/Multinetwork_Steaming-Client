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
public class SpeedTest {

    String server;
    int port;
    String file;
    File testFile;

    public SpeedTest(String server, int port) {
        file = "TestFile.db";
        testFile = new File(file);
        this.server = server;
        this.port = port;
    }

    public int test(String nic) {
        double speed = 0;
        try {
            Socket socket = new Socket();
            socket.bind(new InetSocketAddress(nic, 0));
            socket.connect(new InetSocketAddress(server, port));

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
            speed = ((double) finish - (double) start) / 1000;
            speed = (100 / speed) * 8;
            System.out.println(nic + " : " + (int)speed + " Kb/s.");
            dis.close();
            fis.close();
            dos.close();

        } catch (IOException ex) {
            Logger.getLogger(SpeedTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (int) speed;
    }

}
