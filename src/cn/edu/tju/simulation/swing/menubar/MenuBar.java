package cn.edu.tju.simulation.swing.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.file.CaseFileReader;
import cn.edu.tju.simulation.file.CaseFileWriter;
import cn.edu.tju.simulation.swing.operator.Signal;

/**
 * Menu bar
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements ActionListener{
	/**
	 * Menu Options - File
	 */
	private JMenu file;
	/**
	 * Mednu Options -View
	 */
	private JMenu view;
	/**
	 * Menu Options - Help
	 */
	private JMenu help;
	private JMenu configure;
	/**
	 * Menu Sub-Options - File - Opens file
	 */
	private JMenuItem openFile;
	/**
	 * Menu sub-options - File - Save file
	 */
	private JMenuItem saveFile;
	/**
	 * Menu sub-options - View - View Base Station
	 */
	private JMenuItem viewBS;
	/**
	 * Menu sub-options - View - View content
	 */
	private JMenuItem viewContent;
	/**
	 * Menu Sub-Options - Help - About Edge Caching Simulation
	 */
	private JMenuItem viewHelp;
	private JMenuItem paramater;

	/**
	 * Controller
	 */
	private Controller controller;
	/**
	 * Log
	 */
	private static Logger logger = Logger.getLogger(MenuBar.class);
	

	public MenuBar(){
		initial();
		addListener();
	}
	
	public void initial(){
		//Menu option
		this.file = new JMenu("File");
		this.view = new JMenu("View");
		this.help = new JMenu("Help");
		this.configure = new JMenu("Configure");
		
		//Menu sub-options
		this.openFile =new JMenuItem("Open File...");
		this.saveFile =new JMenuItem("Save As...");
		this.viewContent = new JMenuItem("Content");
		this.viewHelp = new JMenuItem("About Edge Caching Simulation");
		this.viewBS = new JMenuItem("Base Station");
		this.paramater = new JMenuItem("Paramater");
		
		//Add menu sub-options
		this.file.add(openFile);
		this.file.add(saveFile);
		this.view.add(viewContent);
		this.view.add(viewBS);
		this.help.add(viewHelp);
		this.configure.add(paramater);
		
		//Add menu
		this.add(file);
		this.add(view);
		this.add(configure);
		this.add(help);
	}
	
	public void addListener(){
		openFile.addActionListener(this);
		saveFile.addActionListener(this);
		viewHelp.addActionListener(this);
		viewContent.addActionListener(this);
		viewBS.addActionListener(this);
		paramater.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(openFile)){
			openFileClicked();
		}else if(e.getSource().equals(saveFile)){
			saveFileClicked();
		}else if(e.getSource().equals(viewHelp)){
			JOptionPane.showMessageDialog(null, "Information about me", "About Edge Caching Simulation",JOptionPane.INFORMATION_MESSAGE);
		}else if(e.getSource().equals(viewContent)){
			ViewContent content = new ViewContent(getRootPane().getParent());
			content.setContent(content);
		}else if(e.getSource().equals(paramater)){
			Paramater paramater = new Paramater(getRootPane().getParent(),controller);
			paramater.setParamater(paramater);
		}else if(e.getSource().equals(viewBS)){
			ViewNetwork network = new ViewNetwork(getRootPane().getParent(), controller);
			network.setViewNetwork(network);
		}
	}
	
	public void openFileClicked(){
		JFileChooser filechooser = new JFileChooser();   
		filechooser.showOpenDialog(null);  
		File file = filechooser.getSelectedFile();  
		if(file != null){
			CaseFileReader reader = new CaseFileReader();
			if(reader.read(controller,file)){
				controller.appendLog("debug","Read file successfully",logger);
			//Read completed
			}else{
				JOptionPane.showMessageDialog(null,"Incorrect file format or incorrect formatting","Failed to read the file",JOptionPane.ERROR_MESSAGE);
				controller.appendLog("debug","Failed to read the file",logger);
			}
		}
	}
	
	public void saveFileClicked(){
		JFileChooser filechooser = new JFileChooser();   
		int option = filechooser.showSaveDialog(null);  
		//If the user chose to save file 
	    if(option==JFileChooser.APPROVE_OPTION){
	        File file = filechooser.getSelectedFile();
	        //Obtain the file name from the file name input box
	        String fname = filechooser.getName(file);       
	        //If the user fill in the file name without the suffix we developed, then we add it suffix  
	        if(fname.indexOf(".txt") == -1){  
	            file = new File(filechooser.getCurrentDirectory(),fname+".txt"); 
	        }  
	         CaseFileWriter writer = new CaseFileWriter();
	         if(writer.write(controller, file)){
	        	 //Saved successfully!
	        	 Signal.SAVE = true;
	        }
	    }  
	}

	public JMenu getFile() {
		return file;
	}

	public void setFile(JMenu file) {
		this.file = file;
	}

	public JMenu getHelp() {
		return help;
	}

	public void setHelp(JMenu help) {
		this.help = help;
	}

	public JMenuItem getOpenFile() {
		return openFile;
	}

	public void setOpenFile(JMenuItem openFile) {
		this.openFile = openFile;
	}

	public JMenuItem getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(JMenuItem saveFile) {
		this.saveFile = saveFile;
	}

	public JMenuItem getViewHelp() {
		return viewHelp;
	}

	public void setViewHelp(JMenuItem viewHelp) {
		this.viewHelp = viewHelp;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public JMenuItem getViewBS() {
		return viewBS;
	}

	public void setViewBS(JMenuItem viewBS) {
		this.viewBS = viewBS;
	}
	
	
}
