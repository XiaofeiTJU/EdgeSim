package cn.edu.tju.simulation.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.ZipfDistribution;

import cn.edu.tju.simulation.wirelessnetwork.SameTypeWirelessNetwork;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * Sort the content on the Internet based on the downloads
 * @author 李文凯
 */
public class ContentService {
	private List<SingleContent> mediaList;
	/**
	 * According to the download volume, the popularity of sorting..
	 * @param mediaList Content collection
	 */
	
	public static List<SingleLocalHobby> sortByHobby(List<SingleLocalHobby> mediaList) {
		if (mediaList != null) {
			Collections.sort(mediaList, new Comparator<SingleLocalHobby>() {
				public int compare(SingleLocalHobby contentOne, SingleLocalHobby contentTwo) {
					 return contentOne.getLocalHobbyValue() == contentTwo.getLocalHobbyValue() ? 0 :    
			                (contentOne.getLocalHobbyValue() > contentTwo.getLocalHobbyValue() ? -1 : 1); 
				}
			});
		}
		
		return mediaList;
	}

	public static List<SingleLocalHobby> sortByInitialPopularity(List<SingleLocalHobby> mediaList) {
		if (mediaList != null) {
			Collections.sort(mediaList, new Comparator<SingleLocalHobby>() {
				public int compare(SingleLocalHobby contentOne, SingleLocalHobby contentTwo) {
					 return contentOne.getSingleContent().getPopularity() == contentTwo.getSingleContent().getPopularity() ? 0 :    
			                (contentOne.getSingleContent().getPopularity() > contentTwo.getSingleContent().getPopularity() ? -1 : 1); 
				}
			});
		}
		return mediaList;
	}

	public static List<SingleLocalHobby> sortByAverageHobby(List<SingleLocalHobby> mediaList) {
		if (mediaList != null) {
			Collections.sort(mediaList, new Comparator<SingleLocalHobby>() {
				public int compare(SingleLocalHobby contentOne, SingleLocalHobby contentTwo) {
					 return contentOne.getLocalHobbyValue()/contentOne.getSize() == contentTwo.getLocalHobbyValue()/contentTwo.getSize() ? 0 :    
			                (contentOne.getLocalHobbyValue()/contentOne.getSize() > contentTwo.getLocalHobbyValue()/contentTwo.getSize() ? -1 : 1); 
				}
			});
		}
		return mediaList;
	}
	
	public static List<SingleContent> sortByRequestedAmount(List<SingleContent> mediaList) {
		if (mediaList != null) {
			Collections.sort(mediaList, new Comparator<SingleContent>() {
				public int compare(SingleContent contentOne, SingleContent contentTwo) {
					 return contentOne.getRequestAmount() == contentTwo.getRequestAmount() ? 0 :    
			                (contentOne.getRequestAmount() > contentTwo.getRequestAmount() ? -1 : 1); 
				}
			});
		}
		return mediaList;
	}
	
	public static LinkedList<CachingSingleContent> sortCachingContentByPopularity(LinkedList<CachingSingleContent> mediaList) {
		if (mediaList != null) {
			Collections.sort(mediaList, new Comparator<CachingSingleContent>() {
				public int compare(CachingSingleContent contentOne, CachingSingleContent contentTwo) {
					 return contentOne.getSingleContent().getPopularity() == contentTwo.getSingleContent().getPopularity() ? 0 :    
			                (contentOne.getSingleContent().getPopularity() > contentTwo.getSingleContent().getPopularity() ? -1 : 1); 
				}
			});
		}
		return mediaList;
	}
	
	public static LinkedList<CachingSingleContent> sortCachingContentByRequestedAmount(LinkedList<CachingSingleContent> mediaList) {
		if (mediaList != null) {
			Collections.sort(mediaList, new Comparator<CachingSingleContent>() {
				public int compare(CachingSingleContent contentOne, CachingSingleContent contentTwo) {
					 return contentOne.getSingleContent().getRequestAmount() == contentTwo.getSingleContent().getRequestAmount() ? 0 :    
			                (contentOne.getSingleContent().getRequestAmount() > contentTwo.getSingleContent().getRequestAmount() ? -1 : 1); 
				}
			});
		}
		return mediaList;
	}
	
