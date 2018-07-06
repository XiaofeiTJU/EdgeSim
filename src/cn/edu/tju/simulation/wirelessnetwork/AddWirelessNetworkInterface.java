package cn.edu.tju.simulation.wirelessnetwork;

/**
 * The class that implements this interface must implement the following methods
 * Used to limit supported wireless network types
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public interface AddWirelessNetworkInterface {
	/**
	 * Add a base station to the wireless network collection
	 * @param bs
	 */
	public void addWirelessNetwork(WirelessNetwork noAddedNetwork);
}
