package cn.edu.tju.simulation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;


import cn.edu.tju.simulation.content.LocalHobby;
import cn.edu.tju.simulation.content.SingleContent;
import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.data.Data;
import cn.edu.tju.simulation.file.ContentReader;
import cn.edu.tju.simulation.file.ParameterFileReader;
import cn.edu.tju.simulation.handler.RequestHandler;
import cn.edu.tju.simulation.state.StateQueue;
import cn.edu.tju.simulation.swing.log.Log;
import cn.edu.tju.simulation.swing.map.Map;
import cn.edu.tju.simulation.swing.menubar.MenuBar;
import cn.edu.tju.simulation.swing.operator.Operator;
import cn.edu.tju.simulation.user.Users;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetworkGroup;

/**
 * This is a controller class, and at the beginning of the program,
 * it points to all objects that can be used by developers.
 * Developers can access any of the components on the UI, 
 * as well as some parameters and methods for computing.
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 * 
 */
public class Controller {
	/**
	 * The original content, the user and base station's "area popularity" from the property is generated.
	 */
	private LocalHobby originalContent;
	/**
	 * The collection of all wireless networks in the network system, which may contain various types of networks.
	 */
	private WirelessNetworkGroup wirelessNetworkGroup;
	/**
	 * A collection of users, which contains a variety of mobile models.
	 */
	private Users users;
	/**
	 * The processing class for the request. From the beginning of the simulation, to the end, 
	 * it is responsible for "sending" all users' requests to the base station, and counting the hit status of the cache.
	 */
	private RequestHandler requestHandler;
	/**
	 * The menu bar on the UI.
	 */
	private MenuBar menuBar;
	/**
	 * The drawing panel on the UI.
	 */
	private Map mapPanel;
	/**
	 * The operation panel on the  UI
	 */
	private Operator operationPanel;
	/**
	 * The log panel on the UI
	 */
	private Log logPanel;
	/**
	 * The results of the simulation experiment are stored in this property.
 	 */
	private HashMap<String,LinkedList<Data>> resultDataList;
	/**
	 * Instance of the controller
	 */
	private static Controller controller;
	/**
	 * The status queue, which contains the request status of all users. 
	 * It has nothing to do with the time slice, which contains all the states of all users in the entire time stream.
	 */
	private StateQueue stateQueue;
	/**
	 * Log
	 */
	private static Logger logger = Logger.getLogger(Controller.class);
	
	public Controller(){
		initial();
	}
	
	/**
	 * Associate all the panels with the controller and instantiate other properties.
	 * @param menuBar The menu bar on the UI.
	 * @param map The drawing panel on the UI.
	 * @param parameterConfiguration The operation panel on the  UI
	 * @param log The log panel on the UI
	 * @param controller Instance of the controller
	 */
	public void initialController(MenuBar menuBar,Map map,Operator parameterConfiguration,Log log,Controller controller){		
		this.menuBar =menuBar;
		this.mapPanel = map;
		this.operationPanel = parameterConfiguration;
		this.logPanel = log;
		
		Controller.controller = controller;
		
		menuBar.setController(this);
		map.setController(this);
		log.setController(this);
		parameterConfiguration.setController(this);
		
		wirelessNetworkGroup = new WirelessNetworkGroup();
		users = new Users();
		requestHandler = new RequestHandler();
		resultDataList = new HashMap<String, LinkedList<Data>>();
		stateQueue = new StateQueue();
	}
	
	/**
	 * Read a and initialize the popularity and size for the original content.
	 * Popularity conforms to power law.
	 * The size distribution corresponds to the size distribution of the files in the true dataset.
	 */
	public void initial(){
		Boolean readStatus = new ParameterFileReader().read();
		if (readStatus) {
			logger.debug("Load initial parameters successfully����");
		} else {
			logger.debug("Failed to load initial parameters����");
		}
		
		logger.debug("Initialize the popularity according to power law distribution");
		
		this.originalContent = new LocalHobby();
		List<SingleContent> list = new ContentReader().read();
		
		List<SingleLocalHobby> temp = new ArrayList<SingleLocalHobby>();
		for (SingleContent mySingleContent : list) {
			temp.add(new SingleLocalHobby(mySingleContent));
		}
		this.originalContent.setContentList(temp);
	}

	/**
	 * Gets the instance of the controller.
	 * @return Instance of the controller
	 */
	public static Controller getInstance(){
		return controller;
	}
	
	/**
	 * Check if there is a user in the system. If there is, return true, or false.
	 * @return There is a user, return true, or false.
	 */
	public Boolean hasUsers(){
		if(this.users.getSimpleUsersAmount()!=0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Add a log to the log panel and write the log in the log file.
	 * @param type:	"debug" or "info" or "error"
	 * @param text: Contents of the log
	 * @param logger: The log object of the class that is writing this log.
	 * 				  This allows us to find the recorder from the log.
	 */
	public void appendLog(String type,String text,Logger logger){
		this.logPanel.append(text);
		if(logger != null){
			if(type.equals("debug")){
				logger.debug(text);
			}else if(type.equals("info")){
				logger.info(text);
			}else if(type.equals("error")){
				logger.error(text);
			}
		}
	}

	/**
	 * Reset the emulator. Clear all users and base stations.
	 */
	public void clearAll(){
		this.users.getSimpleUsers().clear();
		this.wirelessNetworkGroup.BS.clear();
	}
	
	public List<SingleLocalHobby> getOriginalContentList(){
		return this.originalContent.getContentList();
	}

	public RequestHandler getRequestHandler() {
		return requestHandler;
	}
	
	public Operator getOperationPanel() {
		return operationPanel;
	}

	public Log getLogPanel() {
		return logPanel;
	}
	
	public HashMap<String, LinkedList<Data>> getResultDataList() {
		return resultDataList;
	}

	public Users getUsers() {
		return users;
	}

	public WirelessNetworkGroup getWirelessNetworkGroup() {
		return wirelessNetworkGroup;
	}

	public StateQueue getStateQueue() {
		return stateQueue;
	}

	public LocalHobby getOriginalContent() {
		return originalContent;
	}

	public void setOriginalContent(LocalHobby originalContent) {
		this.originalContent = originalContent;
	}

	
	
}
