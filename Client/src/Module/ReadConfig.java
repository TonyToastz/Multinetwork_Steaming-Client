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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nattapatn
 */
public class ReadConfig {

    String server;
    int port;
    int speedTime;

    public ReadConfig() {
        FileReader fr;
        try {
            fr = new FileReader("config.txt");
            BufferedReader br = new BufferedReader(fr);
            server = br.readLine();
            port = Integer.parseInt(br.readLine());
            speedTime = Integer.parseInt(br.readLine());
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public int getTime(){
        return speedTime;
    }

}
