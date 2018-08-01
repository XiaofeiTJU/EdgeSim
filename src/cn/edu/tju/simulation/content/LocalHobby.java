package cn.edu.tju.simulation.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class LocalHobby {
	private List<SingleLocalHobby> contentList;
	
	public LocalHobby(){
		this.contentList = new ArrayList<SingleLocalHobby>();	
	}
	
	public SingleLocalHobby getSingleContentByName(String name){
		for (SingleLocalHobby sc : contentList) {
			if(sc.getSingleContent().getName().equals(name)){
				return sc;
			}
		}
		return null;
	}
	
	public SingleLocalHobby getSingleLocalHobbyBySingleContent(SingleContent isc){
		Iterator<SingleLocalHobby> it = this.contentList.iterator();
		while(it.hasNext()){
			SingleLocalHobby sh = it.next();
			if(sh.getSingleContent() == isc){
				return sh;
			}
		}
		return null;
	}


	public void setContentList(List<SingleLocalHobby> content) {
		this.contentList = content;
	}

	public void clear(){
		this.contentList.clear();
	}
	
	public void addSingleContent(SingleLocalHobby singleContent){
		this.contentList.add(singleContent);
	}
	
	public void addSingleContentAmount(SingleContent singleContent){
		for(int i = 0 ;i <this.contentList.size();i++){
			if(this.contentList.get(i).getSingleContent().getName().equals(singleContent.getName())){
				this.contentList.get(i).getSingleContent().addRequestAmount(1);
				break;
			}
		}
	}
	
	public void sortByHobby(){
		if (this.contentList != null) {
			Collections.sort(this.contentList, new Comparator<SingleLocalHobby>() {
				public int compare(SingleLocalHobby contentOne, SingleLocalHobby contentTwo) {
					 return contentOne.getLocalHobbyValue() == contentTwo.getLocalHobbyValue() ? 0 :
						 (contentOne.getLocalHobbyValue() > contentTwo.getLocalHobbyValue() ? -1 : 1); 
				}
			});
		}
	}
	
    public void fluctuatePopularity(float min ,float max,List<SingleLocalHobby> contentList){
    		if(max >= min){
    			Random r = new Random();
    	    	this.contentList.clear();
        		for(int j =0;j<contentList.size();j++ ){
        			float random = (min + ((max - min) * r.nextFloat()));
        			SingleLocalHobby singleContent = contentList.get(j);
        			int amount = singleContent.getLocalHobbyValue();
        			int temp = (int)(amount*random);
        			addSingleContent(new SingleLocalHobby(singleContent.getSingleContent(), temp));
		}
        		//Sort the content popularity in the base station
        		sortByHobby();
    	}
    }

    public  List<SingleLocalHobby> getContentList(){
    	return this.contentList;
    }
    
}
