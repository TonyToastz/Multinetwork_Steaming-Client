/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import com.github.sarxos.webcam.Webcam;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author NattapatN
 */
public class Record_Controler_WRR extends Thread {

    Dimension size;
    Webcam wCam;
    JButton liveButton;
    String server;
    int port;
    int fileChunk;
    int speedTime;
    ArrayList<String> speed;
    ArrayList<String> nic;
    ArrayList<String> use;
    SendStream stream[];
    int count = 0;
    int allFile = 0;

    public Record_Controler_WRR(Dimension size, Webcam wCam, JButton liveButton, String server, int port, int fileChunk, int speedTime) {
        this.size = size;
        this.wCam = wCam;
        this.liveButton = liveButton;
        this.server = server;
        this.port = port;
        this.fileChunk = fileChunk;
        this.speedTime = speedTime;
        use = new ArrayList<String>();
        ReadSpeed rSpeed = new ReadSpeed();
        speed = rSpeed.getSpeed();
        for (String t : speed) {
            String[] temp = t.split("#");
            if (temp[0].equals("u")) {
                use.add(t);
                allFile += Integer.parseInt(temp[2]);
            }
        }
        stream = new SendStream[use.size()];
        for (int i = 0; i < use.size(); i++) {
            stream[i] = new SendStream();
        }
        ReadNIC readNIC = new ReadNIC();
        nic = readNIC.getNIC();
        
    }

    public void run() {
        System.out.println("[Streaming...]");
        System.out.println(server+" : "+port);
        long startStreaming = System.currentTimeMillis();
        String[] speedUse;
        while (System.currentTimeMillis() - startStreaming < 120000) {
            IMediaWriter writer = ToolFactory.makeWriter("media/output" + count + ".mp4");
            writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
            long start = System.currentTimeMillis();
//            long start = System.currentTimeMillis();
//            double timing = (double)Integer.parseInt(time[count % time.length])/allTime;
            speedUse = use.get(count % use.size()).split("#");
            double timing = Double.parseDouble(speedUse[2]) / (double) allFile;
            for (int i = 0; System.currentTimeMillis() - start <= timing * fileChunk; i++) {
                BufferedImage image = ConverterFactory.convertToType(wCam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
                IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);
                IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
                frame.setKeyFrame(i == 0);
                frame.setQuality(0);
                writer.encodeVideo(0, frame);
            }
            Thread threadWrite = new Thread() {
                public void run() {
                    int c = count - 1;
//                    long startWirte = System.currentTimeMillis();
                    writer.close();
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Record_Controler_WRR.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                    double writetime = (double) (System.currentTimeMillis() - startWirte) / 1000;
                    if (c >= use.size()) {
                        boolean found = false;
                        while (!found) {
                            if (new File("media/output" + (c - use.size()) + ".mp4").exists()) {
                                synchronized (stream[count % use.size()]) {
                                    stream[count % use.size()].send(server, port,nic.get(c % use.size()), "media/output" + (c - use.size()) + ".mp4");
                                    found = true;
                                }
                            } else {
                                try {
                                    sleep(100);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Record_Controler_WRR.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }

                    }
//                    while (new File("media/output" + c + ".mp4").exists()) {
//                        String[] nic = use.get(c % use.size()).split("#");
//                        String fileName = "media/output" + c + ".mp4";
//                        System.out.println(nic[1]+" : "+fileName);
//                        stream.send(server, port, nic[1], fileName);
//                        try {
//                            sleep(100);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(Record_Controler_WRR.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
                }
            };
            threadWrite.start();
            count++;
        }
        liveButton.setText("•LIVE");
        liveButton.setEnabled(true);
    }

}
