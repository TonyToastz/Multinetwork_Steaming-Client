/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
/**
 *
 * @author NattapatN
 */
public class GetConnection {
    
    ArrayList<String> nic;
     
    public GetConnection(){}
    
    public  ArrayList<String> getNIC(){
        nic = new ArrayList<String>();
        Enumeration<NetworkInterface> net = null;
        try { 
            // get all interfaces; ethernet, wifi, virtual... etc
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        if (net == null) {
            throw new RuntimeException("No network interfaces found.");
        }

        while (net.hasMoreElements()) {
            NetworkInterface element = net.nextElement();
            try {
                if (element.isVirtual() || element.isLoopback()) {
                    // discard virtual and loopback interface (127.0.0.1)
                    continue;
                }

                // rest are either Wifi or ethernet interfaces
                // loop through and print the IPs
                Enumeration<InetAddress> addresses = element.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();
                    if (ip instanceof Inet4Address) {
                        if (ip.isSiteLocalAddress()) {
//                            System.out.println(element.getName() + " - " + ip.getHostAddress());
                            nic.add(ip.getHostAddress());
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return nic;
    }
    
}
