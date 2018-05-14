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

/**
 *
 * @author NattapatN
 */
public class RecordVideo {
    
    Dimension size;
    Webcam wCam;
    
    public RecordVideo(Dimension size, Webcam wCam){
        this.size = size;
        this.wCam = wCam;
    }
    
    public void record(String filename,int duration){
        IMediaWriter writer = ToolFactory.makeWriter(filename);
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
        long start = System.currentTimeMillis();
        for (int i = 0; System.currentTimeMillis() - start <= duration; i++) {
            BufferedImage image = ConverterFactory.convertToType(wCam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
            IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);
            IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
            frame.setKeyFrame(i == 0);
            frame.setQuality(0);
            writer.encodeVideo(0, frame);
        }
        Thread threadWrite = new Thread() {
            public void run() {
                writer.close();
            }
        };
        threadWrite.start();
    }
    
}
