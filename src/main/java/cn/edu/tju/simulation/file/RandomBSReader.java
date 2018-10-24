package cn.edu.tju.simulation.file;

import java.awt.geom.Point2D;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.swing.operator.Signal;
import cn.edu.tju.simulation.wirelessnetwork.BaseStation;

/**
 * Read the pre-saved network system. The pre-saved network system is stored in RANDOMBS.xml, 
 * which contains a variety of different topologies, each reading is random.
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 */
public class RandomBSReader implements XMLReader{
	public void parserXML() {
		try {
			Controller controller = Controller.getInstance();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse("src/main/resources/data/RANDOMBS.xml");
			NodeList randombss = document.getChildNodes();

			for (int i = 0; i < randombss.getLength(); i++) {
				Node randombs = randombss.item(i);
				Node group = null;
				if (randombs instanceof Element) {
					NodeList groups = randombs.getChildNodes();
					while (true) {
						int random = (int) (Math.random() * 10);
						group = groups.item(random);
						if(group instanceof Element && random != Signal.BSFileNumber && random < groups.getLength()){
							group = groups.item(random);
							Signal.BSFileNumber = random ;
							break;
						}
					}

				NodeList BSs = group.getChildNodes();

				for (int k = 0; k < BSs.getLength(); k++) {
					Node BSMeta = BSs.item(k);
					NodeList BSInfo = BSMeta.getChildNodes();

					int location_x = 0, location_y = 0, CacheSize = 0, Radius = 0;
					Boolean Cache = null;
					if (BSMeta instanceof Element) {
						for (int n = 0; n < BSInfo.getLength(); n++) {
							Node item = BSInfo.item(n);
							if (item instanceof Element) {
								if (item.getNodeName().equals("location_x")) {
									location_x = Integer.parseInt(item.getTextContent());
								} else if (item.getNodeName().equals("location_y")) {
									location_y = Integer.parseInt(item.getTextContent());
								} else if (item.getNodeName().equals("Cache")) {
									Cache = Boolean.parseBoolean(item.getTextContent());
								} else if (item.getNodeName().equals("CacheSize")) {
									CacheSize = Integer.parseInt(item.getTextContent());
								} else if (item.getNodeName().equals("Radius")) {
									Radius = Integer.parseInt(item.getTextContent());
								}

							}
						}
						controller.getWirelessNetworkGroup().BS.addWirelessNetwork(new BaseStation(controller.getWirelessNetworkGroup().getBSAmount(),new Point2D.Double(location_x, location_y),Cache, CacheSize, Radius));
					}
				}
			}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

