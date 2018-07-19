package cn.edu.tju.simulation.user;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.tju.simulation.cache.Query;
import cn.edu.tju.simulation.content.CachingSingleContent;
import cn.edu.tju.simulation.content.MyContent;
import cn.edu.tju.simulation.content.MySingleContent;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.file.Parameter;
import cn.edu.tju.simulation.state.State;
import cn.edu.tju.simulation.wirelessnetwork.SameTypeWirelessNetwork;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public abstract class MobilityModel implements Query{
	/**
	 * User's ID
	 */
	protected int ID;
	/**
	 * User's coordinates
	 */
	protected Point2D.Double location;
	/**
	 * The current user's base station
	 */
	protected WirelessNetwork wirelessNetwork;
	/**
	 * Distance to the current base station
	 */
	protected Double distance;
	/**
	 * The current user's own popularity
	 */
	protected MyContent content;
	
	protected List<Double> requestSingleContentProbabilityDistribution;
	
	protected int sumOfPopularity = 0;
	protected List<Integer> ratio;
	
	
	
	public void checkLocationToAdjustNetwork(double x , double y){
		Point2D.Double networkLocation = this.wirelessNetwork.getLocation();
		//如果脱离当前的网络
		WirelessNetwork nextNetwork = null;
		if(Point2D.distance(networkLocation.getX(), networkLocation.getY(), x, y) > this.wirelessNetwork.getRadius()){
			if(Math.random()<0.1){
				double minDistance = 0;
				SameTypeWirelessNetwork BSs = Controller.getInstance().getWirelessNetworkGroup().BS;
				for(int i = 0; i<BSs.getAmount(); i++){
					WirelessNetwork bs = BSs.getNetwork(i);
					double distance = Point2D.distance(bs.getLocation().getX(), bs.getLocation().getY(), x, y);
					if(distance <= bs.getRadius()){
						if(nextNetwork == null){
							nextNetwork = bs;
							minDistance = distance;
						}else if(distance < minDistance){
							nextNetwork = bs;
							minDistance = distance;
						}
						//进入新的距离
						this.location.setLocation(x, y);
						//重置相互关系
//						System.out.println("用户"+this.ID+"发生了越界,从网络"+this.wirelessNetwork.getNumber()+"去了网络"+bs.number);
						this.wirelessNetwork.getUserOfNetwork().remove(this);
						this.wirelessNetwork = bs;
						this.wirelessNetwork.getUserOfNetwork().add(this);
					}
				}
				if(nextNetwork == null){
					//跳出了信号范围，越界反转
					//不动了
					this.location.setLocation(MobilityModelGenerator.generatePoint(wirelessNetwork));
				}
			}else{
					//用户不想出去了，越界反转
					//不动了
				this.location.setLocation(MobilityModelGenerator.generatePoint(wirelessNetwork));
			}
		}else{
			this.location.setLocation(x, y);
		}
	}
	
	/**
	 * The law of movement
	 * @param location coordinate
	 */
	public abstract void move();
	
	public abstract State generateState();
	
	public void  fluctuatePopularity(){
		this.content.fluctuatePopularity(Parameter.UserMinWaveInterval, Parameter.UserMaxWaveInterval, this.wirelessNetwork.getContent().getContentList());
		generateProbabilityOfSingleContent();
	}
	
	public void generateProbabilityOfSingleContent(){
		if(this.ratio != null){
			this.ratio.clear();
		}else{
			this.ratio = new ArrayList<Integer>();
		}
		
		sumOfPopularity = 0;//The total popularity
		//Calculate the total popularity
		Iterator<MySingleContent> it = this.content.getContentList().iterator();
		
		while(it.hasNext()){
			sumOfPopularity += it.next().getMyPopularity();
		}
		
		for (int j =0;j<this.content.getContentList().size();j++) {
			MySingleContent singleContent = content.getContentList().get(j);
			if(j!=0){
				ratio.add((singleContent.getMyPopularity()+ratio.get(j-1)));
			}else if(j==0){
				ratio.add(singleContent.getMyPopularity());
			}
		}
		
	}
	
	public Boolean query(MySingleContent singleContent){
		Boolean hit = false;
		if (this.wirelessNetwork.getCacheContent() != null) {
			Iterator<CachingSingleContent> it = this.wirelessNetwork.getCacheContent().iterator();
			while(it.hasNext()){
				CachingSingleContent cachingSingleContent = it.next();
				if(cachingSingleContent.getName().equals(singleContent.getName())){
					hit = true;
					if(cachingSingleContent.isDownLoad()){
						if(!cachingSingleContent.getTimeSlotNumberMapUser().keySet().contains(this)){
//							System.out.println("之前就有用户在下载，但现在是新的用户请求，所以要加到表里");
							cachingSingleContent.addNewUserToTimeSlotNumberMap(this, singleContent.getTimeSlotNumber());
							this.wirelessNetwork.addHitAmount();
						}else{
//							System.out.println("这是一个用户维持的请求，不计数");
						}
					}else{
						this.wirelessNetwork.addHitAmount();
						cachingSingleContent.addNewUserToTimeSlotNumberMap(this, singleContent.getTimeSlotNumber());
					}
					return true;
				}
			}
			if(!hit){
				this.wirelessNetwork.addRequestAmount();
			}
			return false;
		}else{
			return false;
		}
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Point2D.Double getLocation() {
		return location;
	}

	public void setLocation(Point2D.Double location) {
		this.location = location;
	}

	public WirelessNetwork getWirelessNetwork() {
		return wirelessNetwork;
	}

	public void setWirelessNetwork(WirelessNetwork wirelessNetwork) {
		this.wirelessNetwork = wirelessNetwork;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}


	public MyContent getContent() {
		return content;
	}

	public void setContent(MyContent content) {
		this.content = content;
	}

	public List<Double> getRequestSingleContentProbabilityDistribution() {
		return requestSingleContentProbabilityDistribution;
	}

	public void setRequestSingleContentProbabilityDistribution(
			List<Double> requestSingleContentProbabilityDistribution) {
		this.requestSingleContentProbabilityDistribution = requestSingleContentProbabilityDistribution;
	}
	
	
		
}
