/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author NattapatN
 */
public class WriteNIC {

    public WriteNIC() {
    }

    public void write(JLabel network) {
        ArrayList<String> nic = new ArrayList<String>();
        FileReader fr;
        String temp;
        try {
            fr = new FileReader("speed.txt");
            BufferedReader br = new BufferedReader(fr);
            while ((temp = br.readLine()) != null && temp.length() != 0) {
                nic.add(temp.toString());
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WriteNIC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WriteNIC.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Show all network avialable.
        String showNIC = "<html>";
        for (int i = 0; i < nic.size(); i++) {
            //check connection
            String[] array = nic.get(i).split("#");
            if (array[0].equals("u")) {
                showNIC = showNIC + "<font color=\"#2ecc71\" size=\"4\">•</font>";
            } else {
                showNIC = showNIC + "<font color=\"#e74c3c\" size=\"4\">•</font>";
            }
            showNIC = showNIC +" /"+array[1]+"<br> :: "+array[2]+" Kb/s.<br>";

        }
        showNIC = showNIC + "</html>";
        network.setText(showNIC);
    }

}
