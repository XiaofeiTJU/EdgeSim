package cn.edu.tju.simulation.wirelessnetwork;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

import cn.edu.tju.simulation.content.CachingSingleContent;
import cn.edu.tju.simulation.content.LocalHobby;
import cn.edu.tju.simulation.state.State;
import cn.edu.tju.simulation.user.MobilityModel;

/**
 * Base station
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class BaseStation extends WirelessNetwork{

	@Override
	public int countUserOfNetwork() {
		//The size of the user's collection in the current network
		return userOfNetwork.size();
	}

	/**
	 * Generate a base station with a cache
	 * @param number ID of base station
	 * @param location The midpoint of the base station
	 */
	public BaseStation(int number,Point2D.Double location,Boolean Cache,long CacheSize,int Radius){
		this.hasCache =Cache;
		if(this.hasCache == true){
			cacheContent = new LinkedList<CachingSingleContent>();
		}else{
			cacheContent =null;
		}
		this.cacheRequestAmount = 0;
		this.hitAmount = 0;
		this.number = number;
		this.location = location;
		this.type = "BS";
		this.cacheSize = CacheSize;
		this.radius = Radius;
		this.content = new LocalHobby();
		this.userOfNetwork = new ArrayList<MobilityModel>();
		this.relationalWirelessNetwork = new RelationalWirelessNetwork();
		this.statesQueue = new ArrayList<State>();
	}
	
	public BaseStation(Point2D.Double location,Boolean Cache,long CacheSize,int Radius){
		this.hasCache =Cache;
		if(this.hasCache == true){
			cacheContent = new LinkedList<CachingSingleContent>();
		}else{
			cacheContent =null;
		}
		this.number = -1;
		this.cacheRequestAmount = 0;
		this.hitAmount = 0;
		this.location = location;
		this.type = "BS";
		this.cacheSize = CacheSize;
		this.radius = Radius;
		this.content = new LocalHobby();
		this.userOfNetwork = new ArrayList<MobilityModel>();
		this.relationalWirelessNetwork = new RelationalWirelessNetwork();
		this.statesQueue = new ArrayList<State>();
	}

}
