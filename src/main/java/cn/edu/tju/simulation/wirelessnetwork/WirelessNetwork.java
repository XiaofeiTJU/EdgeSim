package cn.edu.tju.simulation.wirelessnetwork;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

import cn.edu.tju.simulation.cache.Cache;
import cn.edu.tju.simulation.cache.Query;
import cn.edu.tju.simulation.content.ContentService;
import cn.edu.tju.simulation.content.LocalHobby;
import cn.edu.tju.simulation.content.SingleContent;
import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.file.Parameter;
import cn.edu.tju.simulation.state.State;
import cn.edu.tju.simulation.user.MobilityModel;

/**
 * Wireless network
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public abstract class WirelessNetwork extends Cache implements Query{
	/**
	 * Number of wireless networks
	 */
	public int number;
	
	/**
	 * Central coordinates of wireless networks
	 */
	public Point2D.Double location;
	/**
	 * Radius of the wireless network
	 */
	public int radius;

	/**
	 * The type of wireless network
	 */
	public String type;
	
	/**
	 * A collection of users that can connect to the current network
	 */
	public List<MobilityModel> userOfNetwork;
	/**
	 * Adjacent network collection
	 */
	public RelationalWirelessNetwork relationalWirelessNetwork ;
	/**
	 * Status queues within the network
	 */
	public List<State> statesQueue;
	/**
	 * Network's own popularity
	 */
	public LocalHobby content;	
	
	public abstract int countUserOfNetwork();
	
	public List<SingleLocalHobby> getCanBeCachedContent(){
		return ContentService.sortByHobby(this.content.getContentList());
	}
	
	public  void clearCache(){
		if(cacheContent != null){
			this.cacheContent.clear();
			this.cacheRequestAmount = 0;
			this.hitAmount = 0;	
		}
	}
	
	public void addHobbyByRequestContent(SingleContent isc){
		Iterator<SingleLocalHobby> it = this.content.getContentList().iterator();
		while(it.hasNext()){
			SingleLocalHobby mc = it.next();
			if(mc.getSingleContent() == isc){
				mc.addLocalHobbyValue();
				break;
			}
		}
	}

	public 	Boolean query(SingleLocalHobby singleContent){
		return dealQuery(this, singleContent);
	}
	
	public void  fluctuatePopularity(){
		this.content.fluctuatePopularity(Parameter.BSMinWaveInterval, Parameter.BSMaxWaveInterval, Controller.getInstance().getOriginalContentList());
	}
	
	public int getNumber() {
		return number;
	}
	public Point2D.Double getLocation() {
		return location;
	}
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getType() {
		return type;
	}
	public List<MobilityModel> getUserOfNetwork() {
		return userOfNetwork;
	}

	public List<State> getStatesQueue() {
		return statesQueue;
	}

	public void setStatesQueue(List<State> statesQueue) {
		this.statesQueue = statesQueue;
	}

	public LocalHobby getContent() {
		return content;
	}

	public void setContent(LocalHobby content) {
		this.content = content;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setLocation(Point2D.Double location) {
		this.location = location;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUserOfNetwork(List<MobilityModel> inwireless_simpleuser) {
		this.userOfNetwork = inwireless_simpleuser;
	}

	public RelationalWirelessNetwork getRelationalWirelessNetwork() {
		return relationalWirelessNetwork;
	}

	public void setRelationalWirelessNetwork(
			RelationalWirelessNetwork relationalWirelessNetwork) {
		this.relationalWirelessNetwork = relationalWirelessNetwork;
	}
	
	
}
