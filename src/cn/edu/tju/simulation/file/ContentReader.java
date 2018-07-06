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
			NodeList containt = document.getChildNodes();
			
			for (int i = 0; i < containt.getLength(); i++) {
				Node media = containt.item(i);
				NodeList mediaInfo = media.getChildNodes();

				for (int j = 0; j <4000; j++) {
					Node node = mediaInfo.item(j);
					if (node instanceof Element) {
						NodeList mediaMeta = node.getChildNodes();
						tempMedia = new InitialSingleContent();
						for (int k = 0; k < mediaMeta.getLength(); k++) {
							if (mediaMeta.item(k).getNodeName().equals("name")) {
								tempMedia.setName(mediaMeta.item(k).getTextContent());
							}if(mediaMeta.item(k).getNodeName().equals("size")){
								if(mediaMeta.item(k).getTextContent().equals("0")){
									tempMedia.setSize(Integer.parseInt("1"));
								}else{
									tempMedia.setSize(Integer.parseInt(mediaMeta.item(k).getTextContent()));
								}
//								tempMedia.setSize((r.nextInt(40)+1)*1024);
							}if(mediaMeta.item(k).getNodeName().equals("popularity")){
								tempMedia.setPopularity(Integer.parseInt(mediaMeta.item(k).getTextContent()));
							}
						}
						mediaList.add(tempMedia);
					}
				}
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
