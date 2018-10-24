package cn.edu.tju.simulation.swing.menubar;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
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
	private JLabel ZIPF_SAMPLE;
	private JLabel ZIPF_CONEFFICIENT;
	private JLabel requestProbability;
	private JLabel timeSlicesNumber;
	private JTextField ZIPF_SAMPLE_Text;
	private JTextField ZIPF_CONEFFICIENT_Text;
	private JTextField requestProbability_Text;
	private JTextField timeSlicesNumber_Text;
	private JButton ensure;
	private JButton cancel;
	
	private JTextField BSMinWaveInterval;
	private JTextField BSMaxWaveInterval;
	private JLabel BSWaveIntervalLabel;
	
	private JTextField userMinWaveInterval;
	/**
	 * Text Field£º maximum value of user's popularity fluctuation interval
	 */
	private JTextField userMaxWaveInterval;
	/**
	 * Label£ºUser's fluctuation range
	 */
	private Paramater paramater;
	private JLabel userWaveIntervalLabel;
	private JFrame rootFrame;
	private Controller controller;
	
	private JLabel transmissionPower;
	private JTextField transmissionPower_Text;
	
	private JLabel noisePower;
	private JTextField noisePower_Text;
	
	private JLabel exponent;
	private JTextField exponent_Text;
	
	private JLabel bandWidth;
	private JTextField bandWidth_Text;
	
	private JLabel pathLoss;
	private JTextField pathLoss_Text;
	
	public Paramater(Container frame,Controller controller){
		this.rootFrame = (JFrame)frame;
		this.controller = controller;
		
		this.ZIPF_SAMPLE = new JLabel("Zipf Sample :");
		this.ZIPF_CONEFFICIENT = new JLabel("Zipf Exponent :");
		this.requestProbability = new JLabel("Request Probability :");
		this.timeSlicesNumber = new JLabel("Time Slices Number :");
		this.exponent = new JLabel("Exponent:");
		this.transmissionPower = new JLabel("Transmission Power:");
		this.noisePower = new JLabel("Noise:");
		this.bandWidth = new JLabel("Bandwidth:");
		this.pathLoss = new JLabel("Path Loss:");
		
		this.ZIPF_SAMPLE_Text = new JTextField();
		this.ZIPF_CONEFFICIENT_Text = new JTextField();
		this.requestProbability_Text = new JTextField();
		this.timeSlicesNumber_Text = new JTextField();
		
		this.BSMinWaveInterval = new JTextField();
		this.BSMaxWaveInterval = new JTextField();
		this.BSWaveIntervalLabel = new JLabel("Fluctuation :");
		
		this.userMaxWaveInterval = new JTextField();
		this.userMinWaveInterval = new JTextField();
		this.userWaveIntervalLabel = new JLabel("Fluctuation :");
		
		this.exponent_Text = new JTextField();
		this.transmissionPower_Text = new JTextField();
		this.noisePower_Text = new JTextField();
		this.bandWidth_Text = new JTextField();
		this.pathLoss_Text = new JTextField();
			
		this.ensure = new JButton("OK");
		this.cancel = new JButton("Cancel");
		
		initial();
	}
	
	public void initial(){
		this.setTitle("Configure");
        this.setLocation(300,200);
		this.setSize(400, 600);
        this.setVisible(true);
		this.setResizable(false);
		this.setLayout(new GridLayout(6 ,0, 0, 10));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		{
			JPanel content = new JPanel(new GridLayout(0,4, 0, 10));
			content.setBorder(BorderFactory.createTitledBorder(content.getBorder(), "Content"));
			
		
			content.add(ZIPF_SAMPLE);
			content.add(ZIPF_SAMPLE_Text);
			content.add(ZIPF_CONEFFICIENT);
			content.add(ZIPF_CONEFFICIENT_Text);
			content.add(new Label());
			
			ZIPF_SAMPLE_Text.setText(Integer.toString(Parameter.ZIPF_SAMPLE));
			ZIPF_CONEFFICIENT_Text.setText(Double.toString(Parameter.ZIPF_CONEFFICIENT));
			
			this.add(content);
		}
		
		{
			JPanel user = new JPanel(new GridLayout(2,0, 0, 10));
			user.setBorder(BorderFactory.createTitledBorder(user.getBorder(), "User"));
			
			JPanel upper = new JPanel(new GridLayout(0,3,0,10));	
			upper.add(requestProbability);
			JPanel uppser_text = new JPanel(new GridLayout(0,2,0,10));
			uppser_text.add(requestProbability_Text);
			upper.add(uppser_text);
			upper.add(new Label());
			user.add(upper);
			
			JPanel below = new JPanel(new GridLayout(0,4,0,10));
			below.add(userWaveIntervalLabel);
			JPanel userWaveInterval = new JPanel();
			userWaveInterval.setLayout(new GridLayout(0,3, 0, 10));
			userWaveInterval.add(userMinWaveInterval);
			userWaveInterval.add(new JLabel("~"));
			userWaveInterval.add(userMaxWaveInterval);
			below.add(userWaveInterval);
			
			user.add(below);
			
			userMinWaveInterval.setText(Float.toString(Parameter.UserMinWaveInterval));
			userMaxWaveInterval.setText(Float.toString(Parameter.UserMaxWaveInterval));
			
			this.requestProbability_Text.setText(Float.toString(Parameter.RequestProbability));
			
			this.add(user);

		}
		
		{
			JPanel network = new JPanel();
			network.setLayout(new GridLayout(2,0, 0, 10));
			network.setBorder(BorderFactory.createTitledBorder(network.getBorder(), "Network"));
			JPanel upper = new JPanel(new GridLayout(0,4, 0, 10));
			upper.add(BSWaveIntervalLabel);
			JPanel BSWaveInterval = new JPanel();
			BSWaveInterval.setLayout(new GridLayout(0,3, 0, 10));
			BSWaveInterval.add(BSMinWaveInterval);
			BSWaveInterval.add(new JLabel("~"));
			BSWaveInterval.add(BSMaxWaveInterval);
			upper.add(BSWaveInterval);
			network.add(upper);
			
			BSMinWaveInterval.setText(Float.toString(Parameter.BSMinWaveInterval));
			BSMaxWaveInterval.setText(Float.toString(Parameter.BSMaxWaveInterval));
			
			this.add(network);

		}
		
		{
			JPanel communication = new JPanel();
			communication.setLayout(new GridLayout(2,0, 0, 10));
			communication.setBorder(BorderFactory.createTitledBorder(communication.getBorder(), "Communication"));
			JPanel upper = new JPanel(new GridLayout(0,6, 0, 10));	
			upper.add(pathLoss);
			upper.add(pathLoss_Text);
			upper.add(exponent);
			upper.add(exponent_Text);
			upper.add(noisePower);
			upper.add(noisePower_Text);
			
			JPanel below = new JPanel(new GridLayout(0,3,0,10));
			JPanel below_bandwidth = new JPanel(new GridLayout(0,2,0,10));
			below_bandwidth.add(bandWidth);
			below_bandwidth.add(bandWidth_Text);
			below.add(below_bandwidth);
			below.add(transmissionPower);
			below.add(transmissionPower_Text);
			
			communication.add(upper);
			communication.add(below);
			
			pathLoss_Text.setText(Float.toString(Parameter.PATH_LOSS));
			exponent_Text.setText(Float.toString(Parameter.EXPONENT));
			noisePower_Text.setText(Float.toString(Parameter.GAUSSIAN_WHITE_NOISE_POWER));
			bandWidth_Text.setText(Float.toString(Parameter.BANDWIDTH));
			transmissionPower_Text.setText(Float.toString(Parameter.TRANSMISSION_POWER));
			
			this.add(communication);
		}
		
		{
			JPanel system = new JPanel();
			system.setLayout(new GridLayout(0,3, 0, 10));
			system.setBorder(BorderFactory.createTitledBorder(system.getBorder(), "System"));
			system.add(timeSlicesNumber);
			JPanel second = new JPanel(new GridLayout(0,2,0,10));
			second.add(timeSlicesNumber_Text);
			system.add(second);
			system.add(new Label());
			system.add(new Label());

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
		Parameter.ZIPF_SAMPLE = Integer.parseInt(this.ZIPF_SAMPLE_Text.getText());
		Parameter.ZIPF_CONEFFICIENT = Double.parseDouble(this.ZIPF_CONEFFICIENT_Text.getText());
		Parameter.PATH_LOSS = Float.parseFloat(this.pathLoss_Text.getText());
		Parameter.EXPONENT = Float.parseFloat(this.exponent_Text.getText());
		Parameter.GAUSSIAN_WHITE_NOISE_POWER = Float.parseFloat(this.noisePower_Text.getText());
		Parameter.BANDWIDTH = Float.parseFloat(this.bandWidth_Text.getText());
		Parameter.TRANSMISSION_POWER = Float.parseFloat(this.transmissionPower_Text.getText());
		
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
