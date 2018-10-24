package cn.edu.tju.simulation.algorithm;

import java.util.LinkedList;
import java.util.List;

import cn.edu.tju.simulation.content.CachingSingleContent;
import cn.edu.tju.simulation.content.ContentService;
import cn.edu.tju.simulation.content.SingleContent;
import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class LFUAlgorithm implements RealTimeAlgorithm{
	LinkedList<SingleContent> watingCachingContent;
	
	public LFUAlgorithm(){
		watingCachingContent = new LinkedList<SingleContent>();
	}
	
	public void setCache(WirelessNetwork requestedNetwork,SingleLocalHobby requestContent){
		long remainingSize =  requestedNetwork.getCacheSize();
		LinkedList<CachingSingleContent> cachingContentList =  requestedNetwork.getCacheContent();
		List<SingleLocalHobby> list =  requestedNetwork.getContent().getContentList();
		ContentService.sortMySingleContentByRequestedAmount(requestedNetwork.getContent().getContentList());
		//request have not init
		
		cachingContentList.clear();
		
		for(int i = 0 ; i <list.size();i++){
			if(list.get(i).getSize() <= remainingSize){
				cachingContentList.add(new CachingSingleContent(list.get(i).getSingleContent()));
				remainingSize -= list.get(i).getSize();
			}
		}

	}

}
