package cn.edu.tju.simulation.algorithm;

import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public interface RealTimeAlgorithm{
	public void setCache(WirelessNetwork network,SingleLocalHobby requestContent);
}
