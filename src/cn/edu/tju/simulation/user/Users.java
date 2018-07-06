package cn.edu.tju.simulation.user;

import java.util.ArrayList;
import java.util.List;

import cn.edu.tju.simulation.state.State;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class Users {
	private SameTypeMobilityModel simpleUsers;

	public Users(){
		this.simpleUsers = new SameTypeMobilityModel("SimpleUser");
	}
	
	public List<State> generateStateList(){
		List<MobilityModel> simpleUserList  = this.simpleUsers.getMobilityModelList();
		List<State> stateList = new ArrayList<State>();
		
		for (int i = 0; i < simpleUserList.size(); i++) {
			State state = simpleUserList.get(i).generateState();
			if (state != null) {
				stateList.add(state);
			}
		}
		return stateList;	
	}
	
	public int getSimpleUsersAmount(){
		return simpleUsers.getMobilityModelList().size();
	}
	
	public List<MobilityModel> getSimpleUsers() {
		return simpleUsers.getMobilityModelList();
	}

	public void setSimpleUsers(SameTypeMobilityModel simpleUsers) {
		this.simpleUsers = simpleUsers;
	}
	
	public String getSimpleUsersType(){
		return simpleUsers.getType();
	}
}
