package cn.edu.tju.simulation.logger;

import java.io.File;
import java.io.FileFilter;

import org.apache.log4j.helpers.LogLog;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class LogFileFilter implements FileFilter {
	private String logName;
	
	public LogFileFilter(String logName){
		this.logName = logName;
	}
	
	public boolean accept(File file) {
		if(logName == null || file.isDirectory()){
			return false;
		}else{
			LogLog.debug(file.getName());
			return file.getName().startsWith(logName);
		}
	}
}
