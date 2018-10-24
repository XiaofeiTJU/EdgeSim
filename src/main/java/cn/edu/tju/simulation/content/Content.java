package cn.edu.tju.simulation.content;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class Content{
	private LinkedList<SingleContent> content;
	
	public Content(){
		this.content = new LinkedList<SingleContent>();	
	}
	
	public void clear(){
		this.content.clear();
	}
	
	public void addSingleContent(SingleContent singleContent){
		this.content.add(singleContent);
	}
	
	public void sortByPopularity(){
		if (this.content != null) {
			Collections.sort(this.content, new Comparator<SingleContent>() {
				public int compare(SingleContent contentOne, SingleContent contentTwo) {
					 return contentOne.getPopularity() == contentTwo.getPopularity() ? 0 :
						 (contentOne.getPopularity() > contentTwo.getPopularity() ? -1 : 1); 
				}
			});
		}
	}

}
