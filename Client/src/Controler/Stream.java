/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Module.ReadConnection;
import Module.RecordVideo;
import Module.SendStream;
import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author NattapatN
 */
public class Stream extends Thread {

    int loop;
    JLabel status;
    JLabel network;
    Dimension size;
    Webcam wCam;
    JButton liveButton;
    String server;
    int port;
    ArrayList<String> nic;
    ArrayList<String> bandwidth;
    int no, set;
    int[] duration;
    SendStream[] stream;
    int chunk;

    //temp
    ArrayList<String> tempNic;
    ArrayList<String> tempBandwidth;
    int tempNo = 0, tempSet = 0;
    int[] tempDuration;
    int tempChunk;
    SendStream[] tempStream;

    public Stream(Dimension size, Webcam wCam, JButton liveButton, String server, int port, int loop, JLabel network, JLabel status, ArrayList<String> nic, ArrayList<String> bandwidth) {
        this.size = size;
        this.wCam = wCam;
        this.liveButton = liveButton;
        this.server = server;
        this.port = port;
        this.loop = loop;
        this.network = network;
        this.status = status;
        this.tempNic = nic;
        this.tempBandwidth = bandwidth;
    }

    public void run() {
        tempChunk = calChunk();
        RecordVideo video = new RecordVideo(size, wCam);
        ArrayList<String> filename = new ArrayList<String>();
        ReadConnection connection = new ReadConnection(network, status);
        tempDuration = calDuration(tempBandwidth, tempChunk);
        int count = 0;
        long start = System.currentTimeMillis();
        tempStream = setStream(tempNic.size());
        while (System.currentTimeMillis() - start < 120000) {
            copy();
            filename.add("media/video" + set + "_" + no + ".mp4");
            video.record(filename.get(count), duration[count % nic.size()]);
            System.out.println("Record : " + filename.get(count));
            if (count >= nic.size()) {
                synchronized (stream[count % nic.size()]) {
                    stream[count % nic.size()].send(filename.get(count - nic.size()));
                }
            }
            if (connection.found()) {
                Thread thread = new Thread() {
                    public void run() {
                        connection.set();
                        tempNic = connection.readNIC();
                        tempBandwidth = connection.readBandwidth();
                        tempChunk = calChunk();
                        tempDuration = calDuration(bandwidth, chunk);
                        tempStream = setStream(nic.size());
                        tempNo = 0;
                        tempSet++;
                    }
                };
                thread.start();
            }
            tempNo++;
            count++;
        }
        liveButton.setText("END!!");
    }

    private int[] calDuration(ArrayList<String> bandwidth, int chunk) {
        int[] duration = new int[bandwidth.size()];
        int allBandwidth = 0;
        for (String b : bandwidth) {
            allBandwidth += Integer.parseInt(b);
        }
        for (int i = 0; i < bandwidth.size(); i++) {
            duration[i] = (int) ((Double.parseDouble(bandwidth.get(i)) / (double) allBandwidth) * (double) chunk);
        }
        return duration;
    }

    private int calChunk() {
        double chunk = 0;
        for (String b : tempBandwidth) {
            chunk += Double.parseDouble(b);
        }
        chunk = (2.95426 * Math.log(chunk)) - 6.28802;
        chunk *= 1000;
        return (int) chunk;
    }

    private SendStream[] setStream(int size) {
        SendStream[] stream = new SendStream[size];
        for (int i = 0; i < size; i++) {
            stream[i] = new SendStream(server, port, tempNic.get(i));
        }
        return stream;
    }

    private void copy() {
        nic = tempNic;
        chunk = tempChunk;
        bandwidth = tempBandwidth;
        duration = tempDuration;
        stream = tempStream;
        no = tempNo;
        set = tempSet;
    }

}
