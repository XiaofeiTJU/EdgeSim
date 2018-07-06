package cn.edu.tju.simulation.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MyContent {
	private List<MySingleContent> contentList;
	
	public MyContent(){
		this.contentList = new ArrayList<MySingleContent>();	
	}
	
	public MySingleContent getSingleContentByName(String name){
		for (MySingleContent sc : contentList) {
			if(sc.getSingleContent().getName().equals(name)){
				return sc;
			}
		}
		return null;
	}
	
	public MySingleContent getMySingleContentByInitialSingleContent(InitialSingleContent isc){
		Iterator<MySingleContent> it = this.contentList.iterator();
		while(it.hasNext()){
			MySingleContent mc = it.next();
			if(mc.getSingleContent() == isc){
				return mc;
			}
		}
		return null;
	}


	public void setContentList(List<MySingleContent> content) {
		this.contentList = content;
	}

	public void clear(){
		this.contentList.clear();
	}
	
	public void addSingleContent(MySingleContent singleContent){
		this.contentList.add(singleContent);
	}
	
	public void addSingleContentAmount(InitialSingleContent singleContent){
		for(int i = 0 ;i <this.contentList.size();i++){
			if(this.contentList.get(i).getSingleContent().getName().equals(singleContent.getName())){
				this.contentList.get(i).getSingleContent().addRequestAmount(1);
				break;
			}
		}
	}
	
	public void sortByHobby(){
		if (this.contentList != null) {
			Collections.sort(this.contentList, new Comparator<MySingleContent>() {
				public int compare(MySingleContent contentOne, MySingleContent contentTwo) {
					 return contentOne.getMyPopularity() == contentTwo.getMyPopularity() ? 0 :
						 (contentOne.getMyPopularity() > contentTwo.getMyPopularity() ? -1 : 1); 
				}
			});
		}
	}
	
    public void fluctuatePopularity(float min ,float max,List<MySingleContent> contentList){
//		controller.appendLog("Base station fluctuations in popularity adjustment...",null);
    		if(max >= min){
    			Random r = new Random();
    	    	this.contentList.clear();
        		for(int j =0;j<contentList.size();j++ ){
        			float random = (min + ((max - min) * r.nextFloat()));
        			MySingleContent singleContent = contentList.get(j);
//        			System.out.println("波动前："+singleContent.getName()+"的流行度是："+singleContent.getAmount());
        			int amount = singleContent.getMyPopularity();
        			int temp = (int)(amount*random);
        			addSingleContent(new MySingleContent(singleContent.getSingleContent(), temp));
//        			addSingleContent(new SingleContent(singleContent.getName(),temp,singleContent.getSize()));
//        			System.out.println("波动后："+singleContent.getName()+"的流行度是："+temp);
        		}
        		//Sort the content popularity in the base station
        		sortByHobby();
    	}
    }

    public  List<MySingleContent> getContentList(){
    	return this.contentList;
    }
    
}
