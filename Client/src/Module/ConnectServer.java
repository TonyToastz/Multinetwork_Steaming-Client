/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author NattapatN
 */
public class ConnectServer {
    
    String server;
    int port;
    
    public ConnectServer(String server,int port){
        this.server =server;
        this.port=port;
    }
    
    public int connect(){
        int newPort=0;
        try {
            Socket socket = new Socket(server,port);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            newPort=dis.readInt();
            return newPort;
        } catch (IOException ex) {
            return newPort;
        }
    }
    
}
