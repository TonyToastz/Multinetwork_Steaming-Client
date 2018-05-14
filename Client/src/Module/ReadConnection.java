/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author NattapatN
 */
public class ReadConnection {

    ArrayList<String> nic;
    ArrayList<String> bandwidth;
    JLabel network;
    JLabel status;

    public ReadConnection(JLabel network, JLabel status) {
        this.network = network;
        this.status = status;
    }

    public void set() {
        this.nic = new ArrayList<String>();
        this.bandwidth = new ArrayList<String>();
        GetConnection connection = new GetConnection();
        ArrayList<String> nic = connection.getNIC();
        FileReader fr = null;
        String temp;
        try {
            fr = new FileReader("speed.txt");
            BufferedReader br = new BufferedReader(fr);
            while ((temp = br.readLine()) != null) {
                String[] t = temp.split("#");
                this.nic.add(nic.get(Integer.parseInt(t[0])));
                this.bandwidth.add(t[1]);
            }
            br.close();
            fr.close();
            Thread thread = new Thread() {
                public void run() {
                    if (new File(
                            "speed.txt").exists()) {
                        Path o = Paths.get("speed.txt");
                        try {
                            Files.delete(o);
                        } catch (IOException ex) {
                            Logger.getLogger(ReadConnection.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            thread.start();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        String nw = "<html>";
        for (int i = 0; i < nic.size(); i++) {
            nw += "• " + nic.get(i) + "<br>  :: " + bandwidth.get(i) + " kbps.<br>";
        }
        nw += "</html>";
        network.setText(nw);
        status.setText("<html><font color=\"#27ae60\" size=\"4\">•</font> Online</html>");

    }

    public boolean found() {

        if (new File("speed.txt").exists()) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> readNIC() {
        return nic;
    }

    public ArrayList<String> readBandwidth() {
        return bandwidth;
    }
}
