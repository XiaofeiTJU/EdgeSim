package cn.edu.tju.simulation.content;

public class SingleLocalHobby {
	private SingleContent singleContent;
	private int localHobbyValue;

	public void addRequestedAmount() {
		this.singleContent.addRequestAmount(1);
	}

	public void addLocalHobbyValue() {
		this.localHobbyValue++;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof CachingSingleContent){
			CachingSingleContent csc = (CachingSingleContent)obj;
			if(this.singleContent == csc.getSingleContent()){
				return true;
			}else{
				return false;
			}
		}else if(obj instanceof SingleContent){
			SingleContent sc = (SingleContent)obj;
			if(this.singleContent == sc){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	public String getName() {
		return this.singleContent.getName();
	}

	public long getSize() {
		return this.singleContent.getSize();
	}

	public int getTimeSlotNumber() {
		return this.singleContent.getTimeSlotNumber();
	}

	public SingleLocalHobby(SingleContent singleContent, int localHobbyValue) {
		this.singleContent = singleContent;
		this.localHobbyValue = localHobbyValue;
	}

	public SingleLocalHobby(SingleContent singleContent) {
		this.singleContent = singleContent;
		this.localHobbyValue = singleContent.getPopularity();
	}

	public SingleContent getSingleContent() {
		return singleContent;
	}

	public void setSingleContent(SingleContent singleContent) {
		this.singleContent = singleContent;
	}

	public int getLocalHobbyValue() {
		return localHobbyValue;
	}

	public void setLocalHobbyValue(int popularity) {
		this.localHobbyValue = popularity;
	}

}
