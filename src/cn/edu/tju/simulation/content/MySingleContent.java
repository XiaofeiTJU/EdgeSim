package cn.edu.tju.simulation.content;

public class MySingleContent {
	private InitialSingleContent singleContent;
	private int myPopularity;

	
	public void addRequestedAmount(){
		this.singleContent.addRequestAmount(1);
	}
	
	public void addMyHobby(){
		this.myPopularity++;
	}
	
	public String getName(){
		return this.singleContent.getName();
	}
	
	public long getSize(){
		return this.singleContent.getSize();
	}
	
	public int getTimeSlotNumber(){
		return this.singleContent.getTimeSlotNumber();
	}
	
	public MySingleContent(InitialSingleContent singleContent , int popularity){
		this.singleContent = singleContent;
		this.myPopularity = popularity;
	}
	
	public MySingleContent(InitialSingleContent singleContent){
		this.singleContent = singleContent;
		this.myPopularity = singleContent.getPopularity();
	}
	
	public InitialSingleContent getSingleContent() {
		return singleContent;
	}

	public void setSingleContent(InitialSingleContent singleContent) {
		this.singleContent = singleContent;
	}

	public int getMyPopularity() {
		return myPopularity;
	}

	public void setMyPopularity(int popularity) {
		this.myPopularity = popularity;
	}
	
	
	
}
