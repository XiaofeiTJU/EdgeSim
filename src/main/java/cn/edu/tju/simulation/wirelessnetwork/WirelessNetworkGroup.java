package cn.edu.tju.simulation.wirelessnetwork;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class WirelessNetworkGroup {
	public SameTypeWirelessNetwork BS;

	public WirelessNetworkGroup(){
		this.BS = new SameTypeWirelessNetwork("BaseStation");
	}
	
	public int getBSAmount(){
		return BS.getAmount();
	}
	
	public void clearAllCache(){
		BS.clearAllCache();
	}

}
