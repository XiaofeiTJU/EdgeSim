package cn.edu.tju.simulation.algorithm;

import java.util.LinkedList;
import java.util.List;

import cn.edu.tju.simulation.content.CachingSingleContent;
import cn.edu.tju.simulation.content.ContentService;
import cn.edu.tju.simulation.content.InitialSingleContent;
import cn.edu.tju.simulation.content.MySingleContent;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class LFUAlgorithm implements RealTimeAlgorithm{
	LinkedList<InitialSingleContent> watingCachingContent;
	
	public LFUAlgorithm(){
		watingCachingContent = new LinkedList<InitialSingleContent>();
	}
	
	public void setCache(WirelessNetwork requestedNetwork,MySingleContent requestContent){
		long remainingSize =  requestedNetwork.getCacheSize();
		LinkedList<CachingSingleContent> cachingContentList =  requestedNetwork.getCacheContent();
		List<MySingleContent> list =  requestedNetwork.getContent().getContentList();
		ContentService.sortMySingleContentByRequestedAmount(requestedNetwork.getContent().getContentList());
		//request次数没有初始化
		
		cachingContentList.clear();
		
		for(int i = 0 ; i <list.size();i++){
			if(list.get(i).getSize() <= remainingSize){
				cachingContentList.add(new CachingSingleContent(list.get(i).getSingleContent()));
				remainingSize -= list.get(i).getSize();
			}
		}

	}

}
