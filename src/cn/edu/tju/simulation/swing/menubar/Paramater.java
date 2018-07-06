package cn.edu.tju.simulation.swing.menubar;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.tju.simulation.content.ContentService;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.file.Parameter;
import cn.edu.tju.simulation.handler.Pretreatment;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class Paramater extends JFrame{
	private static final long serialVersionUID = -1700018443869451957L;
	private JLabel powerLaw_R;
	private JLabel powerLaw_C;
	private JLabel requestProbability;
	private JLabel timeSlicesNumber;
	private JTextField powerLaw_R_Text;
	private JTextField powerLaw_C_Text;
	private JTextField requestProbability_Text;
	private JTextField timeSlicesNumber_Text;
	private JButton ensure;
	private JButton cancel;
	
	private JTextField BSMinWaveInterval;
	private JTextField BSMaxWaveInterval;
	private JLabel BSWaveIntervalLabel;
	
	private JTextField userMinWaveInterval;
	/**
	 * Text Field： maximum value of user's popularity fluctuation interval
	 */
	private JTextField userMaxWaveInterval;
	/**
	 * Label：User's fluctuation range
	 */
	private Paramater paramater;
	private JLabel userWaveIntervalLabel;
	private JFrame rootFrame;
	private Controller controller;
	
	public Paramater(Container frame,Controller controller){
		this.rootFrame = (JFrame)frame;
		this.controller = controller;
		
		this.powerLaw_R = new JLabel("PowerLaw R :");
		this.powerLaw_C = new JLabel("PowerLaw C :");
		this.requestProbability = new JLabel("Request Probability :");
		this.timeSlicesNumber = new JLabel("Time Slices Number :");
		
		this.powerLaw_R_Text = new JTextField();
		this.powerLaw_C_Text = new JTextField();
		this.requestProbability_Text = new JTextField();
		this.timeSlicesNumber_Text = new JTextField();
		
		this.BSMinWaveInterval = new JTextField();
		this.BSMaxWaveInterval = new JTextField();
		this.BSWaveIntervalLabel = new JLabel("Fluctuation :");
		
		this.userMaxWaveInterval = new JTextField();
		this.userMinWaveInterval = new JTextField();
		this.userWaveIntervalLabel = new JLabel("Fluctuation :");
	
		this.ensure = new JButton("确定");
		this.cancel = new JButton("取消");
		
		initial();
	}
	
	public void initial(){
		this.setTitle("Configure");
        this.setLocation(300,200);
		this.setSize(530, 300);
        this.setVisible(true);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		{
			JPanel content = new JPanel();
			content.setLayout(new GridLayout(0,2, 0, 10));
			content.setBorder(BorderFactory.createTitledBorder(content.getBorder(), "Content"));
			content.setBounds(30, 10, 200, 90);
			content.add(powerLaw_R);
			content.add(powerLaw_R_Text);
			content.add(powerLaw_C);
			content.add(powerLaw_C_Text);
			
			powerLaw_R_Text.setText(Float.toString(Parameter.PowerLaw_R));
			powerLaw_C_Text.setText(Integer.toString(Parameter.PowerLaw_C));
			
			this.add(content);
		}
		
		{
			JPanel user = new JPanel();
			user.setLayout(new GridLayout(0,2, 0, 10));
			user.setBorder(BorderFactory.createTitledBorder(user.getBorder(), "User"));
			user.setBounds(250, 10, 250, 90);
			user.add(requestProbability);
			user.add(requestProbability_Text);
			user.add(userWaveIntervalLabel);
			JPanel userWaveInterval = new JPanel();
			userWaveInterval.setLayout(new GridLayout(0,3, 0, 10));
			userWaveInterval.add(userMinWaveInterval);
			userWaveInterval.add(new JLabel("~"));
			userWaveInterval.add(userMaxWaveInterval);
			user.add(userWaveInterval);
			
			userMinWaveInterval.setText(Float.toString(Parameter.UserMinWaveInterval));
			userMaxWaveInterval.setText(Float.toString(Parameter.UserMaxWaveInterval));
			
			this.requestProbability_Text.setText(Float.toString(Parameter.RequestProbability));
			
			this.add(user);

		}
		
		{
			JPanel network = new JPanel();
			network.setLayout(new GridLayout(0,2, 0, 10));
			network.setBorder(BorderFactory.createTitledBorder(network.getBorder(), "Network"));
			network.setBounds(30, 110, 200, 90);
			network.add(BSWaveIntervalLabel);
			JPanel BSWaveInterval = new JPanel();
			BSWaveInterval.setLayout(new GridLayout(0,3, 0, 10));
			BSWaveInterval.add(BSMinWaveInterval);
			BSWaveInterval.add(new JLabel("~"));
			BSWaveInterval.add(BSMaxWaveInterval);
			network.add(BSWaveInterval);
			network.add(new JLabel());
			
			BSMinWaveInterval.setText(Float.toString(Parameter.BSMinWaveInterval));
			BSMaxWaveInterval.setText(Float.toString(Parameter.BSMaxWaveInterval));
			
			this.add(network);

		}
		
		{
			JPanel system = new JPanel();
			system.setLayout(new GridLayout(0,2, 0, 10));
			system.setBorder(BorderFactory.createTitledBorder(system.getBorder(), "System"));
			system.setBounds(250, 110, 250, 90);
			system.add(timeSlicesNumber);
			system.add(timeSlicesNumber_Text);
			system.add(new JLabel());
			this.timeSlicesNumber_Text.setText(controller.getOperationPanel().getTimeSlices().getSelectedItem().toString());
			
			this.add(system);
		}

		{
			JPanel button = new JPanel();
			button.setBounds(200, 230, 150, 90);
			button.add(ensure);
			button.add(cancel);
			this.add(button);
		}
		
		{
			addListener();
		}
	}

	private class MyActionListener implements ActionListener, WindowListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(ensure)){
				coverParamater();
				if(controller.hasUsers()){
					Pretreatment.process();
				}
				windowClosed(null);
			}else if(e.getSource().equals(cancel)){
				windowClosed(null);
			}
		}
		public void windowOpened(WindowEvent e) {rootFrame.setEnabled(false);}
		public void windowClosed(WindowEvent e) {rootFrame.setEnabled(true);paramater.dispose();}		
		public void windowClosing(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		
	}
	
	public void coverParamater(){
		Parameter.RequestProbability = Float.parseFloat(this.requestProbability_Text.getText());
		Parameter.BSMinWaveInterval = Float.parseFloat(this.BSMinWaveInterval.getText());
		Parameter.BSMaxWaveInterval = Float.parseFloat(this.BSMaxWaveInterval.getText());
		Parameter.UserMinWaveInterval = Float.parseFloat(this.userMinWaveInterval.getText());
		Parameter.UserMaxWaveInterval = Float.parseFloat(this.userMaxWaveInterval.getText());
		Parameter.PowerLaw_C = Integer.parseInt(this.powerLaw_C_Text.getText());
		Parameter.PowerLaw_R = Float.parseFloat(this.powerLaw_R_Text.getText());
		
		controller.getOperationPanel().getTimeSlices().setSelectedItem(Integer.parseInt(this.timeSlicesNumber_Text.getText()));
		
		ContentService.initialPopularity(controller.getOriginalContentList());
	}
	
	public void addListener(){
		MyActionListener anctionListener = new MyActionListener();
		this.ensure.addActionListener(anctionListener);
		this.cancel.addActionListener(anctionListener);
		this.addWindowListener(anctionListener);
	}
	
	public Paramater getParamater() {
		return paramater;
	}

	public void setParamater(Paramater paramater) {
		this.paramater = paramater;
	}
	
	
}
