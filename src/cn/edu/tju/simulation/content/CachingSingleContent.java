package cn.edu.tju.simulation.content;


public class CachingSingleContent {
	private SingleContent singleContent;
	
	public CachingSingleContent(SingleContent cachingSingleContent){
		this.singleContent = cachingSingleContent;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof SingleLocalHobby){
			SingleLocalHobby slh = (SingleLocalHobby)obj;
			if(this.singleContent == slh.getSingleContent()){
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
	
	
	public String getName(){
		return this.singleContent.getName();
	}
	
	public long getSize(){
		return this.singleContent.getSize();
	}
	
	public SingleContent getSingleContent() {
		return singleContent;
	}

}
