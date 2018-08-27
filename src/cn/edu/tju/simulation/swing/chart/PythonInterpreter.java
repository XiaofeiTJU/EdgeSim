package cn.edu.tju.simulation.swing.chart;

import java.io.IOException;

public class PythonInterpreter {
	private static final String PY_HITRATRE_URL = "src\\cn\\edu\\tju\\simulation\\swing\\chart\\HitRate.py";
	private static final String PY_LATENCY_URL = "src\\cn\\edu\\tju\\simulation\\swing\\chart\\Latency.py";
	private static final String PY_TRAFFIC_URL = "src\\cn\\edu\\tju\\simulation\\swing\\chart\\Traffic.py";

	public static void interpreter() throws IOException {
		try {			
			Runtime.getRuntime().exec("cmd /c python "+ PY_HITRATRE_URL);
			Runtime.getRuntime().exec("cmd /c python "+ PY_LATENCY_URL);
			Runtime.getRuntime().exec("cmd /c python "+ PY_TRAFFIC_URL);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
