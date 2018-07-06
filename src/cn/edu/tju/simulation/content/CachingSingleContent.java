package cn.edu.tju.simulation.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.edu.tju.simulation.user.MobilityModel;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

public class CachingSingleContent {
	
	private InitialSingleContent cachingSingleContent;
	private HashMap<MobilityModel,Integer> timeSlotNumberMapUser;
	private HashMap<WirelessNetwork,Integer> timeSlotNumberMapBS;
	
	public CachingSingleContent(InitialSingleContent cachingSingleContent){
		this.timeSlotNumberMapUser = new HashMap<MobilityModel, Integer>();
		this.timeSlotNumberMapBS = new HashMap<WirelessNetwork, Integer>();
		this.cachingSingleContent = cachingSingleContent;
	}
	
	public String getName(){
		return this.cachingSingleContent.getName();
	}
	
	public long getSize(){
		return this.cachingSingleContent.getSize();
	}
	
	public Boolean canBeRemove(){
			return true;
	}
	
	public Boolean isDownLoad(){
		if(this.timeSlotNumberMapUser.size() != 0 || this.timeSlotNumberMapBS.size() != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 */
	public void reduceTimeSlotNumber(){
		if(isDownLoad()){
			if(this.timeSlotNumberMapUser.size() != 0){
				Iterator<MobilityModel> it = this.timeSlotNumberMapUser.keySet().iterator();
				List<MobilityModel> removeUserList = new ArrayList<MobilityModel>();
				HashMap<MobilityModel,Integer> addMap = new HashMap<MobilityModel, Integer>();
				
				while(it.hasNext()){
					MobilityModel user = it.next();
					int temp = timeSlotNumberMapUser.get(user)-1;
					if(temp == 0){
						removeUserList.add(user);
//						System.out.println("时间片结束了，删除用户"+user.getID());
					}else{
						addMap.put(user,temp);
					}
				}
				
				for(int i = 0 ;i <removeUserList.size() ; i++){
					this.timeSlotNumberMapUser.remove(removeUserList.get(i));
				}
				Iterator<MobilityModel> mIt= addMap.keySet().iterator();
				while(mIt.hasNext()){
					MobilityModel user = mIt.next();
					this.timeSlotNumberMapUser.put(user, addMap.get(user));
				}
			}
			if(this.timeSlotNumberMapBS.size() != 0){
				Iterator<WirelessNetwork> it = this.timeSlotNumberMapBS.keySet().iterator();
				List<WirelessNetwork> removeNetworkList = new ArrayList<WirelessNetwork>();
				HashMap<WirelessNetwork,Integer> addMap = new HashMap<WirelessNetwork, Integer>();
				
				while(it.hasNext()){
					WirelessNetwork network = it.next();
					int temp = timeSlotNumberMapBS.get(network)-1;
					if(temp == 0){
						removeNetworkList.add(network);
//						System.out.println("时间片结束了，删除基站"+network.getNumber());
					}else{
						addMap.put(network,temp);
					}
				}
				
				for(int i = 0 ;i <removeNetworkList.size() ; i++){
					this.timeSlotNumberMapBS.remove(removeNetworkList.get(i));
				}
				Iterator<WirelessNetwork> mIt= addMap.keySet().iterator();
				while(mIt.hasNext()){
					WirelessNetwork network = mIt.next();
					this.timeSlotNumberMapBS.put(network, addMap.get(network));
				}
			}
		}
	}

	public HashMap<WirelessNetwork, Integer> getTimeSlotNumberMapBS() {
		return timeSlotNumberMapBS;
	}

	public void setTimeSlotNumberMapBS(
			HashMap<WirelessNetwork, Integer> timeSlotNumberMapBS) {
		this.timeSlotNumberMapBS = timeSlotNumberMapBS;
	}

	public void addNewUserToTimeSlotNumberMap(MobilityModel user , int TimeSlotNumber){
		this.timeSlotNumberMapUser.put(user, TimeSlotNumber);
	}
	
	public void addNewBSToTimeSlotNumberMap(WirelessNetwork network , int TimeSlotNumber){
		this.timeSlotNumberMapBS.put(network, TimeSlotNumber);
	}
	
	
	public InitialSingleContent getCachingSingleContent() {
		return cachingSingleContent;
	}

	public void setCachingSingleContent(InitialSingleContent cachingSingleContent) {
		this.cachingSingleContent = cachingSingleContent;
	}

	public HashMap<MobilityModel, Integer> getTimeSlotNumberMapUser() {
		return timeSlotNumberMapUser;
	}

	public void setTimeSlotNumberMapUser(
			HashMap<MobilityModel, Integer> timeSlotNumberMapUser) {
		this.timeSlotNumberMapUser = timeSlotNumberMapUser;
	}
	
}
