package cn.edu.tju.simulation.algorithm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.edu.tju.simulation.content.CachingSingleContent;
import cn.edu.tju.simulation.content.MySingleContent;
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
	public void setCache(WirelessNetwork network,MySingleContent requestContent){
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
							MySingleContent mySingleContentInNetwork = network.getContent().getMySingleContentByInitialSingleContent(tempList.get(j).getCachingSingleContent());
							sumOfPopularity += mySingleContentInNetwork.getMyPopularity();							
						}
						MySingleContent mySingleContentInNetwork = network.getContent().getMySingleContentByInitialSingleContent(requestContent.getSingleContent());
						if(sumOfPopularity < mySingleContentInNetwork.getMyPopularity() ){
							network.getCacheContent().removeAll(tempList);
						}
				}

			}
		
		
			}

		
		
//	System.out.println("");
//			
//			for(int i = 0 ; i<cachingContentList.size();i++){
//				System.out.print(cachingContentList.get(i).getName()+" ");
//			}
//			
			Controller.getInstance().appendLog("debug","Update Cache", null);

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		long remainingSize = network.getRemainingCacheSize();
//		LinkedList<CachingSingleContent> cachingContentList =  network.getCacheContent();
//		CachingSingleContent includeContent = include(cachingContentList, requestContent);
//		if (requestContent.getSize()< 20480){
//			System.out.println("请求的内容："+requestContent.getName());
//			if(includeContent != null){
//				//缓存中现在包含这个内容，把它放到最前面
//				System.out.println("缓存中已经存在，替换到最前面");
//				cachingContentList.remove(includeContent);
//				cachingContentList.addFirst(includeContent);
//			}else{
//				if(remainingSize >= requestContent.getSize()){
//					//能装下
//					System.out.println("缓存中能装下，直接缓存");
//
//					cachingContentList.addFirst(new CachingSingleContent(requestContent.getSingleContent()));
//				}else{
//					//装不下，一个一个往外扔，直到装下为止
//					if(network.getCacheSize() >= requestContent.getSize()){
//						System.out.println("装不下，一个一个往外扔，直到装下为止");
//						for (int i = cachingContentList.size() - 1; i >= 0; i--) {
//							remainingSize += cachingContentList.get(i).getSize();
//							System.out.println("剩余大小  "+remainingSize);
//							System.out.println("内容大小 " + requestContent.getSize());
//							if (network.removeCacheContent(i)) {
//								if (remainingSize >= requestContent.getSize()) {
//									cachingContentList.addFirst(new CachingSingleContent(requestContent.getSingleContent()));
//									break;
//								}
//							}
//						}
//					}
//				}
//
//			}
//			
//			for(int i = 0 ; i<cachingContentList.size();i++){
//				System.out.print(cachingContentList.get(i).getName()+" ");
//			}
//			
//			Controller.getInstance().appendLog("debug","Update Cache", null);
//
//			System.out.println("结束配置！！");
//		}
	}
	
	public CachingSingleContent include(List<CachingSingleContent> cacheContent , MySingleContent content){
		Iterator<CachingSingleContent> it = cacheContent.iterator();
		while(it.hasNext()){
			CachingSingleContent csc = it.next();
			if(csc.getName().equals(content.getName())){
				return csc;
			}
		}
		return null;
	}
}
