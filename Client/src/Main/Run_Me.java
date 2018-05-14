/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.Scanner;

/**
 *
 * @author NattapatN
 */
public class Run_Me {
    
    public static void main(String []args){
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Enter Server Address : ");
        String server =scan.nextLine();
        System.out.print("Enter Server Port : ");
        int port = scan.nextInt();
        System.out.print("Enter Loop (s.) : ");
        int loop = scan.nextInt();
        
        Main_Frame frame = new Main_Frame(server,port,loop);
        frame.setVisible(true);
    }
}
