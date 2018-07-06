package cn.edu.tju.simulation.swing.operate;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import cn.edu.tju.simulation.algorithm.GreedyAlgorithm;
import cn.edu.tju.simulation.algorithm.KnapsackAlgorithm;
import cn.edu.tju.simulation.algorithm.LFUAlgorithm;
import cn.edu.tju.simulation.algorithm.LRUAlgorithm;
import cn.edu.tju.simulation.algorithm.QLearning;
import cn.edu.tju.simulation.content.ContentService;
import cn.edu.tju.simulation.content.MySingleContent;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.data.Data;
import cn.edu.tju.simulation.file.Parameter;
import cn.edu.tju.simulation.file.RandomBSReader;
import cn.edu.tju.simulation.file.ResultDateWiriter;
import cn.edu.tju.simulation.handler.Pretreatment;
import cn.edu.tju.simulation.handler.RequestHandler;
import cn.edu.tju.simulation.swing.chart.DelayLineChart;
import cn.edu.tju.simulation.swing.chart.TimeLineChart;
import cn.edu.tju.simulation.swing.chart.TrafficLoadLineChart;
import cn.edu.tju.simulation.tool.ToolKit;
import cn.edu.tju.simulation.user.MobilityModelGenerator;
import cn.edu.tju.simulation.wirelessnetwork.SameTypeWirelessNetwork;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * Container for parameter configuration
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 * 
 */
@SuppressWarnings("serial")
public class Operate extends JPanel{

	/**
	 * Button: add a base station
	 */
	private JButton addBS;
	/**
	 * Button: randomly generated base station
	 */
	private JButton addBSRandomly;
	/**
	 * Button: randomly generate users
	 */
	private JButton addUser;
	/**
	 * Button: run
	 */
	private JButton run;
	/**
	 * Button: generate data chart
	 */
	private JButton generateChart;
	/**
	 * Panel: radius
	 */
	private JPanel Radius;
	/**
	 * Panel: cache
	 */
	private JPanel Cache;
	/**
	 * Panel：base station's popularity fluctuation range
	 */
	private JPanel WaveInterval_BS;
	/**
	 * Panel: user
	 */
	private JPanel User;
	/**
	 * Panel: user's popularity fluctuation range
	 */
	private JPanel WaveInterval_User;
	/**
	 * Frame object
	 */
	private JFrame jframe;
	/**
	 * Controller
	 */
	private Controller controller;
	/**
	 * Label: radius
	 */
	private JLabel radiusLabel;
	/**
	 * Text Field: radius
	 */
	private JTextField radiusText;
	/**
	 * Label：Cache size
	 */
	private JLabel cacheSizeLabel;
	/**
	 * Text Field：Cache size
	 */
	private JTextField cacheSizeText;
	/**
	 * Text Field：minimum value of base station's popularity fluctuation interval
	 */
	private JTextField BSMinWaveInterval;
	/**
	 * Text Field： maximum value of base station's popularity fluctuation interval
	 */
	private JTextField BSMaxWaveInterval;
	/**
	 * Label：base station fluctuation interval symbol "~"
	 */
	private JLabel BSSign;
	/**
	 * Label：user fluctuation interval symbol "~"
	 */
	private JLabel userSign;
	/**
	 * Label：Base station fluctuation range
	 */
	private JLabel BSWaveIntervalLabel;
	/**
	 * Text Field：maximum value of user's popularity fluctuation interval
	 */
	private JTextField userMinWaveInterval;
	/**
	 * Text Field： maximum value of user's popularity fluctuation interval
	 */
	private JTextField userMaxWaveInterval;
	/**
	 * Label：User's fluctuation range
	 */
	private JLabel userWaveIntervalLabel;
	/**
	 * Label：amount of users
	 */
	private JLabel userAmountLabel;
	/**
	 * Text Field：amount of users
	 */
	private JTextField userAmountText;
	
