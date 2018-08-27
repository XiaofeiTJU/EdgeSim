package cn.edu.tju.simulation.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.wirelessnetwork.SameTypeWirelessNetwork;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * Use the KnapsackAlgorithm to configure the cache
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 * 
 */
public class KnapsackAlgorithm implements OneTimeAlgorithm{
	/**
	 * Controller
	 */
	private Controller controller ;
	
	public KnapsackAlgorithm(){
		this.controller = Controller.getInstance();
	}
	
	/**
	 * Configure cache
	 * @param controller
	 */
	public void setCache() {
		SameTypeWirelessNetwork BSs = controller.getWirelessNetworkGroup().BS;
		for (int i = 0; i < BSs.getAmount(); i++) {
			WirelessNetwork network = BSs.getNetwork(i);
			int size = (int)(network.getCacheSize()/1024);
			List<SingleLocalHobby> canBeCachedContent= network.getCanBeCachedContent();
			Iterator<SingleLocalHobby> it = canBeCachedContent.iterator();			

			
			List<Integer> mediaAmount = new ArrayList<Integer>();
			List<Integer> mediaSize = new ArrayList<Integer>();

			while (it.hasNext()) {
				SingleLocalHobby sc = it.next();
				mediaAmount.add(sc.getLocalHobbyValue());
				int temp =(int) Math.ceil(sc.getSize()/1024);
				mediaSize.add(temp);
			}

			Integer[] mediaAmounts = mediaAmount.toArray(new Integer[mediaAmount.size()]);
			Integer[] mediaSizes = mediaSize.toArray(new Integer[mediaSize.size()]);
			
			// If there is Cache
			if (network.getHasCache()) {
				List<Integer> optimal = knapsackAlgorithm(size,mediaSizes, mediaAmounts);
				
				for (int j = 0; j < optimal.size(); j++) {
					network.addCacheContent(canBeCachedContent.get(optimal.get(j) - 1));
				}
				
				int maxPopularity = 0;
				for(int m = 0 ;m<network.getCacheContent().size();m++){
					maxPopularity += network.getCacheContent().get(m).getSingleContent().getPopularity();
				}
//				controller.appendLog("debug","The maximum cache popularity£º " + maxPopularity+"  Cached content number£º",null);
//				for(int n = 0 ; n<network.getCacheContent().size();n++){
//					controller.appendLog("debug",network.getCacheContent().get(n).getName()+"",null);
//				}	
			} else {
				System.out.println("No Cache");
			}
		}
	}

	/**
	 * KnapsackAlgorithm£¬According to the popularity to get the best cache queue
	 * 
	 * @param CacheSize
	 *            Cache size
	 * @param media_sizes
	 *            A collection of all content sizes
	 * @param media_amounts
	 *            Collection of all content downloads (popularity)
	 * @return
	 */
	public List<Integer> knapsackAlgorithm(int CacheSize,Integer [] mediaSizes, Integer [] mediaAmounts) {
		List<Integer> optimal = new ArrayList<Integer>();
		int n = mediaAmounts.length;
		Integer[] size = new Integer[n + 1];
		Integer[] amount = new Integer[n + 1];
		double[][] G = new double[n + 1][(CacheSize + 1)];

		for (int i = 1; i < n + 1; i++) {
			amount[i] = mediaAmounts[i - 1];
			size[i] = mediaSizes[i - 1];
		}

		double[] maxpopularity = new double[(CacheSize + 1)];

		for (int i = 1; i < n + 1; i++) {
			for (int t =CacheSize; t >= size[i]; t--) {
				if (maxpopularity[t] < maxpopularity[(t - size[i])] + amount[i]) {
					maxpopularity[t] = maxpopularity[(t - size[i])] + amount[i];
					G[i][t] = 1;
				}
			}
		}

		int i = n;
		int j = (int) CacheSize;
		while (i > 0) {
			if (G[i][j] == 1) {
				optimal.add(i);
				j -= size[i];
			}
			i--;
		}
		return optimal;
	}
	
}
