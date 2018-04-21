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
import java.util.ArrayList;
import javax.swing.JButton;

/**
 *
 * @author NattapatN
 */
public class Record_Controller_DRR extends Thread {

    Dimension size;
    Webcam wCam;
    JButton liveButton;
    int count = 0;
    int allTime = 0;
    int fileChunk;
    ArrayList<String> speed;
    int speedTime;
    long startTest;
    String server;
    ArrayList<String> use;
    int port;
    SendStream stream = new SendStream();
    int allFile = 0;

    public Record_Controller_DRR(Dimension size, Webcam wCam, JButton liveButton, String server, int port, int fileChunk, int speedTime) {
        this.size = size;
        this.wCam = wCam;
        this.liveButton = liveButton;
        this.server = server;
        this.port = port;
        this.fileChunk = fileChunk;
        ReadSpeed rSpeed = new ReadSpeed();
        speed = rSpeed.getSpeed();
        for (String t : speed) {
            String[] temp = t.split("#");
            if (temp[0].equals("u")) {
                use.add(t);
                allFile += Integer.parseInt(temp[2]);
            }
        }
        this.speedTime = speedTime;
    }

    public void run() {
        System.out.println("[Streamin...]");
        long startStreaming = System.currentTimeMillis();
        startTest = System.currentTimeMillis();
        String[] speedUse;
        while (System.currentTimeMillis() - startStreaming < 240000) {
            IMediaWriter writer = ToolFactory.makeWriter("media/output" + count + ".mp4");
            writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
            long start = System.currentTimeMillis();
//            double timing = (double)Integer.parseInt(time[count % time.length])/allTime;
            speedUse = use.get(count%use.size()).split("#");
            double timing = Double.parseDouble(speedUse[2])/(double)allFile;
            for (int i = 0; System.currentTimeMillis() - start <= timing * 10000; i++) {
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
//                    double writetime = (double) (System.currentTimeMillis() - startWirte) / 1000;

                    String[] nic = use.get(c % use.size()).split("#");
                    String fileName = "media/output" + c + ".mp4";
                    stream.send(server, port, nic[1], fileName);

                    if (System.currentTimeMillis() - startTest > speedTime) {
                        allFile = 0;
                        ReadSpeed rSpeed = new ReadSpeed();
                        speed = rSpeed.getSpeed();
                        for (int i = use.size() - 1; i >= 0; i--) {
                            use.remove(i);
                        }
                        for (String t : speed) {
                            String[] temp = t.split("#");
                            if (temp[0].equals("u")) {
                                use.add(t);
                                allFile += Integer.parseInt(temp[2]);
                            }
                        }
                        startTest = System.currentTimeMillis();
                    }
//                    double thisTime = (double) (System.currentTimeMillis() - startStreaming) / 60000;
//                    System.out.println(c + "\t\t" + String.format("%.3f", writetime) + " s.\t" + startWirte + "\ttime : " + String.format("%.3f", thisTime) + " m.");
                }
            };
            threadWrite.start();
            count++;
        }
        liveButton.setText("•LIVE");
        liveButton.setEnabled(true);
    }

}