	private JCheckBox knapsackAlgorithm;
	private JCheckBox greedyAlgorithm;
	private JCheckBox qLearningAlgotirhm;
	private JCheckBox lruAlgorithm;
	private JCheckBox lfuAlgorithm;
	private JComboBox<Integer> timeSlices;
	private JPanel timeSlicesPanel;
	
	/**
	 * Judge--wireless network
	 */
	private Boolean network;
	/**
	 * Judge--user
	 */
	private Boolean user;
	/**
	 * Judge--run
	 */
	private Boolean running;

	/**
	 * Name
	 */
	public static final String addBSName = "Add BS";
	/**
	 * Name
	 */
	public static final String cancelAddBSName = "Cancel add";
	/**
	 * Name
	 */
	public static final String addBSRandomlyname = "Generate BS randomly";
	/**
	 * Name
	 */
	public static final String addUserName = "Add user";
	/**
	 * Name
	 */
	public static final String runName = "Run";
	/**
	 * Name
	 */
	public static final String BSWaveIntervalName = "Fluctuation：";
	/**
	 * Name
	 */
	public static final String userWaveIntervalName = "Fluctuation：";
	/**
	 * Log
	 */
	private static Logger logger = Logger.getLogger(Operate.class);


	public Operate() {
		initial();
		listenerAdd();
	}

	/**
	 * Add monitor
	 */
	public void listenerAdd() {
		MyActionListener myActionListener = new MyActionListener();
		addBS.addActionListener(myActionListener);
		addBSRandomly.addActionListener(myActionListener);
		addUser.addActionListener(myActionListener);
		run.addActionListener(myActionListener);
		generateChart.addActionListener(myActionListener);
	}

