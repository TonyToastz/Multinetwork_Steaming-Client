/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class ConnectLiveServer {
    
    String server;
    int port;
    ArrayList<String> nic;
    
    public ConnectLiveServer(String server,int port){
        this.server =server;
        this.port = port;
        this.nic =nic;
    }
    
    public int connect(){
        int newPort=0;
        Socket socket;
        try {
            socket = new Socket(server, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            newPort = in.readInt();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnectLiveServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newPort;
    }
}
