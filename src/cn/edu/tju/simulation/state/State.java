package cn.edu.tju.simulation.state;

import cn.edu.tju.simulation.content.MySingleContent;
import cn.edu.tju.simulation.user.MobilityModel;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * Status
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class State implements Cloneable{
	/**
	 * The user to whom this status belongs
	 */
	private MobilityModel user;
	/**
	 * The time this state occurred
	 */
	private String time;
	/**
	 * Whether this user request content
	 */
	private Boolean request;
	/**
	 * Download content in this state
	 */
	private MySingleContent requestSingleContent;
	
	private int remainingTimeSlotNumber;
	
	private Boolean isExecute;
	/**
	 * 
	 * @param mobilityModel State belongs to the user
	 * @param time The time this state occurred
	 * @param media Download content in this state
	 */
	public State(MobilityModel user,String time,MySingleContent requestSingleContent){
		this.user = user;
		this.time = time;
		this.isExecute = false;		
		if(requestSingleContent == null){
			this.request = false;
			this.remainingTimeSlotNumber = 0;
		}else{
			this.request = true;
			this.requestSingleContent = requestSingleContent;
			this.remainingTimeSlotNumber = requestSingleContent.getTimeSlotNumber();
		}
	}

	@Override
	public Object clone() {
		State state = null;
		try {
			state = (State) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return state;
	}
	
	public void cutTimeSlotNumber(){
		this.remainingTimeSlotNumber--;
	}

	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}


	public MySingleContent getRequestSingleContent() {
		return requestSingleContent;
	}

	public void setRequestSingleContent(MySingleContent requestSingleContent) {
		this.requestSingleContent = requestSingleContent;
	}

	public WirelessNetwork getNetwork() {
		return this.user.getWirelessNetwork();
	}
	
	public MobilityModel getUser() {
		return user;
	}
	public Boolean getRequest() {
		return request;
	}

	public Boolean getIsExecute() {
		return isExecute;
	}

	public void setIsExecute(Boolean isExecute) {
		this.isExecute = isExecute;
	}

	public int getRemainingTimeSlotNumber() {
		return remainingTimeSlotNumber;
	}

	public void setRemainingTimeSlotNumber(int remainingTimeSlotNumber) {
		this.remainingTimeSlotNumber = remainingTimeSlotNumber;
	}

}