	private class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(addBS)) {
				addBS();
			} else if (e.getSource().equals(addBSRandomly)) {
				addBSRandomly();
			} else if (e.getSource().equals(addUser)) {
				addUser();
			} else if (e.getSource().equals(run)) {
				if(!controller.getWirelessNetworkGroup().BS.isNull() && controller.getWirelessNetworkGroup().BS.containsNetwork() && controller.getUsers().getSimpleUsers()!=null && controller.getUsers().getSimpleUsersAmount()!=0){
					run();
					move();		
				}else{
					JOptionPane.showMessageDialog(null, "Please add base station and user first.", "Prompt",JOptionPane.INFORMATION_MESSAGE);
				}
			}else if(e.getSource().equals(generateChart)){
//				new LineChart(controller.getResultDataList());
				
				new TimeLineChart();
//				new NumberLineChart();
				new DelayLineChart();
				new TrafficLoadLineChart();
//				
				
//				new TimeAndHitRateLineChart();
				
//				new QLearningRateLineChart();
				new ResultDateWiriter().write();
//				new QLearningRateAndQLineChart();
			}
		}
	}
	
	public void initial() {
				this.setBorder(BorderFactory.createTitledBorder(getBorder(), "Operate"));
				this.setLayout(new GridLayout(18, 0, 0, 3));

				{
					// Base station radius
					radiusLabel = new JLabel("Radius:");
					radiusText = new JTextField();
					Radius = new JPanel();
					Radius.add(radiusLabel);
					Radius.add(radiusText);
					Radius.setLayout(new GridLayout(0, 2, 0, 3));
					radiusText.setText("200");
				}
				
				{
					// Base station Cache size
					cacheSizeLabel = new JLabel("Cache size(GB):");
					cacheSizeText = new JTextField();
					Cache = new JPanel();
					Cache.add(cacheSizeLabel);
					Cache.add(cacheSizeText);
					Cache.setLayout(new GridLayout(0, 2, 0, 3));
					cacheSizeText.setText("0.2");
				}
				
				{
					addBS = new JButton(addBSName);
					addBSRandomly = new JButton(addBSRandomlyname);
				}
				
				{
					// Base station fluctuation range
					BSMinWaveInterval = new JTextField();
					BSMaxWaveInterval = new JTextField();
					BSSign = new JLabel(" ~");
					BSWaveIntervalLabel = new JLabel(BSWaveIntervalName);
					WaveInterval_BS = new JPanel();
					JPanel WaveInterval_BS_children = new JPanel();
					WaveInterval_BS.add(BSWaveIntervalLabel);
					WaveInterval_BS_children.add(BSMinWaveInterval);
					WaveInterval_BS_children.add(BSSign);
					WaveInterval_BS_children.add(BSMaxWaveInterval);
					WaveInterval_BS.add(WaveInterval_BS_children);
					WaveInterval_BS_children.setLayout(new GridLayout(0, 3, 0, 0));
					WaveInterval_BS.setLayout(new GridLayout(0, 2, 0, 0));
					BSMinWaveInterval.setText("0.9");
					BSMaxWaveInterval.setText("1.1");
				}
				
				{
					// amount of users
					userAmountLabel = new JLabel("Number:");
					userAmountText = new JTextField();
					User = new JPanel();
					User.add(userAmountLabel);
					User.add(userAmountText);
					User.setLayout(new GridLayout(0, 2, 0, 3));
					userAmountText.setText("500");
				}
			
				{
					// User's fluctuation range
					userMinWaveInterval = new JTextField();
					userMaxWaveInterval = new JTextField();
					userSign = new JLabel(" ~");
					userWaveIntervalLabel = new JLabel(userWaveIntervalName);
					WaveInterval_User = new JPanel();
					JPanel WaveInterval_User_Children = new JPanel();
					WaveInterval_User.add(userWaveIntervalLabel);
					WaveInterval_User_Children.add(userMinWaveInterval);
					WaveInterval_User_Children.add(userSign);
					WaveInterval_User_Children.add(userMaxWaveInterval);
					WaveInterval_User.add(WaveInterval_User_Children);
					WaveInterval_User_Children.setLayout(new GridLayout(0, 3, 0, 0));
					WaveInterval_User.setLayout(new GridLayout(0, 2, 0, 0));
					userMinWaveInterval.setText("0.9");
					userMaxWaveInterval.setText("1.1");
				}
				
				{
					addUser = new JButton(addUserName);
				}
				
				{
					timeSlicesPanel = new JPanel();
					timeSlices = new JComboBox<Integer>();
					for(int i =0 ; i<Parameter.TimeSlicesMaxNumber ;i++){
						timeSlices.addItem(i+1);
					}
					timeSlices.setSelectedIndex(19);
					timeSlicesPanel.add(new JLabel("Time Slices Number :"));
					timeSlicesPanel.add(timeSlices);
				}
				
				{
					knapsackAlgorithm = new JCheckBox();
					greedyAlgorithm = new JCheckBox();
					qLearningAlgotirhm = new JCheckBox();
					lruAlgorithm = new JCheckBox();
					lfuAlgorithm = new JCheckBox();
					qLearningAlgotirhm.setText("QLearning");
					knapsackAlgorithm.setText("Knaspsack");
					greedyAlgorithm.setText("Greedy");	
					lruAlgorithm.setText("LRU");
					lfuAlgorithm.setText("LFU");
				}
				
				{
					run = new JButton(runName);
					generateChart = new JButton("Genarate Chart");
				}
				
				{
					this.add(Radius);
					this.add(Cache);
					this.add(addBS);
					this.add(addBSRandomly);
					this.add(User);
					this.add(addUser);
					this.add(timeSlicesPanel);
					this.add(knapsackAlgorithm);
					this.add(greedyAlgorithm);
					this.add(qLearningAlgotirhm);
					this.add(lruAlgorithm);
					this.add(lfuAlgorithm);
					this.add(run);
					this.add(generateChart);
				}
				
				{
					network = false;
					user = false;
					running = false;
				}	
	}
	
	public void addUser(){
		if(running == false && network == false && !controller.getWirelessNetworkGroup().BS.isNull() && controller.getWirelessNetworkGroup().getBSAmount()!=0){
			Thread thread = new Thread(new Runnable() {
				public void run() {
					user = true;		
					if(userAmountText.getText().trim() != null && !userAmountText.getText().trim().equals("")){
						MobilityModelGenerator.generateUser();
						Pretreatment.process();
						controller.appendLog("debug","Add user success！",logger);
						JOptionPane.showMessageDialog(null, "Add user success！", "Prompt",JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "Number of users can not be empty", "Prompt", JOptionPane.ERROR_MESSAGE);
					}
					user = false;		
				}
			});
			thread.start();
		}else{
			JOptionPane.showMessageDialog(null, "No base stations have been added or the last operation is in progress。", "Prompt",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public void addBSRandomly(){
		if(running == false && user == false){
			network = true;
			controller.appendLog("debug","Add base stations randomly...",logger);
			//Clear all base stations
			controller.getWirelessNetworkGroup().BS.clear();;

			//Randomly read a group of base stations
			new RandomBSReader().parserXML();
			//Output
			for(int i =0 ; i<controller.getWirelessNetworkGroup().getBSAmount();i++){
				controller.appendLog("debug","Add base station：Coordinates ("+controller.getWirelessNetworkGroup().BS.getNetwork(i).getLocation().getX()+","+controller.getWirelessNetworkGroup().BS.getNetwork(i).getLocation().getY()+")  Cache size "+controller.getWirelessNetworkGroup().BS.getNetwork(i).getCacheSize(),null);
			}

			network = false;
		}else{
			JOptionPane.showMessageDialog(null, "The last calculation is in progress. Please try again later！", "Prompt",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void addBS(){
		if(running == false && user == false){
			network =true;
			if (addBS.getText().equals(cancelAddBSName)) {
				addBS.setText(addBSName);
				Signal.Button_BS_Click = false;
			} else {
				addBS.setText(cancelAddBSName);
				Signal.Button_BS_Click = true;
			}
			network =false;
		}else{
			JOptionPane.showMessageDialog(null, "The last calculation is in progress. Please try again later！", "Prompt",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void run() {
		Thread processThread = new Thread(new Runnable() {
			public void run() {
				long startTime = System.currentTimeMillis();
				
				running = true;
				Boolean algorithmSelected = false;			
				controller.getResultDataList().clear();	
				
				controller.appendLog("debug","Set the cache in each base station...",logger);
				
				HashMap<Integer, List<MySingleContent>> initialMyHobbyMap = new HashMap<Integer, List<MySingleContent>>();
				SameTypeWirelessNetwork BSs = controller.getWirelessNetworkGroup().BS;
				for(int j =0; j< BSs.getAmount();j++){
					WirelessNetwork wirelessNetwork = BSs.getNetwork(j);
					List<MySingleContent> initialMyHobby = ContentService.copyMyHobby(wirelessNetwork.getContent().getContentList());
					initialMyHobbyMap.put(wirelessNetwork.getNumber(), initialMyHobby);
				}
				
//				System.out.println("初始化以前的流行度分布");
//				Iterator<Integer> itt = initialPopularity.keySet().iterator();
//				while(itt.hasNext()){
//					int key = itt.next();
//					List<SingleContent> mediaOfNet = initialPopularity.get(key);
//					System.out.println("网络"+key+"的流行度分布");
//					for (SingleContent media : mediaOfNet) {
//						System.out.println(media.getName()+"的流行度是:"+media.getAmount());
//					}
//				}
				
				controller.getResultDataList().clear();
				RequestHandler.ndMap .clear();
				
				if (knapsackAlgorithm.isSelected()) {
					algorithmSelected = true;
					controller.getWirelessNetworkGroup().clearAllCache();

					for (int i = 0; i < (Integer) (timeSlices.getSelectedItem()); i++) {
						controller.appendLog("debug","-------------------------------------------------------------------------------------------One day-----------------------------------------------------------------------------------------------------------------------------------",null);
						controller.appendLog("debug","------------------------------------------------------------------------------------Knapsack Algorithm-------------------------------------------------------------------------------------------------------------------------------------",null);
						new KnapsackAlgorithm().setCache();
						ToolKit.printCache(controller);
						process(knapsackAlgorithm.getText(), i);
					}
				}
				
//				System.out.println("背包.运行完后的流行度分布");
//				for(int i =0 ;i<controller.getWirelessNetworkGroup().getBSAmount();i++){
//					WirelessNetwork networks = controller.getWirelessNetworkGroup().BS.getNetwork(i);
//					List<SingleContent> medias = networks.getContent().getContentList();
//					System.out.println("网络"+networks.getNumber()+"的流行度分布");
//					for (SingleContent media : medias) {
//						System.out.println(media.getName()+"的流行度是:"+media.getAmount());
//					}
//				}
//				
				if(greedyAlgorithm.isSelected()) {
					ContentService.resetMyHobby(initialMyHobbyMap,controller.getWirelessNetworkGroup().BS);
					algorithmSelected = true;
					controller.getWirelessNetworkGroup().clearAllCache();

					for(int i=0;i<(Integer)(timeSlices.getSelectedItem());i++){
						System.out.println("__________________时间片"+(i+1)+"____________________________");
						controller.appendLog("debug","-------------------------------------------------------------------------------------Greedy Algorithm-----------------------------------------------------------------------------------------------------------------------------------", null);
						
						new GreedyAlgorithm().setCache();
						ToolKit.printCache(controller);
						process(greedyAlgorithm.getText(),i);	
					}	
				}
				
//				System.out.println("贪心运行完之后，运行完后的流行度分布");
//				for(int i =0 ;i<controller.getWirelessNetworkGroup().getBSAmount();i++){
//					WirelessNetwork networks = controller.getWirelessNetworkGroup().BS.getNetwork(i);
//					List<SingleContent> medias = networks.getContent().getContentList();
//					System.out.println("网络"+networks.getNumber()+"的流行度分布");
//					for (SingleContent media : medias) {
//						System.out.println(media.getName()+"的流行度是:"+media.getAmount());
//					}
//				}
				
				if(qLearningAlgotirhm.isSelected()){
					QLearning q = new QLearning();
					ContentService.resetMyHobby(initialMyHobbyMap,controller.getWirelessNetworkGroup().BS);
					algorithmSelected = true;
					controller.getWirelessNetworkGroup().clearAllCache();


					for(int i=0;i<(Integer)(timeSlices.getSelectedItem());i++){
						controller.appendLog("debug","-------------------------------------------------------------------------------------Q-Learning Algorithm-----------------------------------------------------------------------------------------------------------------------------------", null);
						q.setGamma(0.8f);
						q.setCache();
						ToolKit.printCache(controller);
						process(qLearningAlgotirhm.getText(), i);
						
					}
				}
				
				if(lruAlgorithm.isSelected()){
					LRUAlgorithm lru = new LRUAlgorithm();
					ContentService.resetMyHobby(initialMyHobbyMap,controller.getWirelessNetworkGroup().BS);
					algorithmSelected = true;
					controller.getWirelessNetworkGroup().clearAllCache();


					for(int i=0;i<(Integer)(timeSlices.getSelectedItem());i++){						
						controller.appendLog(null,"-------------------------------------------------------------------------------------LRU Algorithm-----------------------------------------------------------------------------------------------------------------------------------", null);
						ToolKit.printCache(controller);
						controller.appendLog("debug","Concentrate on the request...",logger);
						controller.getRequestHandler().processRequestRealTime(lru,"Lru", i);
					}
				}
				
				
				if(lfuAlgorithm.isSelected()){
					LFUAlgorithm lfu = new LFUAlgorithm();
					ContentService.resetMyHobby(initialMyHobbyMap,controller.getWirelessNetworkGroup().BS);
					algorithmSelected = true;
					controller.getWirelessNetworkGroup().clearAllCache();


					for(int i=0;i<(Integer)(timeSlices.getSelectedItem());i++){
						controller.appendLog(null,"-------------------------------------------------------------------------------------LFU Algorithm-----------------------------------------------------------------------------------------------------------------------------------", null);
						ToolKit.printCache(controller);
						controller.appendLog("debug","Concentrate on the request...",logger);
						controller.getRequestHandler().processRequestRealTime(lfu,"Lfu", i);
					}
				}

				if(algorithmSelected == true){
					controller.appendLog(null,"-------------------------------------------------------------------------------------------Over-----------------------------------------------------------------------------------------------------------------------------------", null);
					Iterator<String> it  = controller.getResultDataList().keySet().iterator();
					while(it.hasNext()){
						String key = it.next();
						List<Data> dataList = controller.getResultDataList().get(key);
						for(int i = 0 ;i < dataList.size();i++){
							controller.appendLog("debug",key+"'s hit rate on "+dataList.get(i).getTimeSlice()+" time slice :"+dataList.get(i).getHitRate(), logger);
						}
					}	
					long endTime = System.currentTimeMillis();    //获取结束时间

					System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
					Runtime runtime = Runtime.getRuntime();
					System.out.println(runtime.totalMemory() - runtime.freeMemory());
				
					
					controller.appendLog("debug","Congratulations, successful operation!!",logger);
					JOptionPane.showMessageDialog(null, "Successful operation！","Prompt", JOptionPane.INFORMATION_MESSAGE);
					
				}else{
					JOptionPane.showMessageDialog(null, "Please select one or several algorithms first！", "Prompt",JOptionPane.INFORMATION_MESSAGE);
				}
				running = false;
				
			}
		});
		processThread.start();
	}
	
	public void process(String algorithm, int times){	
		controller.appendLog("debug","Concentrate on the request...",logger);
		
		controller.getRequestHandler().processRequestOneTime(algorithm,times);
	}
	
	
	public void move() {
		// 用户移动线程
		Thread moveThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					for(int i =0;i< controller.getUsers().getSimpleUsers().size();i++){
						controller.getUsers().getSimpleUsers().get(i).move();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		moveThread.start();
	}


	public JButton getAddBS() {
		return addBS;
	}

	public void setAddBS(JButton addBS) {
		this.addBS = addBS;
	}

	public JButton getAddBSRandomly() {
		return addBSRandomly;
	}

	public void setAddBSRandomly(JButton addBSRandomly) {
		this.addBSRandomly = addBSRandomly;
	}

	public JButton getAddUser() {
		return addUser;
	}

	public void setAddUser(JButton addUser) {
		this.addUser = addUser;
	}

	public JButton getRun() {
		return run;
	}

	public void setRun(JButton run) {
		this.run = run;
	}

	public JFrame getJframe() {
		return jframe;
	}

	public void setJframe(JFrame jframe) {
		this.jframe = jframe;
	}

	public void setController(Controller controller) {
		this.controller = controller;
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

	public JLabel getCacheSizeLabel() {
		return cacheSizeLabel;
	}

	public void setCacheSizeLabel(JLabel cacheSizeLabel) {
		this.cacheSizeLabel = cacheSizeLabel;
	}

	public JTextField getCacheSizeText() {
		return cacheSizeText;
	}

	public void setCacheSizeText(JTextField cacheSizeText) {
		this.cacheSizeText = cacheSizeText;
	}

	public void setUserWaveIntervalLabel(JLabel userWaveIntervalLabel) {
		this.userWaveIntervalLabel = userWaveIntervalLabel;
	}

	public JLabel getUserAmountLabel() {
		return userAmountLabel;
	}

	public void setUserAmountLabel(JLabel userAmountLabel) {
		this.userAmountLabel = userAmountLabel;
	}

	public JTextField getUserAmountText() {
		return userAmountText;
	}

	public void setUserAmountText(JTextField userAmountText) {
		this.userAmountText = userAmountText;
	}

	public Boolean getNetwork() {
		return network;
	}

	public void setNetwork(Boolean network) {
		this.network = network;
	}

	public Boolean getUser() {
		return user;
	}

	public void setUser(Boolean user) {
		this.user = user;
	}

	public Boolean getRunning() {
		return running;
	}

	public void setRunning(Boolean running) {
		this.running = running;
	}

	public JComboBox<Integer> getTimeSlices() {
		return timeSlices;
	}

	public void setTimeSlices(JComboBox<Integer> timeSlices) {
		this.timeSlices = timeSlices;
	}
	
}
