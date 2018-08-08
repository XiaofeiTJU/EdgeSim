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
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin
 *         University
 * 
 */
public class LRUAlgorithm implements RealTimeAlgorithm {

	/**
	 * 最后面的是最新的
	 * 
	 * @param network
	 * @param requestContent
	 */
	public void setCache(WirelessNetwork network, SingleLocalHobby requestContent) {
		long remainingSize = network.getRemainingCacheSize();
		LinkedList<CachingSingleContent> cachingContentList = network.getCacheContent();
		CachingSingleContent includeContent = include(cachingContentList,requestContent);

		// System.out.println("请求的内容："+requestContent.getName());
		if (includeContent != null) {
			// 缓存中现在包含这个内容，把它放到最前面
			// System.out.println("缓存中已经存在，替换到最前面");
			cachingContentList.remove(includeContent);
			cachingContentList.addFirst(includeContent);
		} else if (network.getCacheSize() >= requestContent.getSize()) {
			if (remainingSize >= requestContent.getSize()) {
				// 能装下
				// System.out.println("缓存中能装下，直接缓存");
				cachingContentList.addFirst(new CachingSingleContent(requestContent.getSingleContent()));
			} else {
				// System.out.println("剩余大小："+remainingSize +" 缓存的大小:"+
				// network.getCacheSize()*(float)(1f/6f));
				// 装不下，一个一个往外扔，直到装下为止
				// System.out.println("装不下，一个一个往外扔，直到装下为止");
				while(cachingContentList.size()!=0){
					remainingSize -= cachingContentList.getLast().getSize();
					cachingContentList.removeLast();
					if(remainingSize >= requestContent.getSize()){
						cachingContentList.addFirst(new CachingSingleContent(requestContent.getSingleContent()));
						break;
					}
				}
			}

		}
		Controller.getInstance().appendLog("debug", "LRU is Updating Cache", null);
	}

	public CachingSingleContent include(List<CachingSingleContent> cacheContent, SingleLocalHobby content) {
		Iterator<CachingSingleContent> it = cacheContent.iterator();
		while (it.hasNext()) {
			CachingSingleContent csc = it.next();
			if (csc.getSingleContent() == content.getSingleContent()) {
				return csc;
			}
		}
		return null;
	}
}
