package cn.edu.tju.simulation.logger;

import org.apache.log4j.Logger;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class Log{  
	  
	private static Logger logger = Logger.getLogger(Log.class);
  
    public static Logger getLogger(){  
    
        return logger;  
    }  
}  