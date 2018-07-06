package cn.edu.tju.simulation.wirelessnetwork;

import java.util.HashMap;
import java.util.Set;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class RelationalWirelessNetwork {
	private HashMap<WirelessNetwork,Double> relationalNetworkMap ;
	
	public RelationalWirelessNetwork(){
		this.relationalNetworkMap = new HashMap<WirelessNetwork, Double>();
	}
	
	public void addNetwork(WirelessNetwork network,Double distance){
		this.relationalNetworkMap.put(network, distance);
	}
	
	public void clear(){
		this.relationalNetworkMap.clear();
	}
	
	public Set<WirelessNetwork> getNetworkList(){
		return this.relationalNetworkMap.keySet();
	}
	
	//如果基站之间距离小于两个基站的最小半径，则互相标记
	public void tryToAssociate(WirelessNetwork noAddedNetwork, WirelessNetwork thisNetwork){
		double x = noAddedNetwork.getLocation().getX();
		double y = noAddedNetwork.getLocation().getY();
		double x2 = thisNetwork.getLocation().getX();
		double y2 = thisNetwork.getLocation().getY();
		double distance = Math.sqrt(Math.pow((x2 - x), 2) + Math.pow((y2 - y), 2));
		if(noAddedNetwork.getRadius() >= thisNetwork.getRadius()){
			if (distance <= noAddedNetwork.getRadius()) {
				this.relationalNetworkMap.put(noAddedNetwork, distance);
				noAddedNetwork.getRelationalWirelessNetwork().addNetwork(thisNetwork, distance);
			}
		}else{
			if (distance <= thisNetwork.getRadius()) {
				this.relationalNetworkMap.put(noAddedNetwork, distance);
			}
		}
	}

}
