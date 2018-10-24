package cn.edu.tju.simulation.swing.log;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cn.edu.tju.simulation.controller.Controller;

/**
 * Log container
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 * 
 */
@SuppressWarnings("serial")
public class Log extends JScrollPane {
	/**
	 * Controller
	 */
	private Controller controller;
	/**
	 * Log
	 */
	private JTextArea text;
	
	public Log() {
		this.setBorder(BorderFactory.createTitledBorder(getBorder(), "Log"));
		
		text = new JTextArea();
		this.add(text);
		text.setOpaque(false);
		text.setText("Welcome to use Edge Caching Simulation!");
		text.setEditable(false);
		Initial();

	}

	public void Initial() {
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setViewportView(text);
	}

	public void append(String str){
			text.append("\n"+str);
			text.setCaretPosition(text.getDocument().getLength());
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
}
