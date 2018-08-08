package cn.edu.tju.simulation.user;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.wirelessnetwork.SameTypeWirelessNetwork;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * Incoming user id collection, generate user
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class MobilityModelGenerator {
	/**
	 * Generate users randomly
	 * @param number The number of users
	 * @param BSs Collection of base stations
	 * 
	 */
	public static void generateUser(){	
		Controller controller = Controller.getInstance();
		//Clear all users
		controller.getUsers().getSimpleUsers().clear();	
		//Clear all users of network
		for(int i = 0; i<controller.getWirelessNetworkGroup().BS.getAmount();i++){
			controller.getWirelessNetworkGroup().BS.getNetwork(i).getUserOfNetwork().clear();
		}
		
		//Generate Users
		WirelessNetwork network = null;
		Iterator<Integer> it = generateID(Integer.parseInt(controller.getOperationPanel().getUserAmountText().getText())).iterator();
		while(it.hasNext()){
			int ID = it.next();			
			network = chooseNetwork();
			Point2D.Double point = generatePoint(network);
		
			SimpleMobilityModel sm = new SimpleMobilityModel(ID, point,network);
			controller.getUsers().getSimpleUsers().add(sm);
			network.getUserOfNetwork().add(sm);
		}
	}
	
	public static Point2D.Double generatePoint(WirelessNetwork network){
		double angle =  Math.random()*Math.PI*2;

		double x_temp = 0;
		double y_temp = 0;
		double temp = Math.random();
		if(Math.cos(angle)<0){
			x_temp = Math.ceil(network.getRadius()*Math.cos(angle)*Math.sqrt(temp));
		}else{
			x_temp = Math.floor(network.getRadius()*Math.cos(angle)*Math.sqrt(temp));
		}
		if(Math.sin(angle)<0){
			y_temp = Math.ceil(network.getRadius()*Math.sin(angle)*Math.sqrt(temp));
		}else{
			y_temp = Math.floor(network.getRadius()*Math.sin(angle)*Math.sqrt(temp));
		}
		
		double x = network.getLocation().getX()+x_temp;
		double y = network.getLocation().getY()+y_temp;	

		return new Point2D.Double(x,y);
	}
	
	
	//随机选择一个基站
	public static WirelessNetwork chooseNetwork(){
		SameTypeWirelessNetwork BSs = Controller.getInstance().getWirelessNetworkGroup().BS;
		WirelessNetwork network = null;
		//随机选择一个基站
		while(true){
			int a = (int)Math.floor(Math.random()*10);
			if(a<BSs.getAmount()){
				network = BSs.getNetwork(a);
				break;
			}	
		}
		return network;
	}	
	
	/**
	 * Generate a user ID based on the number of users
	 * @param number The number of users
	 * @return
	 */
	public static List<Integer> generateID(int number){
		List<Integer> usersID = new ArrayList<Integer>();
		for(int i = 0; i<number ;i++){
			usersID.add(i+1);
		}
		return usersID;
	}
}
