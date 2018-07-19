package cn.edu.tju.simulation.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.edu.tju.simulation.content.InitialSingleContent;

/**
 * Read XML file, initialize multimedia
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 * 
 */
public class ContentReader {
	/**
	 * Read content.xml
	 * @return
	 */
	public List<InitialSingleContent> read() {
		List<InitialSingleContent> mediaList = new ArrayList<InitialSingleContent>();
		InitialSingleContent tempMedia = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse("data/CONTAINT.xml");
			NodeList containt = document.getElementsByTagName("media");
			
			for (int i = 0; i < containt.getLength(); i++) {
				tempMedia = new InitialSingleContent();
				tempMedia.setName(document.getElementsByTagName("name").item(i).getFirstChild().getNodeValue());
				String size =  document.getElementsByTagName("size").item(i).getFirstChild().getNodeValue();
				if(size.equals("0")){
					tempMedia.setSize(Integer.parseInt("1"));
				}else{
					tempMedia.setSize(Integer.parseInt(size));
				}
				tempMedia.setPopularity(Integer.parseInt(document.getElementsByTagName("popularity").item(i).getFirstChild().getNodeValue()));

				mediaList.add(tempMedia);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
//		ContentService.sortByPopularity2(mediaList);
		return mediaList;
	}

}
