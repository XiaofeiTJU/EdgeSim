package cn.edu.tju.simulation.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Reads SIMULATION.xml£¬and assign values to static attributes in the Pramater class.
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class ParameterFileReader{
	
	public Boolean read(){
		Properties properties = new Properties();
		InputStream r = null ;
		try {
			r= this.getClass().getClassLoader().getResourceAsStream("SIMULATION.properties");
			properties.load(r);
			Class<?> clazz = Class.forName("cn.edu.tju.simulation.file.Parameter");
			Parameter parameter = (Parameter) clazz.newInstance();
			Field [] attributeArray = clazz.getDeclaredFields();
			Method [] methodArray = clazz.getMethods();
			for(Field attribute : attributeArray) {
				String Parameter = properties.getProperty(attribute.getName());
				for(Method method : methodArray){
					if(method.getName().toLowerCase().equals("set"+attribute.getName().toLowerCase())){
						method.invoke(parameter, Parameter);
					}
				}
			}
			return true;
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		}finally{
			try {
				r.close();
				properties.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

//	public static void main(String args[]){
//		new ParameterFileReader().read();
//	}
}
