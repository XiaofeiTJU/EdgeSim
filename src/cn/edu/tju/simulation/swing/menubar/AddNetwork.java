package cn.edu.tju.simulation.swing.menubar;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.wirelessnetwork.BaseStation;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class AddNetwork extends JFrame{
	private static final long serialVersionUID = -494149436472757127L;
	
	/**
	 * Label - type 
	 */
	private JLabel type;
	/**
	 * ComboBox - type 
	 */
	private JComboBox<String> typeBox;
	/**
	 * Label - x
	 */
	private JLabel x;
	/**
	 * Text filed - x
	 */
	private JTextField xText;
	/**
	 * Label - y
	 */
	private JLabel y;
	/**
	 * Text filed - y
	 */
	private JTextField yText;
	/**
	 * Label - radius
	 */
	private JLabel radius;
	/**
	 * Text filed - radius
	 */
	private JTextField radiusText;
	/**
	 * Label - cache size
	 */
	private JLabel cacheSize;
	/**
	 * Text filed - cache size
	 */
	private JTextField cacheSizeText;
	/**
	 * Button - OK
	 */
	private JButton ensure;
	/**
	 * Button - cancel
	 */
	private JButton cancel;

	private AddNetwork addNetwork;
	private ViewNetwork viewNetwork;
	
	
	public AddNetwork(ViewNetwork viewNetwork){    
		this.viewNetwork = viewNetwork; 

		initial();
        addListener(); 
	}
	
	private class ActionListener extends MouseAdapter implements WindowListener{
		public void mousePressed(MouseEvent e) { // Press the left mouse button
			if(e.getSource().equals(ensure)){
				WirelessNetwork noAddedNetwork = clickEnsure();
				if(noAddedNetwork != null){
					Controller.getInstance().getWirelessNetworkGroup().BS.addWirelessNetwork(noAddedNetwork);
					viewNetwork.tableUpdate();
					addNetwork.dispose();
				}else{
					JOptionPane.showMessageDialog(null,"Parameter can not be empty!","Prompt",JOptionPane.ERROR_MESSAGE);
				}
			}else if(e.getSource().equals(cancel)){
				addNetwork.dispose();
			}
		}

		public void windowOpened(WindowEvent e) {viewNetwork.setEnabled(false);}
		public void windowClosed(WindowEvent e) {viewNetwork.setEnabled(true);}
		public void windowClosing(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
	}
	
	public void addListener(){
		ActionListener actionListener  = new ActionListener();
		this.addWindowListener(actionListener);
		this.ensure.addMouseListener(actionListener);
		this.cancel.addMouseListener(actionListener);
	}
	
	public WirelessNetwork clickEnsure(){
		String type = (String)typeBox.getSelectedItem();
		String x = xText.getText();
		String y = yText.getText();
		String radius = radiusText.getText();
		String cacheSize = cacheSizeText.getText();

		if(type != null && !type.trim().equals("") && x != null && !x.trim().equals("") && y != null && !y.trim().equals("") && radius != null && !radius.trim().equals("") && cacheSize != null && !cacheSize.trim().equals("")){
			if(type .equals("BS")){
				WirelessNetwork network = new BaseStation(new Point2D.Double(Double.parseDouble(x),Double.parseDouble(y)), true, Long.parseLong(cacheSize)*1024*1024*1024, Integer.parseInt(radius));
				return network;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public void initial(){
        this.setLayout(new GridLayout(5, 0, 0, 3));
		this.setTitle("Add content");
        this.setLocation(300,200);
        this.setSize(350,230);
		this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(AddNetwork.DISPOSE_ON_CLOSE);
		
        {
        	type = new JLabel("Type£º");
            typeBox = new JComboBox<String>();
            typeBox.addItem("BS");
        	JPanel typePanel = new JPanel();
        	typePanel.setLayout(new GridLayout(0, 4, 0, 3));
        	typePanel.add(new JLabel());
        	typePanel.add(type);
        	typePanel.add(typeBox);
            this.add(typePanel);
        }
        
        {
        	 x = new JLabel("X£º");
             xText = new JTextField();
             y = new JLabel("Y£º");
             yText = new JTextField();
             JPanel xPanel = new JPanel();
             xPanel.setLayout(new GridLayout(0,2,0,3));
             xPanel.add(x);
             xPanel.add(xText);
             JPanel yPanel = new JPanel();
             yPanel.setLayout(new GridLayout(0,2,0,3));
             yPanel.add(y);
             yPanel.add(yText);
             JPanel coordinatePanel = new JPanel();
             coordinatePanel.setLayout(new GridLayout(0, 4, 0, 3));
             coordinatePanel.add(new JLabel());
             coordinatePanel.add(xPanel);
             coordinatePanel.add(yPanel);
             this.add(coordinatePanel);
        }
       
        {
            radius = new JLabel("Radius:");
            radiusText = new JTextField();     	
            JPanel radiusPanel = new JPanel();
            radiusPanel.setLayout(new GridLayout(0, 4, 0, 3));
            radiusPanel.add(new JLabel());
            radiusPanel.add(radius);
            radiusPanel.add(radiusText);
            this.add(radiusPanel);
        }
        
        {
            cacheSize = new JLabel("Cache Size:");
            cacheSizeText = new JTextField();     	
            JPanel cacheSizePanel = new JPanel();
            cacheSizePanel.setLayout(new GridLayout(0, 4, 0, 3));
            cacheSizePanel.add(new JLabel());
            cacheSizePanel.add(cacheSize);
            cacheSizePanel.add(cacheSizeText);
            this.add(cacheSizePanel);
        }

        {
        	ensure = new JButton("OK");
            cancel = new JButton("Cancle");
            JPanel option = new JPanel();
            option.setLayout(new GridLayout(0, 4, 0, 3));
            option.add(new Label());
            option.add(ensure);
            option.add(cancel);
            this.add(option);
        }
	}

	public AddNetwork getAddNetwork() {
		return addNetwork;
	}

	public void setAddNetwork(AddNetwork addNetwork) {
		this.addNetwork = addNetwork;
	}
	
}
