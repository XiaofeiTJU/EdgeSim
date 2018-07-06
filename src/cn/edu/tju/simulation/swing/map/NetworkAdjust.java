package cn.edu.tju.simulation.swing.map;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class NetworkAdjust extends JFrame{
	private static final long serialVersionUID = 1607891817972634667L;
	/**
	 * Main frame
	 */
	private JFrame frame;
	/**
	 * Label - base station radius
	 */
	private JLabel radiusLabel;
	/**
	 * Text filed - base station radius
	 */
	private JTextField radiusText;
	/**
	 * Label - base station cache
	 */
	private JLabel cacheLabel;
	/**
	 * Text filed - base station cache
	 */
	private JTextField cacheText;
	/**
	 * Button - OK
	 */
	private JButton ensure;
	/**
	 * Button - cancel
	 */
	private JButton cancel;
	/**
	 * wireless network
	 */
	private WirelessNetwork network;
	/**
	 * Label - x
	 */
	private JLabel XLabel;
	/**
	 * Label - y
	 */
	private JLabel YLabel;
	/**
	 * Text filed - x
	 */
	private JTextField XText;
	/**
	 * Text filed - y
	 */
	private JTextField YText;
	
	private NetworkAdjust networkAdjust;
	
	
	public NetworkAdjust(WirelessNetwork network){
		this.network = network;
        this.setLayout(new GridLayout(5, 0, 0, 3));
        
        initial();
        addListener(); 
	}
	
	private class MouseAction extends MouseAdapter {
		public void mousePressed(MouseEvent e) { // Press the left mouse button
			if(e.getSource().equals(ensure)){
				network.setRadius(Integer.parseInt(radiusText.getText()));
				network.setCacheSize(Long.parseLong(cacheText.getText()));
				network.setLocation(new Point2D.Double(Double.parseDouble(XText.getText()),Double.parseDouble(YText.getText())));
				networkAdjust.dispose();
			}else if(e.getSource().equals(cancel)){
				networkAdjust.dispose();
			}
		}
	}
	
	public void addListener(){
		MouseAction mouseAction  = new MouseAction();
		this.ensure.addMouseListener(mouseAction);
		this.cancel.addMouseListener(mouseAction);
	}
	
	@SuppressWarnings("static-access")
	public void initial(){
		this.setTitle("Network Parameters");
        this.setLocation(300,200);
        this.setSize(400, 230);
        this.setVisible(true);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		
        XText= new JTextField();
        XLabel = new JLabel("X£º");
        YText = new JTextField();
        YLabel = new JLabel("Y£º");
        radiusLabel = new JLabel("Radius£º");
        radiusText = new JTextField();
        cacheLabel = new JLabel("Cache size£º");
        cacheText = new JTextField();
        ensure = new JButton("OK");
        cancel = new JButton("Cancle");
        
        JPanel x = new JPanel();
        x.setLayout(new GridLayout(0, 4, 0, 3));
        JPanel y = new JPanel();
        y.setLayout(new GridLayout(0, 4, 0, 3));
        x.add(new JLabel());
        x.add(XLabel);
        x.add(XText);
        y.add(new JLabel());
        y.add(YLabel);
        y.add(YText);
        
        JPanel radius = new JPanel();
        radius.setLayout(new GridLayout(0, 4, 0, 3));
        JPanel cache = new JPanel();
        cache.setLayout(new GridLayout(0, 4, 0, 3));
        JPanel option = new JPanel();
        option.setLayout(new GridLayout(0, 4, 0, 3));
        
        
        
        radius.add(new JLabel());
        radius.add(radiusLabel);
        radius.add(radiusText);
        cache.add(new JLabel());
        cache.add(cacheLabel);
        cache.add(cacheText);
        option.add(new Label());
        option.add(ensure);
        option.add(cancel);
        
        this.add(x);
        this.add(y);
        this.add(radius);
        this.add(cache);
        this.add(option);
        
        radiusText.setText(network.getRadius()+"");
        cacheText.setText(network.getCacheSize()+"");
        XText.setText(String.valueOf(network.getLocation().getX()));
        YText.setText(String.valueOf(network.getLocation().getY()));
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public JLabel getRadiusLabel() {
		return radiusLabel;
	}

	public void setRadiusLabel(JLabel radiusLabel) {
		this.radiusLabel = radiusLabel;
	}

	public JTextField getRadiusText() {
		return radiusText;
	}

	public void setRadiusText(JTextField radiusText) {
		this.radiusText = radiusText;
	}

	public JLabel getCacheLabel() {
		return cacheLabel;
	}

	public void setCacheLabel(JLabel cacheLabel) {
		this.cacheLabel = cacheLabel;
	}

	public JTextField getCacheText() {
		return cacheText;
	}

	public void setCacheText(JTextField cacheText) {
		this.cacheText = cacheText;
	}

	public JButton getEnsure() {
		return ensure;
	}

	public void setEnsure(JButton ensure) {
		this.ensure = ensure;
	}

	public JButton getCancel() {
		return cancel;
	}

	public void setCancel(JButton cancel) {
		this.cancel = cancel;
	}

	public WirelessNetwork getNetwork() {
		return network;
	}

	public void setNetwork(WirelessNetwork network) {
		this.network = network;
	}

	public NetworkAdjust getNetworkAdjust() {
		return networkAdjust;
	}

	public void setNetworkAdjust(NetworkAdjust networkAdjust) {
		this.networkAdjust = networkAdjust;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JLabel getXLabel() {
		return XLabel;
	}

	public void setXLabel(JLabel xLabel) {
		XLabel = xLabel;
	}

	public JLabel getYLabel() {
		return YLabel;
	}

	public void setYLabel(JLabel yLabel) {
		YLabel = yLabel;
	}

	public JTextField getXText() {
		return XText;
	}

	public void setXText(JTextField xText) {
		XText = xText;
	}

	public JTextField getYText() {
		return YText;
	}

	public void setYText(JTextField yText) {
		YText = yText;
	}

	
}
