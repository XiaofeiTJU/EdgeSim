package cn.edu.tju.simulation.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.file.Parameter;
import cn.edu.tju.simulation.user.MobilityModel;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class StateQueue {
	private HashMap<Integer,List<State>> stateQueueMap;	
	/**
	 * Log
	 */
	private static Logger logger = Logger.getLogger(StateQueue.class);
	
	public void generateStateQueue(){
		Controller controller = Controller.getInstance();
		controller.appendLog("debug","Create status...",logger);

		stateQueueMap.clear();
		
		for(int i = 0; i < Parameter.TimeSlicesMaxNumber; i++){
			List<State> list = new ArrayList<State>();
			if(i == 0){
				this.stateQueueMap.put(i, controller.getUsers().generateStateList());
			}else{
				List<MobilityModel> simpleUserList = controller.getUsers().getSimpleUsers();
				for(int k = 0 ; k < simpleUserList.size() ; k++){
					State state = simpleUserList.get(k).generateState();
					if(state != null ){
						list.add(state);
					}
				}
				this.stateQueueMap.put(i,list);
			}
		}
	}
	
	public void assignStateToNetwork(int key){
		Controller controller = Controller.getInstance();
		Iterator<WirelessNetwork> it = controller.getWirelessNetworkGroup().BS.getIterator();
		while(it.hasNext()){
			it.next().getStatesQueue().clear();
		}

		//choose one state queue
		List<State> stateList = controller.getStateQueue().getStateList(key);
		
		Iterator<State> mIt = stateList.iterator();
		while(mIt.hasNext()){
			State state = mIt.next();
			state.getUser().getWirelessNetwork().getStatesQueue().add(state);
		}
	}
	
	public StateQueue(){
		this.stateQueueMap = new HashMap<Integer, List<State>>();
	}
	
	public Iterator<State> getStateListIterator(int times){
		return stateQueueMap.get(times).iterator();
	}
	
	public List<State> getStateList(int times){
		return stateQueueMap.get(times);
	}
}
