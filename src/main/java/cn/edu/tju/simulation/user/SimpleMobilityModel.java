package cn.edu.tju.simulation.user;

import java.awt.geom.Point2D;
import java.util.Random;

import cn.edu.tju.simulation.content.LocalHobby;
import cn.edu.tju.simulation.state.State;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * Simple user
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class SimpleMobilityModel extends MobilityModel{
	@Override
	public void move() {
		if(Math.random()<=0.5){
			//假设每次移动最多10米
			int distance = 10;
			double angle =  Math.random()*Math.PI*2;
			double x = this.location.getX() + Math.ceil(Math.cos(angle)*distance);
			double y = this.location.getY()+Math.ceil(Math.sin(angle)*distance);
			checkLocationToAdjustNetwork(x,y);
		}
	}
	
	@Override
	public State generateState() {
		if(Math.random() < 0.6){
			State state = null;
			Random r = new Random();
			int temp = r.nextInt(sumOfPopularity);
			for(int k =0;k<ratio.size();k++){
				if(k!=0){
					if(temp<=ratio.get(k) && temp>ratio.get(k-1)){
						state = new State(this, content.getContentList().get(k));
						break;
					}
				}else{
					if(temp<=ratio.get(k)){
						state = new State(this, content.getContentList().get(k));
						break;
					}
				}
			}
			return state;
		}else{
			return null;
		}
	}
	
	public SimpleMobilityModel(int ID,Point2D.Double location,WirelessNetwork network){
		this.ID = ID;
		this.location = location;
		this.distance = Point2D.distance(location.getX(), location.getY(), network.getLocation().getX(), network.getLocation().getY());
		this.wirelessNetwork = network;
		this.content = new LocalHobby();
	}
}
