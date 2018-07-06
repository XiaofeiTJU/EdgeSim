package cn.edu.tju.simulation.content;


/**
 * Multimedia content
 * @author ÀîÎÄ¿­
 *
 */
public class InitialSingleContent{
	/**
	 * Multimedia name
	 */
	private String name;
	/**
	 * Multimedia downloads
	 */
	private int popularity;
	/**
	 * Multimedia size
	 */
	private long size;
	
	private int requestAmount;
	
	private int timeSlotNumber;
	
	public InitialSingleContent(String name,int amount,long size){
		this.name = name;
		this.popularity = amount;
		this.size = size;
//		this.timeSlotNumber = (int) (Math.ceil(size/Parameter.ContentSizePerTimeSlice));
//		if(this.timeSlotNumber == 0){
//			this.timeSlotNumber += 1;
//		}
		this.timeSlotNumber = 1;
		this.requestAmount = 0;
	}

	public InitialSingleContent(){
		
	}
	
	public InitialSingleContent(int name){
		this.name = String.valueOf(name);
	}
	
	public void addRequestAmount(int x){
		this.requestAmount +=x;
	}
	
	

	public int getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(int requestAmount) {
		this.requestAmount = requestAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int getPopularity(){
		return popularity;
	}

	public void setPopularity(int amount) {
		this.popularity = amount;
	}

	
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
		this.timeSlotNumber = 1;
	}

	public int getTimeSlotNumber() {
		return timeSlotNumber;
	}

	public void setTimeSlotNumber(int timeSlotNumber) {
		this.timeSlotNumber = timeSlotNumber;
	}

	
}
