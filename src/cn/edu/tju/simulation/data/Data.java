package cn.edu.tju.simulation.data;
/**
 * This class stores the result of running a time slice, 
 * and the panel of the drawing result graph can get the result of a run through this class.
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class Data {
	/**
	 * This property represents which time slice the result belongs to.
	 */
	private int timeSlice;
	/**
	 * The system's hit rate in the current time slice.
	 */
	private float hitRate;
	/**
	 * Save time
	 */
	private int saveTime;
	/**
	 * Save traffic
	 */
	private long saveTraffic;

	public Data(int times){
		this.timeSlice = times;
		this.saveTime = 0;
		this.saveTraffic = 0;

	}
	
	public void addSaveTraffic(long saveTraffic){
		this.saveTraffic += saveTraffic;
	}
	
	public void reduceSaveTime(int reduceTime){
		this.saveTime -= reduceTime;
	}
	
	public void addSaveTime(int saveTime){
		this.saveTime += saveTime;
	}
	
	public long getSaveTraffic() {
		return saveTraffic;
	}
	
	public float getHitRate() {
		return hitRate;
	}

	public int getTimeSlice() {
		return timeSlice;
	}

	public int getSaveTime() {
		return saveTime;
	}

	public void setHitRate(float hitRate) {
		this.hitRate = hitRate;
	}

	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}

	public void setSaveTime(int saveTime) {
		this.saveTime = saveTime;
	}

	public void setSaveTraffic(long saveTraffic) {
		this.saveTraffic = saveTraffic;
	}

	
	
}
