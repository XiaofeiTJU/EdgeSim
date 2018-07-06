package cn.edu.tju.simulation.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.tool.StringHandler;

/**
 * Open the case file. The case file is used to record the configuration of the network system. 
 * For example, you designed the network topology and configured the radius, cache size, and other information for each base station. When you do the next simulation, you want to call this case directly, and you can choose to save it as a case file.
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 * 
 */
public class CaseFileReader {
	
	public Boolean read(Controller controller,File file){
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		controller.getWirelessNetworkGroup().BS.clear();
		if(file.exists()){
			try {
				fileReader = new FileReader(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			bufferedReader = new BufferedReader(fileReader);
			String line;
			Boolean checkNetwork = false;
			Boolean checkNetworkWave = false;
			try {
				while ((line = bufferedReader.readLine()) != null) {
					if(line.equals("#WirelessNetwork")){
						checkNetwork = true;
						continue;
					}else if(!line.equals("#WirelessNetwork") && !line.equals("#WirelessNetworkWave") && checkNetwork ==true){
						controller.getWirelessNetworkGroup().BS.addWirelessNetwork(StringHandler.readBS(line));
						continue;
					}else if(line.equals("#WirelessNetworkWave")){
						checkNetworkWave = true;
						checkNetwork = false;
					}else if(!line.equals("#WirelessNetwork") && !line.equals("#WirelessNetworkWave") && checkNetworkWave ==true){
						String Wave []= line.split(" ");
						Parameter.BSMinWaveInterval = Float.parseFloat(Wave[0]);
						Parameter.BSMaxWaveInterval = Float.parseFloat(Wave[1]);
					}
				}
				if(checkNetwork == false && checkNetworkWave == true){
					return true;
				}else{
					return false;	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
					if (fileReader != null) {
						fileReader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;		
	}
	
}
