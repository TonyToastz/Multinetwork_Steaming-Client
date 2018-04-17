/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class ClearOldData {

    public ClearOldData() {
    }

    public void clear() {
        File folder = new File("media");
        if(new File("speed.txt").exists()){
            Path speed = Paths.get("speed.txt");
            try {
                Files.delete(speed);
                System.out.println("speed.txt was delete!!");
            } catch (IOException ex) {
                Logger.getLogger(ClearOldData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                try {
                    Path a = Paths.get("media/" + fileEntry.getName());
                    Files.delete(a);
                    System.out.println(fileEntry.getName()+"was delete!!");
                } catch (IOException ex) {
                    Logger.getLogger(ClearOldData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
