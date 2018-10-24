package cn.edu.tju.simulation.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.distribution.ZipfDistribution;

import cn.edu.tju.simulation.file.Parameter;
import cn.edu.tju.simulation.wirelessnetwork.SameTypeWirelessNetwork;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * Sort the content on the Internet based on the downloads
 * @author ÀîÎÄ¿­
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
    	ZipfDistribution zd = new ZipfDistribution(Parameter.ZIPF_SAMPLE, Parameter.ZIPF_CONEFFICIENT);
    	int [] popularity = zd.sample(noPopularityMediaList.size());

    	for(int i = 0 ; i<noPopularityMediaList.size();i++){
    		noPopularityMediaList.get(i).getSingleContent().setPopularity(popularity[i]);

    	}
    	return sortByInitialPopularity(noPopularityMediaList);
    }
    
    public static void resetMyHobby(HashMap<Integer, List<SingleLocalHobby>> initialPopularity, SameTypeWirelessNetwork BSs){
    	Iterator<WirelessNetwork> it = BSs.getIterator();
    	while(it.hasNext()){
    		WirelessNetwork network = it.next();
    		Iterator<SingleLocalHobby> mIt = initialPopularity.get(network.getNumber()).iterator();
    		while(mIt.hasNext()){
    			SingleLocalHobby slh = mIt.next();
    			network.getContent().getSingleContentByName(slh.getName()).setLocalHobbyValue(slh.getLocalHobbyValue());;
    		}
    	}
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
