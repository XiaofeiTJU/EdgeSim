package cn.edu.tju.simulation.handler;

import java.util.List;

import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.user.MobilityModel;
import cn.edu.tju.simulation.wirelessnetwork.SameTypeWirelessNetwork;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class Pretreatment {
	
	public static void process(){
		Controller controller = Controller.getInstance();
		
		//网络进行流行度波动
		SameTypeWirelessNetwork BSs = controller.getWirelessNetworkGroup().BS;
		for(int i = 0 ;i<BSs.getAmount();i++){
			BSs.getNetwork(i).fluctuatePopularity();;
		}
		
		//用户进行流行度波动
		List<MobilityModel> users = controller.getUsers().getSimpleUsers();
		for(int j = 0 ;j<users.size();j++){
			users.get(j).fluctuatePopularity();
		}

		//生成队列
		controller.getStateQueue().generateStateQueue();
	}
}
