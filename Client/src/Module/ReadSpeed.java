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

/**
 *
 * @author NattapatN
 */
public class ReadSpeed {

    ArrayList<String> speed;
    
    public ReadSpeed() {
    }

    public ArrayList<String> getSpeed() {
        speed= new ArrayList<String>();
        FileReader fr;
        String temp;
        try {
            fr = new FileReader("speed.txt");
            BufferedReader br = new BufferedReader(fr); 
                while ((temp = br.readLine()) != null && temp.length() != 0) {
                speed.add(temp.toString());
            }

            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadSpeed.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadSpeed.class.getName()).log(Level.SEVERE, null, ex);
        }

        return speed;
    }

}
