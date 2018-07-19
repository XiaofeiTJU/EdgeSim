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
	private List<InitialSingleContent> mediaList;
	/**
	 * According to the download volume, the popularity of sorting..
	 * @param mediaList Content collection
	 */
	
	public static List<MySingleContent> sortByHobby(List<MySingleContent> mediaList) {
		if (mediaList != null) {
			Collections.sort(mediaList, new Comparator<MySingleContent>() {
				public int compare(MySingleContent contentOne, MySingleContent contentTwo) {
					 return contentOne.getMyPopularity() == contentTwo.getMyPopularity() ? 0 :    
			                (contentOne.getMyPopularity() > contentTwo.getMyPopularity() ? -1 : 1); 
				}
			});
		}
		
		return mediaList;
	}

	public static List<MySingleContent> sortByInitialPopularity(List<MySingleContent> mediaList) {
		if (mediaList != null) {
			Collections.sort(mediaList, new Comparator<MySingleContent>() {
				public int compare(MySingleContent contentOne, MySingleContent contentTwo) {
					 return contentOne.getSingleContent().getPopularity() == contentTwo.getSingleContent().getPopularity() ? 0 :    
			                (contentOne.getSingleContent().getPopularity() > contentTwo.getSingleContent().getPopularity() ? -1 : 1); 
				}
			});
		}
		return mediaList;
	}

	public static List<MySingleContent> sortByAverageHobby(List<MySingleContent> mediaList) {
		if (mediaList != null) {
			Collections.sort(mediaList, new Comparator<MySingleContent>() {
				public int compare(MySingleContent contentOne, MySingleContent contentTwo) {
					 return contentOne.getMyPopularity()/contentOne.getSize() == contentTwo.getMyPopularity()/contentTwo.getSize() ? 0 :    
			                (contentOne.getMyPopularity()/contentOne.getSize() > contentTwo.getMyPopularity()/contentTwo.getSize() ? -1 : 1); 
				}
			});
		}
		return mediaList;
	}
	
	public static List<InitialSingleContent> sortByRequestedAmount(List<InitialSingleContent> mediaList) {
		if (mediaList != null) {
			Collections.sort(mediaList, new Comparator<InitialSingleContent>() {
				public int compare(InitialSingleContent contentOne, InitialSingleContent contentTwo) {
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
					 return contentOne.getCachingSingleContent().getPopularity() == contentTwo.getCachingSingleContent().getPopularity() ? 0 :    
			                (contentOne.getCachingSingleContent().getPopularity() > contentTwo.getCachingSingleContent().getPopularity() ? -1 : 1); 
				}
			});
		}
		return mediaList;
	}
	
	public static LinkedList<CachingSingleContent> sortCachingContentByRequestedAmount(LinkedList<CachingSingleContent> mediaList) {
		if (mediaList != null) {
			Collections.sort(mediaList, new Comparator<CachingSingleContent>() {
				public int compare(CachingSingleContent contentOne, CachingSingleContent contentTwo) {
					 return contentOne.getCachingSingleContent().getRequestAmount() == contentTwo.getCachingSingleContent().getRequestAmount() ? 0 :    
			                (contentOne.getCachingSingleContent().getRequestAmount() > contentTwo.getCachingSingleContent().getRequestAmount() ? -1 : 1); 
				}
			});
		}
		return mediaList;
	}
	
	public static List<MySingleContent> sortMySingleContentByRequestedAmount(List<MySingleContent> list) {
		if (list != null) {
			Collections.sort(list, new Comparator<MySingleContent>() {
				public int compare(MySingleContent contentOne, MySingleContent contentTwo) {
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
    public static List<MySingleContent> initialPopularity(List<MySingleContent> noPopularityMediaList){
    	ZipfDistribution zd = new ZipfDistribution(5000, 1);
    	int [] popularity = zd.sample(noPopularityMediaList.size());

    	for(int i = 0 ; i<noPopularityMediaList.size();i++){
    		noPopularityMediaList.get(i).getSingleContent().setPopularity(popularity[i]);

    	}
    	return sortByInitialPopularity(noPopularityMediaList);
    }
    
    //可优化
    public static void resetMyHobby(HashMap<Integer, List<MySingleContent>> initialPopularity, SameTypeWirelessNetwork BSs){
    	Iterator<Integer> it = initialPopularity.keySet().iterator();
		while(it.hasNext()){
			int key = it.next();
			for(int i =0;i<BSs.getAmount();i++){
				if(BSs.getNetwork(i).getNumber() == key){
					WirelessNetwork wn = BSs.getNetwork(i);
					for(int j = 0;j<initialPopularity.get(key).size();j++){
						
						Iterator<MySingleContent> mIt =wn.getContent().getContentList().iterator();
						while(mIt.hasNext()){
							MySingleContent sc = mIt.next();
							if(sc.getName().equals(initialPopularity.get(key).get(j).getName())){
								sc.setMyPopularity(initialPopularity.get(key).get(j).getMyPopularity());
							}
						}
					}
					break;
				}
			}
		}
    }
    
    public static List<InitialSingleContent> initialSize(List<InitialSingleContent> noPopularityMediaList){
//    	double probability [] = {0.005,0.018,0.047,0.466,0.995,1};
    	double probability [] = {0.095,0.218,0.317,0.466,0.805,1};

    	Random r = new Random();
    	
    	for (InitialSingleContent media : noPopularityMediaList) {
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
    
    public static List<MySingleContent> copyMyHobby(List<MySingleContent> contentList){
		List<MySingleContent> myContentList = new ArrayList<MySingleContent>();
		for (MySingleContent content : contentList) {
			MySingleContent mContent = new MySingleContent(content.getSingleContent(),content.getMyPopularity());
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
    
	public List<InitialSingleContent> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<InitialSingleContent> mediaList) {
		this.mediaList = mediaList;
	}

}
