/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class Speed_Controler extends Thread {

    String server;
    int controlChannel;
    ArrayList<String> nic;
    int speedTime;

    public Speed_Controler(String server, int controlChannel,int speedTime) {
        this.server = server;
        this.controlChannel = controlChannel;
        this.speedTime = speedTime;
        ReadNIC readNIC = new ReadNIC();
        nic = readNIC.getNIC();
    }

    public void run() {
        SpeedTest speedTest = new SpeedTest(server, controlChannel);
        int speed = 0;
        BufferedWriter writer;
//        while (true) {
            try {
                if (new File("speed.txt").exists()) {
                    Path o = Paths.get("speed.txt");
                    Files.delete(o);
                    System.out.println("---------------( Check Speed )---------------");

                }
                writer = new BufferedWriter(new FileWriter("speed.txt_o"));
                for (String n : nic) {
                    speed = speedTest.test(n);
                    writer.write("u#"+n + " #" + speed + "\n");
                }
                writer.close();

                //test every x? s.
                File oldFile = new File("speed.txt_o");
                File newFile = new File("speed.txt");
                oldFile.renameTo(newFile);
                sleep(speedTime);

            } catch (InterruptedException ex) {
                Logger.getLogger(Speed_Controler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Speed_Controler.class.getName()).log(Level.SEVERE, null, ex);
            }
//        }
//        }
    }
}
