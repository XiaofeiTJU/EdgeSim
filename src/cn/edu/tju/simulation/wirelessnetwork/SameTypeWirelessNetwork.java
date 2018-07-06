package cn.edu.tju.simulation.wirelessnetwork;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Class of the same kind of wireless network
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class SameTypeWirelessNetwork implements AddWirelessNetworkInterface{
	/**
	 * collection of wireless networks
	 */
	private List<WirelessNetwork> networks;
	/**
	 * Type of wireless network
	 */
	private String type;
	/**
	 * The color of this kind of wireless network
	 */
	private Color color;
	/**
	 * Log
	 */
	private static Logger logger = Logger.getLogger(SameTypeWirelessNetwork.class);
	
	public SameTypeWirelessNetwork (){
		this.networks.clear();
	}

	public void clearAllCache(){
		for(int i = 0 ;i<this.networks.size();i++){
			this.networks.get(i).clearCache();
		}
	}
	
	/**
	 * @param type Type of network
	 */
	public SameTypeWirelessNetwork (String type){
		this.type = type;
		this.networks = new ArrayList<WirelessNetwork>();
	}
	/**
	 * Generating a collection of the same network
	 * @param amount The number of networks
	 * @param type Type of network
	 * @param color The color displayed
	 */
	public SameTypeWirelessNetwork(int amount,String type,Color color){
		this.type = type;
		this.color = color;
		this.networks = new ArrayList<WirelessNetwork>();
	}

	//考虑问题：如果加AP呢？
	public void addWirelessNetwork(WirelessNetwork noAddedNetwork){
		//Configure bs number
		if(noAddedNetwork.getNumber() ==-1){
			if(networks.size()!=0){
				noAddedNetwork.setNumber(networks.get(networks.size()-1).getNumber()+1);
			}else{
				noAddedNetwork.setNumber(0);
			}
		}
		
		//最强关联性
//		//Marking between base stations
//		if(networks.size()!=0){
//			for (WirelessNetwork addedNetwork : networks) {
//				if(WirelessNetworkHandler.relationalNetworkHandler(noAddedNetwork, addedNetwork)){
//					List<WirelessNetwork> addedRelationalNetwork = addedNetwork.getRelationalNetwork();
//					if(addedRelationalNetwork.size()!=0){
//						for(int i =0 ;i<addedRelationalNetwork.size();i++){
//							WirelessNetwork relationalNetwork_wn = addedRelationalNetwork.get(i);
//							if(!noAddedNetwork.getRelationalNetwork().contains(relationalNetwork_wn)){
//								noAddedNetwork.getRelationalNetwork().add(relationalNetwork_wn);
//								if(!relationalNetwork_wn.getRelationalNetwork().contains(noAddedNetwork)){
//									relationalNetwork_wn.relationalNetwork.add(noAddedNetwork);
//								}
//							}
//						}
//					}
//					if(!noAddedNetwork.getRelationalNetwork().contains(addedNetwork)){
//						noAddedNetwork.relationalNetwork.add(addedNetwork);
//					}
//					if(!addedNetwork.getRelationalNetwork().contains(noAddedNetwork)){
//						addedNetwork.relationalNetwork.add(noAddedNetwork);
//					}
//
//				}
//			}
//			
//			
//			List<WirelessNetwork> noAddedRelationalNetwork = noAddedNetwork.getRelationalNetwork();
//			for (WirelessNetwork wirelessNetwork : noAddedRelationalNetwork) {
//				for(int i=0;i<noAddedRelationalNetwork.size();i++){
//					WirelessNetwork network = noAddedRelationalNetwork.get(i);
//					if(!wirelessNetwork.getRelationalNetwork().contains(network) && network != wirelessNetwork){
//						wirelessNetwork.getRelationalNetwork().add(network);
//					}
//				}
//			}
//			
//		}
		
		if(networks.size()!=0){
			for (WirelessNetwork addedNetwork : networks) {
				addedNetwork.getRelationalWirelessNetwork().tryToAssociate(noAddedNetwork, addedNetwork);
			}
		}
		
		
		this.networks.add(noAddedNetwork);
	}
	
	public void deleteWirelessNetwork(int index){
		this.networks.remove(index);
	}
	
	/**
	 * Reset network relationship
	 */
	public void resetNetowk(){
		List<WirelessNetwork> networksTemp = new ArrayList<WirelessNetwork>();
		networksTemp.addAll(networks);
		networks.clear();
		for (WirelessNetwork network : networksTemp) {
			network.getRelationalWirelessNetwork().clear();
			this.addWirelessNetwork(network);
			logger.info("重新建立关系网络");
		}
		networksTemp.clear();
	}
	
	public WirelessNetwork getNetwork(int index){
		return this.networks.get(index);
	}
	
	public void clear(){
		this.networks.clear();
	}
	
	public void removeNetwork(WirelessNetwork network){
		this.networks.remove(network);
		resetNetowk();
	}
	
	public void removeNetwork(int index){
		this.networks.remove(index);
		resetNetowk();
	}
	
	public Boolean isNull(){
		if(this.networks == null){
			return true;
		}else{
			return false;
		}
	}
	
	public Iterator<WirelessNetwork> getWNetworkIterator(){
		return this.networks.iterator();
	}
	
	public Boolean containsNetwork(){
		if(this.networks.size()!=0){
			return true;
		}else{
			return false;
		}
	}
	
	public Iterator<WirelessNetwork> getIterator(){
		return this.networks.iterator();
	}
	
	public int getAmount() {
		return this.networks.size();
	}

	public void setAmount(int amount) {
	}

}
