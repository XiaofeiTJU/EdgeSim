package cn.edu.tju.simulation.algorithm;

import java.util.List;

import cn.edu.tju.simulation.content.ContentService;
import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.wirelessnetwork.SameTypeWirelessNetwork;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * Use the Greedy Algorithm to configure the cache
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 * 
 */
public class GreedyAlgorithm implements OneTimeAlgorithm{
	Controller controller = Controller.getInstance();
	List<SingleLocalHobby> list = null;
	SameTypeWirelessNetwork BSs = null;
	
	public GreedyAlgorithm(){
		Controller controller = Controller.getInstance();
		BSs = controller.getWirelessNetworkGroup().BS;
		
	
	}
	
	/**
	 * Configure cache
	 * @param controller
	 */
	public void setCache() {
		for (int i = 0; i < BSs.getAmount(); i++) {		
			WirelessNetwork network = BSs.getNetwork(i);			
			list = ContentService.sortByAverageHobby(network.getCanBeCachedContent());
//			list = ContentService.sortByHobby(network.getCanBeCachedContent());
		}
		
		for (int i = 0; i < BSs.getAmount(); i++) {		
			WirelessNetwork network = BSs.getNetwork(i);	
			long size = network.getCacheSize();
			for(int n = 0 ; n < list.size() ; n ++){
				SingleLocalHobby singleContent = list.get(n);
				if (size >= singleContent.getSize() && size > 0) {	
					if(network.addCacheContent(singleContent)){
						size -= singleContent.getSize();
					}
				}
			}
	
			int maxPopularity = 0;

			for(int j = 0;j<network.getCacheContent().size() ; j++){
				maxPopularity += network.getCacheContent().get(j).getSingleContent().getPopularity();
			}

			controller.appendLog("debug","network "+network.getNumber()+"'s maxPopularity is "+maxPopularity, null);
		
		}	
	}
}
