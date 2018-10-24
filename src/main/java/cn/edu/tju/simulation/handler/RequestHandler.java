package cn.edu.tju.simulation.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.edu.tju.simulation.algorithm.OneTimeAlgorithm;
import cn.edu.tju.simulation.algorithm.RealTimeAlgorithm;
import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.data.Data;
import cn.edu.tju.simulation.state.State;
import cn.edu.tju.simulation.swing.operator.Operator;
import cn.edu.tju.simulation.tool.ToolKit;
import cn.edu.tju.simulation.user.MobilityModel;
import cn.edu.tju.simulation.wirelessnetwork.SameTypeWirelessNetwork;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * The class that handles user requests, with the state flow as the main line
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 */
public class RequestHandler {
	/**
	 * Iterators of state list
	 */
	public Iterator<State> stateIterator;
	/**
	 * Iterator for wireless network collection
	 */
	public Iterator<WirelessNetwork> wirelessNetworkIterator;
	public static HashMap<String,LinkedList<mydata>> ndMap;
	/**
	 * Log
	 */
	private static Logger logger = Logger.getLogger(RequestHandler.class);
	
	/**
	 * Controller
	 */
	private Controller controller;
	
	private SameTypeWirelessNetwork BSs ;
	
	public RequestHandler(){
		this.controller = Controller.getInstance();
		this.BSs = controller.getWirelessNetworkGroup().BS;
		this.ndMap = new HashMap<String, LinkedList<mydata>>();
	}
	
	public class mydata{
		public float hitrate;
		public int hitamount;
		
		public mydata(float hitrate,int hitamount){
			this.hitamount = hitamount;
			this.hitrate = hitrate;
		}
	}
	
	public void processRequest(Object algorithm,String algorithmName, int maxTimes){
		for (int i = 0; i < maxTimes; i++) {
			ToolKit.printCache(controller);
			controller.appendLog("debug","Concentrate on the request...",logger);
			if(OneTimeAlgorithm.class.isAssignableFrom(algorithm.getClass())){
				controller.appendLog("debug","-------------------------------------------------------------------------------------" + algorithmName + " Algorithm-----------------------------------------------------------------------------------------------------------------------------------", null);
				((OneTimeAlgorithm)algorithm).setCache();
			}
			//画图用的数据类
			Data newData = initResultData(algorithmName, i);
			addLocalHobby(controller.getStateQueue().getStateListIterator(i));
			//一共有多少个状态
			Iterator<State> it  = controller.getStateQueue().getStateListIterator(i);
			while(it.hasNext()){
				State state = it.next();
				SingleLocalHobby requestedContent = state.getRequestSingleContent();
				MobilityModel user = state.getUser();
				WirelessNetwork network = user.getWirelessNetwork();
				
				//add latency , this latency is needed by all state
				newData.addLatency(state.getLatency());

				
				if(user.query(requestedContent)){
					controller.appendLog(null,i+" User "+state.getUser().getID()+" in the network "+network.getNumber()+" request content is "+state.getRequestSingleContent().getName()+"----Hit!",null);
					network.addHitAmount();			
	        		newData.addTraffic(requestedContent.getSize());
				}else if(network.getRelationalWirelessNetwork().getNetworkList().size() != 0){
			    	//view relational network
			    	Set<WirelessNetwork> relationalNetwork = network.getRelationalWirelessNetwork().getNetworkList();
			    	for (WirelessNetwork wirelessnetwork : relationalNetwork) {
			    		//associated base stations are also not
			        	if(!wirelessnetwork.query(requestedContent)){
			        		controller.appendLog(null,i+ " View the associated base station "+wirelessnetwork.getNumber()+" No Hit！",null);
			        		network.addRequestAmount();
			        		//add traffic and latency 
			        		newData.addLatency(10);
							
			        	}else{
			        	//associated base stations have
			        		controller.appendLog(null,i+ " View the associated base station "+wirelessnetwork.getNumber()+" Hit！",null);
							network.addHitAmount();
			        		newData.addTraffic(requestedContent.getSize());
							break;
			        	}
			    	}
				}else{
					controller.appendLog(null,i+" User "+state.getUser().getID()+" in the network "+network.getNumber()+" request content is "+state.getRequestSingleContent().getName()+"----No Hit!",null);
	        		network.addRequestAmount();
	        		
	        		//add traffic and latency 
	        		newData.addLatency(10);
				}
				if(RealTimeAlgorithm.class.isAssignableFrom(algorithm.getClass())){
					((RealTimeAlgorithm)algorithm).setCache(network, state.getRequestSingleContent());
				}
			}
			newData.setLatency(newData.getLatency()/controller.getStateQueue().getStateList(i).size());
			printResult(algorithmName, newData);
			
		}
	}	
	
	public Data initResultData(String algorithm, int times){
		Data data = new Data(times);
		if(times ==0){
			LinkedList<Data> dataList = new LinkedList<Data>();
			controller.getResultDataList().put(algorithm, dataList);
		}
		for(int j =0;j<BSs.getAmount();j++){
			WirelessNetwork network = BSs.getNetwork(j);
			network.resetAmountOfRequestAndHits();
			network.getContent().sortByHobby();
		}
		
		return data;
	}

	public void printResult(String algorithm, Data data){
		int amount = 0;
		int hitCount =0;
		//Calculate the hit rate
		for(int i =0;i<BSs.getAmount();i++){
			WirelessNetwork network = BSs.getNetwork(i);
			hitCount = hitCount+network.getHitAmount();
			amount = amount + network.getRequestAmount();
			controller.appendLog(null,"Network "+network.getNumber()+" requests and hits   "+network.getRequestAmount()+" and "+network.getHitAmount(),null);
		}

		float mfloat = (float)((float)hitCount/(float)amount);
		
		controller.appendLog(null,"A total of "+amount+" request  hit "+hitCount+" hit rate is "+mfloat,null);
		controller.appendLog(null,"A total of "+amount+" latency is " + data.getLatency() + " s",null);
		
		data.setHitRate(mfloat);
			
		controller.getResultDataList().get(algorithm).add(data);
	}
	
	public void addLocalHobby(Iterator<State> mIt){
		while(mIt.hasNext()){
			State state = mIt.next();
			SingleLocalHobby requestedContent = state.getRequestSingleContent();
			requestedContent.addRequestedAmount();
			//基站增加该内容的流行度
			state.getNetwork().addHobbyByRequestContent(requestedContent.getSingleContent());
		}
	}
	
}