	public static List<SingleLocalHobby> sortMySingleContentByRequestedAmount(List<SingleLocalHobby> list) {
		if (list != null) {
			Collections.sort(list, new Comparator<SingleLocalHobby>() {
				public int compare(SingleLocalHobby contentOne, SingleLocalHobby contentTwo) {
					 return contentOne.getSingleContent().getRequestAmount() == contentTwo.getSingleContent().getRequestAmount() ? 0 :    
			                (contentOne.getSingleContent().getRequestAmount() > contentTwo.getSingleContent().getRequestAmount() ? -1 : 1); 
				}
			});
		}
		return list;
	}
	
	
    /**
     * Initialize the popularity according to power law distribution
     */
    public static List<SingleLocalHobby> initialPopularity(List<SingleLocalHobby> noPopularityMediaList){
    	ZipfDistribution zd = new ZipfDistribution(5000, 1);
    	int [] popularity = zd.sample(noPopularityMediaList.size());

    	for(int i = 0 ; i<noPopularityMediaList.size();i++){
    		noPopularityMediaList.get(i).getSingleContent().setPopularity(popularity[i]);

    	}
    	return sortByInitialPopularity(noPopularityMediaList);
    }
    
    //可优化
    public static void resetMyHobby(HashMap<Integer, List<SingleLocalHobby>> initialPopularity, SameTypeWirelessNetwork BSs){
    	Iterator<Integer> it = initialPopularity.keySet().iterator();
		while(it.hasNext()){
			int key = it.next();
			for(int i =0;i<BSs.getAmount();i++){
				if(BSs.getNetwork(i).getNumber() == key){
					WirelessNetwork wn = BSs.getNetwork(i);
					for(int j = 0;j<initialPopularity.get(key).size();j++){
						
						Iterator<SingleLocalHobby> mIt =wn.getContent().getContentList().iterator();
						while(mIt.hasNext()){
							SingleLocalHobby sc = mIt.next();
							if(sc.getName().equals(initialPopularity.get(key).get(j).getName())){
								sc.setLocalHobbyValue(initialPopularity.get(key).get(j).getLocalHobbyValue());
							}
						}
					}
					break;
				}
			}
		}
    }
    
    public static List<SingleContent> initialSize(List<SingleContent> noPopularityMediaList){
//    	double probability [] = {0.005,0.018,0.047,0.466,0.995,1};
    	double probability [] = {0.095,0.218,0.317,0.466,0.805,1};

    	Random r = new Random();
    	
    	for (SingleContent media : noPopularityMediaList) {
        	int size = 0;
        	double temp  = Math.random();
        	for(int i = 0 ;i <probability.length; i++){
        		if(i==0){
        			if(temp<probability[i]){
        				size = (r.nextInt(10)%(10)+1);
        				break;
        			}
        		}else{
        			if(temp<probability[i] && temp>= probability[i-1]){
        				size = r.nextInt((i+1)*10)%(10)+i*10;
        				break;
        			}
        		}
        	}
        	media.setSize(size);
		}
		return noPopularityMediaList;
   
    }
    
    public static List<SingleLocalHobby> copyMyHobby(List<SingleLocalHobby> contentList){
		List<SingleLocalHobby> myContentList = new ArrayList<SingleLocalHobby>();
		for (SingleLocalHobby content : contentList) {
			SingleLocalHobby mContent = new SingleLocalHobby(content.getSingleContent(),content.getLocalHobbyValue());
			myContentList.add(mContent);
		}
		return myContentList;
    }

    
    /**
     * KB converted to GB
     */
    public static float unitConversion(long B){
    	return (float)B/1024/1024;
    }
    
	public List<SingleContent> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<SingleContent> mediaList) {
		this.mediaList = mediaList;
	}
}
