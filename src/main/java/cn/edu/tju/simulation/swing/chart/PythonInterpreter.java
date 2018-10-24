package cn.edu.tju.simulation.swing.chart;

import java.io.IOException;


public class PythonInterpreter {
	private static String path = PythonInterpreter.class.getClassLoader().getResource("").getPath().substring(1,PythonInterpreter.class.getClassLoader().getResource("").getPath().indexOf("/out"));
	private static final String PY_HITRATRE_URL = path + "/src/main/java/cn/edu/tju/simulation/swing/chart/HitRate.py";
	private static final String PY_LATENCY_URL = path + "/src/main/java/cn/edu/tju/simulation/swing/chart/Latency.py";
	private static final String PY_TRAFFIC_URL = path + "/src/main/java/cn/edu/tju/simulation/swing/chart/Traffic.py";

	public static void interpreter(){
		try {
			System.out.println(PythonInterpreter.class.getClassLoader().getResource("").getPath());

			Runtime.getRuntime().exec("cmd /k python "+ PY_HITRATRE_URL);
			Runtime.getRuntime().exec("cmd /k python "+ PY_LATENCY_URL);
			Runtime.getRuntime().exec("cmd /k python "+ PY_TRAFFIC_URL);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
