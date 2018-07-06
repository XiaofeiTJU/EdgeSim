package cn.edu.tju.simulation.file;

/**
 * Class of all parameters. This class corresponds to SIMULATION.xml, and each static attribute in the class corresponds to a key value in the file.
 * You can also easily understand that the configuration file is mapped to this class.
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */

public class Parameter {
	/**
	 * Transmission delay from user to base station
	 */
	public static int USER_TO_BS_DELAY;
	/**
	 * Transmission delay between base station and base station
	 */
	public static int BS_TO_BS_DELAY;
	/**
	 * Base station to MNO transmission delay
	 */
	public static int BS_TO_MNO_DELAY;
	/**
	 * MNO to the Internet delay
	 */
	public static int MNO_TO_CLOUD_DELAY;
	/**
	 * File path of state set
	 */
	public static String PATH = null;
	/**
	 * Power law parameters r
	 */
	public static float PowerLaw_R;
	/**
	 * Power law parameters c
	 */
	public static int PowerLaw_C;
	/**
	 * The default radius of the base station.
	 */
	public static int Radius;
	/**
	 * The probability that a user will generate a request at a time slice.
	 */
	public static float RequestProbability;
	/**
	 * The maximum number of time slices that the system supports by default.
	 */
	public static int TimeSlicesMaxNumber;
	/**
	 * The minimum value of the default base station's fluctuation range.
	 */
	public static float BSMinWaveInterval;
	/**
	 * The maximum value of the default base station's fluctuation range.
	 */
	public static float BSMaxWaveInterval;
	/**
	 * The minimum value of the default user's fluctuation range.
	 */
	public static float UserMinWaveInterval;
	/**
	 * The maximum value of the default user's fluctuation range.
	 */
	public static float UserMaxWaveInterval;
	/**
	 * 
	 */
	public static int ContentSizePerTimeSlice;

	
	public void setUSER_TO_BS_DELAY(String uSER_TO_BS_DELAY) {
		USER_TO_BS_DELAY = Integer.parseInt(uSER_TO_BS_DELAY);
	}
	public void setBS_TO_BS_DELAY(String bS_TO_BS_DELAY) {
		BS_TO_BS_DELAY = Integer.parseInt(bS_TO_BS_DELAY);
	}
	public void setBS_TO_MNO_DELAY(String bS_TO_MNO_DELAY) {
		BS_TO_MNO_DELAY = Integer.parseInt(bS_TO_MNO_DELAY);
	}
	public void setMNO_TO_CLOUD_DELAY(String mNO_TO_CLOUD_DELAY) {
		MNO_TO_CLOUD_DELAY = Integer.parseInt(mNO_TO_CLOUD_DELAY);
	}
	public void setPATH(String pATH) {
		PATH = pATH;
	}
	public void setRadius(String radius) {
		Radius = Integer.parseInt(radius);
	}
	public void setPowerLaw_R(String powerLaw_R) {
		PowerLaw_R = Float.parseFloat(powerLaw_R);
	}
	public void setPowerLaw_C(String powerLaw_C) {
		PowerLaw_C = Integer.parseInt(powerLaw_C);
	}
	
	public void setRequestProbability(String requestProbability) {
		RequestProbability = Float.parseFloat(requestProbability);
	}
	
	public static void setTimeSlicesMaxNumber(String timeSlicesMaxNumber) {
		TimeSlicesMaxNumber = Integer.parseInt(timeSlicesMaxNumber);
	}

	public void setBSMinWaveInterval(String bSMinWaveInterval) {
		BSMinWaveInterval = Float.parseFloat(bSMinWaveInterval);
	}

	public void setBSMaxWaveInterval(String bSMaxWaveInterval) {
		BSMaxWaveInterval = Float.parseFloat(bSMaxWaveInterval);
	}

	public void setUserMinWaveInterval(String userMinWaveInterval) {
		UserMinWaveInterval = Float.parseFloat(userMinWaveInterval);
	}
	
	public void setUserMaxWaveInterval(String userMaxWaveInterval) {
		UserMaxWaveInterval = Float.parseFloat(userMaxWaveInterval);
	}
	public static void setContentSizePerTimeSlice(String contentSizePerTimeSlice) {
		ContentSizePerTimeSlice = Integer.parseInt(contentSizePerTimeSlice);
	}	
	
	
}
