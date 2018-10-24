package cn.edu.tju.simulation.file;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.edu.tju.simulation.content.SingleContent;


/**
 * Unfinished
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class ContentWriter {
	
    public Boolean creatContentXML(){  
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Random r = new Random();
		try {
			db = dbf.newDocumentBuilder();
			 //生成一个Dom树  
	        Document document = db.newDocument();  
	        //去掉standalone="no"声明,说明只是一个简单的xml,没有特殊DTD(document type definition文档类型定义)规范  
	        document.setXmlStandalone(true);  
	        //创建Location根节点  
	        Element rootElement = document.createElement("containt"); 
	        List<SingleContent> fileSizeList = new ContentReader().read();
	        //创建CountryRegion节点  
	        for(int i = 0;i<fileSizeList.size();i++){
	            Element type = document.createElement("media");
	            type.setAttribute("id",String.valueOf(i)); 
	            
	            Element name = document.createElement("name"); 
	            name.setTextContent(String.valueOf(i));
	            
	            Element size = document.createElement("size");
	            size.setTextContent((String.valueOf((int)((fileSizeList.get(i).getSize())*(r.nextFloat()+0.1)))));
	            
	            Element popularity = document.createElement("popularity");
	            popularity.setTextContent((String.valueOf((int)((fileSizeList.get(i).getPopularity())))));
	            
	            type.appendChild(name); 
	            type.appendChild(size);
	            type.appendChild(popularity);
	            rootElement.appendChild(type);  
	            
	        }
	          
	        //将包含了子节点的rootElement添加到document中  
	        document.appendChild(rootElement);  
	        //实例化工厂类，工厂类不能使用new关键字实例化创建对象  
	        TransformerFactory transFactory = TransformerFactory.newInstance();
	        //创建transformer对象  
            Transformer transformer = transFactory.newTransformer();  
            //设置换行  
            transformer.setOutputProperty(OutputKeys.INDENT, "Yes");  
            //构造转换,参数都是抽象类，要用的却是更具体的一些类，这些的类的命名有一些规律的。  
            transformer.transform(new DOMSource(document), new StreamResult("data/Containt.xml"));
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} 

		return true;  
    }
    
    public void saveContentXML(Vector<Vector<String>>tableColumn ){		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			 //生成一个Dom树  
	        Document document = db.newDocument();  
	        //去掉standalone="no"声明,说明只是一个简单的xml,没有特殊DTD(document type definition文档类型定义)规范  
	        document.setXmlStandalone(true);  
	        //创建Location根节点  
	        Element rootElement = document.createElement("containt"); 
	        //创建CountryRegion节点  
			for(int i = 0 ;i<tableColumn.size();i++){
				Vector<String> vector = tableColumn.get(i);
	            Element type = document.createElement("media");
	            type.setAttribute("id",vector.get(0)); 
	            
	            Element name = document.createElement("name"); 
	            name.setTextContent(vector.get(0));
	            
	            Element size = document.createElement("size");
	            size.setTextContent(vector.get(1));
	            
	            Element popularity = document.createElement("popularity");
	            popularity.setTextContent(vector.get(2));
	            
	            type.appendChild(name); 
	            type.appendChild(size);
	            type.appendChild(popularity);
	            rootElement.appendChild(type); 
			}

	          
	        //将包含了子节点的rootElement添加到document中  
	        document.appendChild(rootElement);  
	        //实例化工厂类，工厂类不能使用new关键字实例化创建对象  
	        TransformerFactory transFactory = TransformerFactory.newInstance();
	        //创建transformer对象  
            Transformer transformer = transFactory.newTransformer();  
            //设置换行  
            transformer.setOutputProperty(OutputKeys.INDENT, "Yes");  
            //构造转换,参数都是抽象类，要用的却是更具体的一些类，这些的类的命名有一些规律的。  
            transformer.transform(new DOMSource(document), new StreamResult("data/Containt.xml"));  
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} 

    }
    
    public static void main(String[] args) {
		new ContentWriter().creatContentXML();
	}

}
