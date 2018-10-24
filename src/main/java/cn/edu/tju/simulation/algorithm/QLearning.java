package cn.edu.tju.simulation.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class QLearning implements OneTimeAlgorithm{
	private float gamma = 0.6f;
	private float alpha = 0.1f;
	private Controller controller;
	private int b = 5;
	private int C = 0;
	private HashMap<WirelessNetwork , List<reward>> Q;

	
	public QLearning(){
		this.controller = Controller.getInstance();
		this.Q = new HashMap<WirelessNetwork, List<reward>>();
		for(int i =0;i<controller.getWirelessNetworkGroup().BS.getAmount();i++){
			this.Q.put(controller.getWirelessNetworkGroup().BS.getNetwork(i), new ArrayList<reward>());
		}
	}
	
	public void setCache(){
		for(int j=0;j<Controller.getInstance().getWirelessNetworkGroup().BS.getAmount();j++){
			WirelessNetwork network = Controller.getInstance().getWirelessNetworkGroup().BS.getNetwork(j);
			long size = network.getCacheSize();
			List<SingleLocalHobby> canBeCachedContent = network.getContent().getContentList();
			
			
			int sumOfPopularity = 0;
			for (SingleLocalHobby singleContent : canBeCachedContent) {
				sumOfPopularity = sumOfPopularity+singleContent.getLocalHobbyValue();
			}
			
			LinkedHashMap<SingleLocalHobby,Float> probabilityDistribution = new LinkedHashMap<SingleLocalHobby, Float>();
			for (SingleLocalHobby singleContent : canBeCachedContent) {
				probabilityDistribution.put(singleContent, (float)singleContent.getLocalHobbyValue()/(float)sumOfPopularity);
			}

			
			if(Q.get(network).size() == 0){
				for(int i = 0;i<canBeCachedContent.size();i++){
					Q.get(network).add(new reward(canBeCachedContent.get(i),(probabilityDistribution.get(canBeCachedContent.get(i))*network.getUserOfNetwork().size()*b-5)*b-C));
				}
			}else{
				List<reward> lastReward = Q.get(network);
				for (reward mReward : lastReward) {
					for(int i =0;i<canBeCachedContent.size();i++){
						if(canBeCachedContent.get(i) == mReward.singleContent){
						    float rewardValue = (probabilityDistribution.get(canBeCachedContent.get(i))*network.getUserOfNetwork().size()*b-5)*b-C;
						    float newReward = rewardValue+alpha*(mReward.reward+gamma*rewardValue-rewardValue);
						    mReward.reward = newReward;
						    break;
						}
					}
				}
			}
			
			Collections.sort(Q.get(network),new Comparator<reward>() {
				public int compare(reward rewardOne, reward rewardTwo) {
					float rewardone = rewardOne.reward;
					float rewardtwo = rewardTwo.reward;
					if (rewardone < rewardtwo) {
						return 1;
					} else if (rewardone == rewardtwo) {
						return 0;
					} else {
						return -1;
					}
				}
			});	
			
			for (reward mReward : Q.get(network)) {
				if (size != 0 && size > mReward.singleContent.getSize()) {
					if (network.addCacheContent(mReward.singleContent)) {
						size -= mReward.singleContent.getSize();
					}
				}
			}
		}
	}
	
	class reward {
		public SingleLocalHobby singleContent;
		public float reward;

		public reward(SingleLocalHobby mySingleContent, float reward) {
			this.singleContent = mySingleContent;
			this.reward = reward;
		}
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getC() {
		return C;
	}

	public void setC(int c) {
		C = c;
	}

	public HashMap<WirelessNetwork, List<reward>> getQ() {
		return Q;
	}

	public void setQ(HashMap<WirelessNetwork, List<reward>> q) {
		Q = q;
	}

	public float getGamma() {
		return gamma;
	}

	public void setGamma(float gamma) {
		this.gamma = gamma;
	}
}
