/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import javax.swing.JLabel;

/**
 *
 * @author NattapatN
 */
public class ShowServer {
    
    public ShowServer(){}
    
    public int show(JLabel sAddr,JLabel sPort,String server,int port){
        
        int newPort=0;
            
            //Connect to server and get newPort.
            ConnectLiveServer conServer = new ConnectLiveServer(server,port);
            newPort = conServer.connect();
            
            sAddr.setText("Address : "+server);
            sPort.setText("Port : "+newPort);
            System.out.println("Server Address : "+server);
            System.out.println("Server Port : "+newPort);
            
        return newPort;
            
    }
    
}
