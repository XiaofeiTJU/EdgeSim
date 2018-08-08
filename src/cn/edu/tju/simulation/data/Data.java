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
	private double latency;
	/**
	 * Save traffic
	 */
	private long Traffic;

	public Data(int times){
		this.timeSlice = times;
		this.latency = 0;
		this.Traffic = 0;

	}
	
	public void addTraffic(long saveTraffic){
		this.Traffic += saveTraffic;
	}
	
	public void addLatency(double latency){
		this.latency += latency;
	}
	
	public long getSaveTraffic() {
		return Traffic;
	}
	
	public float getHitRate() {
		return hitRate;
	}

	public int getTimeSlice() {
		return timeSlice;
	}

	public double getLatency() {
		return latency;
	}

	public void setLatency(double latency) {
		this.latency = latency;
	}

	public void setHitRate(float hitRate) {
		this.hitRate = hitRate;
	}

	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}

	public void setTraffic(long saveTraffic) {
		this.Traffic = saveTraffic;
	}

	
	
}
