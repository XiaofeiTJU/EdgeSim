package cn.edu.tju.simulation.algorithm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.edu.tju.simulation.content.CachingSingleContent;
import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * 最近最少使用
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class LRUAlgorithm implements RealTimeAlgorithm{

	/**
	 * 最后面的是最新的
	 * @param network
	 * @param requestContent
	 */
	public void setCache(WirelessNetwork network,SingleLocalHobby requestContent){
		long remainingSize = network.getRemainingCacheSize();
		LinkedList<CachingSingleContent> cachingContentList =  network.getCacheContent();
		CachingSingleContent includeContent = include(cachingContentList, requestContent);

		
//			System.out.println("请求的内容："+requestContent.getName());
			if(includeContent != null){
				//缓存中现在包含这个内容，把它放到最前面
//				System.out.println("缓存中已经存在，替换到最前面");
				cachingContentList.remove(includeContent);
				cachingContentList.addFirst(includeContent);
			}else{
				if(remainingSize >= requestContent.getSize()){
					//能装下
//					System.out.println("缓存中能装下，直接缓存");
					cachingContentList.addFirst(new CachingSingleContent(requestContent.getSingleContent()));
				}else{
//					System.out.println("剩余大小："+remainingSize +" 缓存的大小:"+ network.getCacheSize()*(float)(1f/6f));
					//装不下，一个一个往外扔，直到装下为止
					if(network.getCacheSize() >= requestContent.getSize()){
//						System.out.println("装不下，一个一个往外扔，直到装下为止");
						long tempRenmaingSize = remainingSize;
						LinkedList<CachingSingleContent> tempList = new LinkedList<CachingSingleContent>();
						for (int i = cachingContentList.size() - 1; i >= 0; i--) {	
							tempRenmaingSize += cachingContentList.get(i).getSize();
//							System.out.println("剩余大小  "+tempRenmaingSize);
//							System.out.println("内容大小 " + requestContent.getSize());
							tempList.add(network.getCacheContent().get(i));
							if (tempRenmaingSize >= requestContent.getSize()) {
								break;
							}
						}
						
						int sumOfPopularity = 0;
						for(int j = 0 ; j <tempList.size();j++){
							SingleLocalHobby mySingleContentInNetwork = network.getContent().getSingleLocalHobbyBySingleContent(tempList.get(j).getSingleContent());
							sumOfPopularity += mySingleContentInNetwork.getLocalHobbyValue();							
						}
						SingleLocalHobby mySingleContentInNetwork = network.getContent().getSingleLocalHobbyBySingleContent(requestContent.getSingleContent());
						if(sumOfPopularity < mySingleContentInNetwork.getLocalHobbyValue() ){
							network.getCacheContent().removeAll(tempList);
						}
				}

			}
		
		
			}
			Controller.getInstance().appendLog("debug","Update Cache", null);
	}
	
	public CachingSingleContent include(List<CachingSingleContent> cacheContent , SingleLocalHobby content){
		Iterator<CachingSingleContent> it = cacheContent.iterator();
		while(it.hasNext()){
			CachingSingleContent csc = it.next();
			if(csc.getName().equals(content.getName())){
				System.out.println(cacheContent.contains(content));
				return csc;
			}
		}
		return null;
	}
}
