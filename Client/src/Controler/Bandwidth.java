/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Module.CheckConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author NattapatN
 */
public class Bandwidth extends Thread {

    String server;
    int port;
    int loop;

    public Bandwidth(String server, int port, int loop) {
        this.loop = loop;
        this.server = server;
        this.port = port;
    }

    public void run() {
        while (true) {
            CheckConnection connection = new CheckConnection(server, port);
            connection.start();
            try {
                sleep(loop);
            } catch (InterruptedException ex) {
                Logger.getLogger(Bandwidth.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
