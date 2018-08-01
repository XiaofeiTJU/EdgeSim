package cn.edu.tju.simulation.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import cn.edu.tju.simulation.algorithm.RealTimeAlgorithm;
import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.data.Data;
import cn.edu.tju.simulation.state.State;
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
	
	public void processRequestOneTime(String algorithm, int times) {
			//画图用的数据类
			Data data = initResultData(algorithm, times);
			addLocalHobby(controller.getStateQueue().getStateListIterator(times));
			
			//一共有多少个状态
			Iterator<State> it  = controller.getStateQueue().getStateListIterator(times);
			while(it.hasNext()){
				State state = it.next();
				SingleLocalHobby requestedContent = state.getRequestSingleContent();
				MobilityModel user = state.getUser();
				WirelessNetwork network = user.getWirelessNetwork();

				if(user.query(requestedContent)){
					controller.appendLog(null,times+" User "+state.getUser().getID()+" in the network "+network.getNumber()+" request content is "+state.getRequestSingleContent().getName()+"----Hit!",null);
					network.addHitAmount();
				}else if(network.getRelationalWirelessNetwork().getNetworkList().size() != 0){
			    	//view relational network
			    	Set<WirelessNetwork> relationalNetwork = network.getRelationalWirelessNetwork().getNetworkList();
			    	for (WirelessNetwork wirelessnetwork : relationalNetwork) {
			    		//associated base stations are also not
			        	if(!wirelessnetwork.query(requestedContent)){
			        		controller.appendLog(null,times+ " View the associated base station "+wirelessnetwork.getNumber()+" No Hit！",null);
			        		network.addRequestAmount();
			        	}else{
			        	//associated base stations have
			        		controller.appendLog(null,times+ " View the associated base station "+wirelessnetwork.getNumber()+" Hit！",null);
							network.addHitAmount();
							break;
			        	}
			    	}
				}else{
					controller.appendLog(null,times+" User "+state.getUser().getID()+" in the network "+network.getNumber()+" request content is "+state.getRequestSingleContent().getName()+"----No Hit!",null);
	        		network.addRequestAmount();
				}
			}
			
			printResult(algorithm, data);
	}
	
	public void processRequestRealTime(RealTimeAlgorithm rta,String algorithm, int times) {
		Data data = initResultData(algorithm, times);
		addLocalHobby(controller.getStateQueue().getStateListIterator(times));
		
			//画图用的数据类			
			Iterator<State> it  = controller.getStateQueue().getStateListIterator(times);
			while(it.hasNext()){
				State state = it.next();
				SingleLocalHobby  requestedContent = state.getRequestSingleContent();
				MobilityModel user = state.getUser();
				WirelessNetwork network = user.getWirelessNetwork();
				
				if(user.query(requestedContent)){
					controller.appendLog(null,"User "+state.getUser().getID()+" in the network "+network.getNumber()+" request content is "+state.getRequestSingleContent().getName()+"----hit!",null);
					network.addHitAmount();
				}else if(network.getRelationalWirelessNetwork().getNetworkList().size() != 0){
					//查看关系网络
			    	Set<WirelessNetwork> relationalNetwork = network.getRelationalWirelessNetwork().getNetworkList();
			    	for (WirelessNetwork wirelessnetwork : relationalNetwork) {
			    		//associated base stations are also not
			        	if(!wirelessnetwork.query(requestedContent)){
			        		network.addRequestAmount();
							controller.appendLog(null,"User "+state.getUser().getID()+" in the network "+network.getNumber()+" request content is "+state.getRequestSingleContent().getName()+"----No hit!",null);
			        	}else{
			        	//associated base stations have
			        		controller.appendLog(null,"View the associated base station "+wirelessnetwork.getNumber()+" Hit！",null);
			        		network.addHitAmount();
			        		break;
			        	}
			    	}
				}else{
					controller.appendLog(null,times+" User "+state.getUser().getID()+" in the network "+network.getNumber()+" request content is "+state.getRequestSingleContent().getName()+"----No Hit!",null);
	        		network.addRequestAmount();
				}
				rta.setCache(network, state.getRequestSingleContent());
			}
			printResult(algorithm, data);
	}
	
	
	public Data initResultData(String algorithm, int times){
		if(times ==0){
			LinkedList<Data> dataList = new LinkedList<Data>();
			controller.getResultDataList().put(algorithm, dataList);
		}
		
		for(int j =0;j<BSs.getAmount();j++){
			WirelessNetwork network = BSs.getNetwork(j);
			network.resetAmountOfRequestAndHits();
			network.getContent().sortByHobby();
		}
		
		return new Data(times);
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
		controller.appendLog(null,"A total of "+amount+" saving delay "+data.getSaveTime(),null);

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
