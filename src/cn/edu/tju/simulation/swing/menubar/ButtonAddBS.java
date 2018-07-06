package cn.edu.tju.simulation.swing.menubar;

import javax.swing.JButton;

/**
 * Add base station button
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
@SuppressWarnings("serial")
public class ButtonAddBS extends JButton{
	private Boolean click;
	
	public ButtonAddBS(String name){
		this.click = false;
		this.setText(name);
	}

	public Boolean getClick() {
		return click;
	}

	public void setClick(Boolean click) {
		this.click = click;
	}
	
	
}
