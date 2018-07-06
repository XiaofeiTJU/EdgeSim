package cn.edu.tju.simulation.tool;

import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import cn.edu.tju.simulation.state.State;
import cn.edu.tju.simulation.wirelessnetwork.BaseStation;

/**
 * String processing class
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 * 
 */
public class StringHandler {
	/**
	 * A line state to generate an array
	 * @param line One line in the status file
	 * @return
	 */
	public static String[] array(String line) {
		if (line != null) {
			String[] state = line.split(" ");
			state[1]=state[1]+" "+state[2];
			state[2]=state[3];
			return state;
		} else {
			return null;
		}
	}
	
	/**
	 * Sort the status chronologically
	 * @param states The original state collection
	 * @return
	 */

	public static List <State> sort(List <State> states){
		if(states != null){
			Collections.sort(states,new Comparator<State>(){
	            public int compare(State stateOne, State stateTwo) {
	            	Double stateOneDate = Double.valueOf(stateOne.getTime().replaceAll("-", "").replaceAll(":", "").replace(" ", ""));
	            	Double stateTwoDate = Double.valueOf(stateTwo.getTime().replaceAll("-", "").replaceAll(":", "").replace(" ", ""));
	            	Double field =  Double.parseDouble("10000000000000");
	            	if(stateOneDate < field || stateTwoDate <field){
	            		System.out.println("The number of digits is incorrect£¡");
	            		return -1;
	            	}else{
	            		if(stateOneDate > stateTwoDate){
	            			return 1;
	            		}else if(stateOneDate == stateTwoDate){
	            			return 0;
	            		}else{
	            			return -1;	
	            		}
	            	}
	            }
	        });
			return states;
		}else{
			return null;	
		}
	}

	/**
	 * Read the base station information in the file to generate the base station
	 * @param line
	 * @return 
	 */
	public static BaseStation readBS(String line){
		String [] networkInfo= line.split(" ");
		int ID = Integer.parseInt(networkInfo[0]);
		int x = (int)Double.parseDouble(networkInfo[1]);
		int y = (int)Double.parseDouble(networkInfo[2]);
		int radius = Integer.parseInt(networkInfo[3]);
		int cacheSize = Integer.parseInt(networkInfo[4]);
		
		return new BaseStation(ID,new Point2D.Double(x,y),true,cacheSize,radius);
	}
	
	
	
}
