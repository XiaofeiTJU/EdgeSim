package cn.edu.tju.simulation.swing.menubar;

import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.io.File;  

import javax.swing.JFileChooser;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  

import org.apache.log4j.Logger;
  
/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class FileChooser extends JFrame implements ActionListener{  
	private static final long serialVersionUID = -6661122612585315490L;
	private Logger logger = Logger.getLogger(FileChooser.class);
	
    public static void main(String[] args) {  
        new FileChooser();  
    }  
    
    public FileChooser(){  
        this.setBounds(400, 200, 100, 100);  
        this.setVisible(true);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
    
    public void actionPerformed(ActionEvent e) {  
        JFileChooser jfc=new JFileChooser();  
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
        jfc.showDialog(new JLabel(), "Select");  
        File file=jfc.getSelectedFile();  
        if(file.isDirectory()){  
        	logger.debug("Folder:"+file.getAbsolutePath());  
        }else if(file.isFile()){  
            System.out.println("File:"+file.getAbsolutePath());  
        }  
        	logger.debug(jfc.getSelectedFile().getName());  
          
    }  
  
}  
