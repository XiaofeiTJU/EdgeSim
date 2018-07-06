package cn.edu.tju.simulation.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class InitialContent{
	private List<InitialSingleContent> content;
	
	public InitialContent(){
		this.content = new ArrayList<InitialSingleContent>();	
	}
	
	public InitialSingleContent getSingleContentByName(String name){
		for (InitialSingleContent sc : content) {
			if(sc.getName().equals(name)){
				return sc;
			}
		}
		return null;
	}
	
	public List<InitialSingleContent> getContentList(){
		return this.content;
	}
	
	public void setContentList(List<InitialSingleContent> content){
		this.content = content;
	}
	
	public void clear(){
		this.content.clear();
	}
	
	public void addSingleContent(InitialSingleContent singleContent){
		this.content.add(singleContent);
	}
	
	public void addSingleContentAmount(InitialSingleContent singleContent){
		for(int i = 0 ;i <this.content.size();i++){
			if(this.content.get(i).getName().equals(singleContent.getName())){
				this.content.get(i).addRequestAmount(1);
				break;
			}
		}
	}
	
	public void sortByPopularity(){
		if (this.content != null) {
			Collections.sort(this.content, new Comparator<InitialSingleContent>() {
				public int compare(InitialSingleContent contentOne, InitialSingleContent contentTwo) {
					 return contentOne.getPopularity() == contentTwo.getPopularity() ? 0 :
						 (contentOne.getPopularity() > contentTwo.getPopularity() ? -1 : 1); 
				}
			});
		}
	}
	
    public void fluctuatePopularity(float min ,float max,List<InitialSingleContent> contentList){
//		controller.appendLog("Base station fluctuations in popularity adjustment...",null);
    		if(max >= min){
    			Random r = new Random();
    	    	this.content.clear();
        		for(int j =0;j<contentList.size();j++ ){
        			float random = (min + ((max - min) * r.nextFloat()));
        			InitialSingleContent singleContent = contentList.get(j);
//        			System.out.println("波动前："+singleContent.getName()+"的流行度是："+singleContent.getAmount());
        			int amount = singleContent.getPopularity();
        			int temp = (int)(amount*random);
        	
        			addSingleContent(new InitialSingleContent(singleContent.getName(),temp,singleContent.getSize()));
//        			System.out.println("波动后："+singleContent.getName()+"的流行度是："+temp);
        		}
        		//Sort the content popularity in the base station
            	sortByPopularity();
    	}
    }

    
    
}
