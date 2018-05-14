/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class CheckConnection extends Thread {

    ArrayList<String> nic;
    ArrayList<String> use;
    String server;
    int port;

    public CheckConnection(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public void run() {
        use = new ArrayList<String>();
        GetConnection connection = new GetConnection();
        TestConnection test = new TestConnection(server, port);
        nic = connection.getNIC();

        for (int i = 0; i < nic.size(); i++) {
            if (test.connect(nic.get(i))) {
                int bandwidth = test.getBandwidth();
                use.add(i + "#" + bandwidth);
            }
        }
        if (new File("speed.txt_o").exists()) {
            try {
                Path o = Paths.get("speed.txt_o");
                Files.delete(o);
            } catch (IOException ex) {
                Logger.getLogger(CheckConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("speed.txt_o"));
            for (String u : use) {
                writer.write(u + "\n");
            }
            writer.close();
            File oldFile = new File("speed.txt_o");
            File newFile = new File("speed.txt");
            oldFile.renameTo(newFile);
//            System.out.println("Bandwidth writed!!");
        } catch (IOException ex) {
            Logger.getLogger(CheckConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
