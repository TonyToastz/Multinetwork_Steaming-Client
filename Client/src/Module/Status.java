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
public class Status {
    JLabel status;
    
    public Status(JLabel status){
        this.status =status;
    }
    
    public void setOffline(){
        status.setText("<html><font color=\"#e74c3c\" size=\"4\">•</font> Offline</html>");
    }
    
    public void setPrepare(){
        status.setText("<html> <font color=\"#e67e22\" size=\"4\">•</font> Preparing..</html>");
    }
    
    public void setReady(){
        status.setText("<html><font color=\"#27ae60\" size=\"4\">•</font> Ready</html>");
    }
    
}
