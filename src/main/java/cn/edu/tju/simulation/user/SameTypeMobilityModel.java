package cn.edu.tju.simulation.user;

import java.util.ArrayList;
import java.util.List;

/**
 * The same type of user collection's class
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class SameTypeMobilityModel{
	/**
	 * User's collection
	 */
	private List<MobilityModel> mobilityModelList;
	/**
	 * User's type
	 */
	private String type;
	
	public void clearMobilityModel(){
		this.mobilityModelList.clear();
	}

	public SameTypeMobilityModel(String Type){
		this.mobilityModelList = new ArrayList<MobilityModel>(); 
		this.type = Type;
	}
	
	public List<MobilityModel> getMobilityModelList() {
		return mobilityModelList;
	}

	public void setUsers(List<MobilityModel> users) {
		this.mobilityModelList = users;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
