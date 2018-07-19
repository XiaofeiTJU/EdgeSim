package cn.edu.tju.simulation.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.edu.tju.simulation.algorithm.RealTimeAlgorithm;
import cn.edu.tju.simulation.content.MySingleContent;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.data.Data;
import cn.edu.tju.simulation.file.Parameter;
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
	/**
	 * Log
	 */
	private static Logger logger = Logger.getLogger(RequestHandler.class);
	
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

	public void processRequestOneTime(String algorithm,int times) {
		if(times ==0){
			LinkedList<Data> dataList = new LinkedList<Data>();
			controller.getResultDataList().put(algorithm, dataList);
			
		}
		/**
		 * 赶紧取消注释
		 */
			//画图用的数据类
			Data data = new Data(times);
//			if(times != 0){
//				data.setSaveTime(controller.getResultDataList().get(algorithm).getLast().getSaveTime());
//				data.setSaveTraffic(controller.getResultDataList().get(algorithm).getLast().getSaveTraffic());
//			}
//			
			
			for(int j =0;j<BSs.getAmount();j++){
				WirelessNetwork network = BSs.getNetwork(j);
				network.resetAmountOfRequestAndHits();
				network.getContent().sortByHobby();
			}
			//一共有多少个状态
			int size = controller.getStateQueue().getStateList(times).size();

			if(times <= 11){
				if(!ndMap.keySet().contains(algorithm)){
					ndMap.put(algorithm, new LinkedList<mydata>());
				}
			}
			
			Boolean hit;
			for(int i = 0; i <size;i++)	{
				hit = false;
				State state = controller.getStateQueue().getStateList(times).get(i);
				MySingleContent requestedContent = state.getRequestSingleContent();
				requestedContent.addRequestedAmount();

				//基站增加该内容的流行度
				state.getNetwork().addHobbyByRequestContent(requestedContent.getSingleContent());
				
				MobilityModel user = state.getUser();
				WirelessNetwork network = user.getWirelessNetwork();
				if(user.getWirelessNetwork().query(state.getUser(),requestedContent)){
					controller.appendLog(null,times+" User "+state.getUser().getID()+" in the network "+network.getNumber()+" request content is "+state.getRequestSingleContent().getName()+"----hit!",null);
					
					
					//按时间片的数量，单位时间片节省的时延
//					data.addSaveTime((Parameter.BS_TO_MNO_DELAY +Parameter.MNO_TO_CLOUD_DELAY));
					data.addSaveTraffic(state.getRequestSingleContent().getSize());

					
					
					data.addSaveTime(Parameter.USER_TO_BS_DELAY);
					
					hit =true;
				}else{
					controller.appendLog(null,times+" User "+state.getUser().getID()+" in the network "+network.getNumber()+" request content is "+state.getRequestSingleContent().getName()+"----No hit!",null);
			    	//view relational network
			    	Set<WirelessNetwork> relationalNetwork = network.getRelationalWirelessNetwork().getNetworkList();
			    	for (WirelessNetwork wirelessnetwork : relationalNetwork) {
			    		//associated base stations are also not
			        	if(!wirelessnetwork.query(network,requestedContent)){
			        		controller.appendLog(null,times+ " View the associated base station "+wirelessnetwork.getNumber()+" No Hit！",null);
			        		
			        		//
							data.addSaveTime((Parameter.USER_TO_BS_DELAY+Parameter.BS_TO_MNO_DELAY +Parameter.MNO_TO_CLOUD_DELAY));
	
			        		
			        		
			        	}else{
			        	//associated base stations have
			        		controller.appendLog(null,times+ " View the associated base station "+wirelessnetwork.getNumber()+" Hit！",null);
							hit =true;
			        		network.addHitAmount();
			        		
			        		
			        		
//							data.addSaveTime((Parameter.BS_TO_BS_DELAY+Parameter.BS_TO_MNO_DELAY +Parameter.MNO_TO_CLOUD_DELAY));
							data.addSaveTraffic(state.getRequestSingleContent().getSize());
							
							
							data.addSaveTime((Parameter.BS_TO_BS_DELAY));
							
							
							break;
			        	}
			    	}
				}
				
				if(times <=11){
					if(times == 0 ){
						int amount = 0;
						int hitCount =0;
						//Calculate the hit rate
						for(int j =0;j<BSs.getAmount();j++){
							WirelessNetwork network1 = BSs.getNetwork(j );
							hitCount = hitCount+network1.getHitAmount();
							amount = amount + network1.getRequestAmount();
						}
						float mfloat = (float)((float)hitCount/(float)amount);

						this.ndMap.get(algorithm).add(new mydata(mfloat, hitCount));
					}else{
						if(hit){
							int hitamount = this.ndMap.get(algorithm).getLast().hitamount;
							float mfloat = (float)((float)(hitamount+1)/(float)(this.ndMap.get(algorithm).size()+1));
							this.ndMap.get(algorithm).add(new mydata(mfloat, hitamount+1));
						}else{
							int hitamount = this.ndMap.get(algorithm).getLast().hitamount;
							float mfloat = (float)((float)(hitamount)/(float)(this.ndMap.get(algorithm).size()+1));
							this.ndMap.get(algorithm).add(new mydata(mfloat, hitamount));
						}
					
					}
				}
			}
			
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
			
			
			data.setSaveTime(data.getSaveTime()/size);
			data.setSaveTraffic(data.getSaveTraffic()/size);

			controller.getResultDataList().get(algorithm).add(data);
			
	}
	
	public void processRequestRealTime(RealTimeAlgorithm rta,String algorithm, int times) {
		if(times ==0){
			LinkedList<Data> dataList = new LinkedList<Data>();

			controller.getResultDataList().put(algorithm, dataList);
		}
			//画图用的数据类
			Data data = new Data(times);
			if(controller.getResultDataList().get(algorithm).size() != 0){
				data.setSaveTime(controller.getResultDataList().get(algorithm).getLast().getSaveTime());
				data.setSaveTraffic(controller.getResultDataList().get(algorithm).getLast().getSaveTraffic());

			}
			
			for(int j =0;j<BSs.getAmount();j++){
				WirelessNetwork network = BSs.getNetwork(j);
				network.resetAmountOfRequestAndHits();
				network.getContent().sortByHobby();
			}
			
			
			//一共有多少个状态
			int size = controller.getStateQueue().getStateList(times).size();

			if(times <= 11){
				if(!ndMap.keySet().contains(algorithm)){
					ndMap.put(algorithm, new LinkedList<mydata>());
				}
			}
			
			Boolean hit;
			Iterator<State> it  = controller.getStateQueue().getStateListIterator(times);
			while(it.hasNext()){
				hit = false;
				State state = it.next();
				MySingleContent  requestedContent = state.getRequestSingleContent();
				requestedContent.addRequestedAmount();

				MobilityModel user = state.getUser();
				WirelessNetwork network = user.getWirelessNetwork();
				if(user.getWirelessNetwork().query(state.getUser() , state.getRequestSingleContent())){
					controller.appendLog(null,"User "+state.getUser().getID()+" in the network "+network.getNumber()+" request content is "+state.getRequestSingleContent().getName()+"----hit!",null);
					data.addSaveTime((Parameter.BS_TO_MNO_DELAY +Parameter.MNO_TO_CLOUD_DELAY)/state.getRequestSingleContent().getTimeSlotNumber());
					data.addSaveTraffic(state.getRequestSingleContent().getSize()/state.getRequestSingleContent().getTimeSlotNumber());
					hit = true;
				}else{

					controller.appendLog(null,"User "+state.getUser().getID()+" in the network "+network.getNumber()+" request content is "+state.getRequestSingleContent().getName()+"----No hit!",null);

					//查看关系网络
			    	Set<WirelessNetwork> relationalNetwork = network.getRelationalWirelessNetwork().getNetworkList();
			    	for (WirelessNetwork wirelessnetwork : relationalNetwork) {
			    		//associated base stations are also not
			        	if(!wirelessnetwork.query(network,state.getRequestSingleContent())){
			        		controller.appendLog(null,"View the associated base station "+wirelessnetwork.getNumber()+" No Hit！",null);
			        		data.reduceSaveTime(Parameter.BS_TO_BS_DELAY);
			        	}else{
			        	//associated base stations have
			        		controller.appendLog(null,"View the associated base station "+wirelessnetwork.getNumber()+" Hit！",null);
			        		network.addHitAmount();
							hit = true;
							data.addSaveTraffic(state.getRequestSingleContent().getSize()/state.getRequestSingleContent().getTimeSlotNumber());
			        		data.addSaveTime((Parameter.BS_TO_MNO_DELAY +Parameter.MNO_TO_CLOUD_DELAY)/state.getRequestSingleContent().getTimeSlotNumber());
			        		break;
			        	}
			    	}
				}
				
				if(times <=11){
					if(times == 0 ){
						int amount = 0;
						int hitCount =0;
						//Calculate the hit rate
						for(int j =0;j<BSs.getAmount();j++){
							WirelessNetwork network1 = BSs.getNetwork(j );
							hitCount = hitCount+network1.getHitAmount();
							amount = amount + network1.getRequestAmount();
						}
						float mfloat = (float)((float)hitCount/(float)amount);

						this.ndMap.get(algorithm).add(new mydata(mfloat, hitCount));
					}else{
						if(hit){
							int hitamount = this.ndMap.get(algorithm).getLast().hitamount;
							float mfloat = (float)((float)(hitamount+1)/(float)(this.ndMap.get(algorithm).size()+1));
							this.ndMap.get(algorithm).add(new mydata(mfloat, hitamount+1));
						}else{
							int hitamount = this.ndMap.get(algorithm).getLast().hitamount;
							float mfloat = (float)((float)(hitamount)/(float)(this.ndMap.get(algorithm).size()+1));
							this.ndMap.get(algorithm).add(new mydata(mfloat, hitamount));
						}
					
					}
				}
				
				rta.setCache(network, state.getRequestSingleContent());
			}
			
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
}
