package cn.edu.tju.simulation.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.apache.log4j.Logger;

import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.swing.log.Log;
import cn.edu.tju.simulation.swing.map.Map;
import cn.edu.tju.simulation.swing.menubar.MenuBar;
import cn.edu.tju.simulation.swing.operator.Operator;
import cn.edu.tju.simulation.swing.operator.Signal;

/**
 * The entrance of the program
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin
 *         University
 * 
 */
public class EdgeSim extends JFrame {
	private static final long serialVersionUID = -8348937984292754656L;

	private JFrame mainFrame;
	/**
	 * Menu container
	 */
	private MenuBar menu;
	/**
	 * Drawing container
	 */
	private Map map;
	/**
	 * Operate container
	 */
	private Operator operator;
	/**
	 * Log container
	 */
	private Log log;
	/**
	 * Controller
	 */
	private Controller controller;
	/**
	 * Log
	 */
	private static Logger logger = Logger.getLogger(EdgeSim.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EdgeSim window = new EdgeSim();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EdgeSim() {
		logger.info("The program is running");
		initial();
	}

	/**
	 * Add a controller to the emulator and initialize the main window. In
	 * addition, the main window adds listeners.
	 */
	private void initial() {
		{
			// Instantiate all components and controller
			Controller controller = new Controller();
			this.map = new Map();
			this.operator = new Operator();
			this.log = new Log();
			this.menu = new MenuBar();
			controller.initialController(menu, map, operator, log, controller);
		}

		{
			// Initialize UI
			JPanel panel = new JPanel();
			GridBagLayout variablePanel = new GridBagLayout();
			variablePanel.columnWidths = new int[] { 0, 0 };
			variablePanel.rowHeights = new int[] { 0, 0, 0 };
			variablePanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
			variablePanel.rowWeights = new double[] { 1.0, 0.0,
					Double.MIN_VALUE };
			panel.setLayout(variablePanel);

			// Initialize main frame
			mainFrame = new JFrame();
			mainFrame.setTitle("EdgeSim");
			mainFrame.setBounds(250, 0, 1400, 1050);
			
			mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);

			
			// mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); .
			mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			ImageIcon icon = new ImageIcon("src/main/resources/images/icon.png"); // xxx代表图片存放路径，2.png图片名称及格式
			mainFrame.setIconImage(icon.getImage());

			// Add menu
			mainFrame.getContentPane().add(menu, BorderLayout.NORTH);

			// Set the separation panel
			JSplitPane MainPanel = new JSplitPane();
			MainPanel.setContinuousLayout(true);
			MainPanel.setResizeWeight(1);
			MainPanel.setDividerLocation(0.9);
			mainFrame.getContentPane().add(MainPanel, BorderLayout.CENTER);

			JSplitPane MapAndLogPanel = new JSplitPane(
					JSplitPane.VERTICAL_SPLIT);
			MapAndLogPanel.setContinuousLayout(true);
			MapAndLogPanel.setResizeWeight(0.8);
			MainPanel.setDividerLocation(0.9);

			MapAndLogPanel.setLeftComponent(map);
			MapAndLogPanel.setRightComponent(log);

			MainPanel.setLeftComponent(MapAndLogPanel);
			MainPanel.setRightComponent(operator);

		}

		// Add listener
		mainFrame.addWindowListener(new MyActionListener());
	}

	// Main frame listener class.
	private class MyActionListener implements WindowListener {
		public void windowOpened(WindowEvent e) {
		}

		public void windowClosing(WindowEvent e) {
			if (Signal.SAVE == false) {
				int choice = JOptionPane.showConfirmDialog(null,
						"The file has not been saved, whether to quit?",
						"Prompt", JOptionPane.YES_NO_OPTION);
				if (choice == 0) {
					logger.info("The program is closed normally");
					System.exit(0);
				} else if (choice == 1) {

				}
			} else {
				logger.info("The program is closed normally");
				System.exit(0);
			}
		}

		public void windowClosed(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}

	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public Map getMap() {
		return map;
	}

	public Operator getOperator() {
		return operator;
	}

	public Controller getController() {
		return controller;
	}

}
