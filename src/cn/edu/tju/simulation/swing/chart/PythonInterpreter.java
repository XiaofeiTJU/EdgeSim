package cn.edu.tju.simulation.swing.chart;

import java.io.IOException;

public class PythonInterpreter {
	private static final String PY_URL = "src\\cn\\edu\\tju\\simulation\\swing\\chart\\Linechart.py";
	public static void interpreter() throws IOException {
		try {
			Runtime.getRuntime().exec("cmd /c python " + PY_URL);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
