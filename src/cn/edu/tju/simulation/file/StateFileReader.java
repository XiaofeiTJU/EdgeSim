package cn.edu.tju.simulation.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.edu.tju.simulation.content.SingleContent;
import cn.edu.tju.simulation.tool.StringHandler;

/**
 * Read the contents of the file, generate a collection of states
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 * 
 */
public class StateFileReader {
	/**
	 *Traverse the file to see how many users there are.
	 * @return
	 */
	public List<Integer> traverseFolderToGetMax() {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		List<Integer> ids = new ArrayList<Integer>();
		File filepath = new File(this.getFilePath());
		if (filepath.exists()) {
			File[] files = filepath.listFiles();
			if (files.length == 0) {
				System.out.println("Status set is empty");
			} else {
				for (File file : files) {
					if (file.isDirectory()) {
						System.out.println("Directory contains the folder！");
					} else {
						try {
							fileReader = new FileReader(file);
							bufferedReader = new BufferedReader(fileReader);
							String line;
							while ((line = bufferedReader.readLine()) != null) {
								String[] state = StringHandler
										.array(line);
								int temp = Integer.parseInt(state[0]);
								System.out.println(state[0]);
								if (!ids.contains(temp)) {
									ids.add(temp);
								}
							}
							return ids;
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
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
				}
			}
		} else {
			System.out.println("File path error！");
			return null;
		}
		return null;
	}

//	/**
//	 * Traversing the file a second time generates each line in the file as a state
//	 * 
//	 * @return
//	 */
//	public List<State> 1traverseFolderToGetStates(SameTypeMobilityModel sameMobilityModel, List<Media> list) {
//		FileReader fileReader = null;
//		BufferedReader bufferedReader = null;
//		File filepath = new File(this.getFilePath());
//		if (filepath.exists()) {
//			File[] files = filepath.listFiles();
//			if (files.length == 0) {
//				System.out.println("Status set is empty");
//			} else {
//				for (File file : files) {
//					if (file.isDirectory()) {
//						System.out.println("Directory contains the folder！");
//					} else {
//						try {
//							fileReader = new FileReader(file);
//							bufferedReader = new BufferedReader(fileReader);
//							List<State> states = new ArrayList<State>();
//							String line;
//							while ((line = bufferedReader.readLine()) != null) { // 一次读一行，遍历所有内容
//								String[] state = StringHandler
//										.array(line);
//								states = StringHandler.agregate(states,state, sameMobilityModel, list);
//							}
//							states = StringHandler.sort(states);
//
//							// for(int i=0;i<states.size();i++){
//							// System.out.println(states.get(i).getId_wireless());
//							// }
//							//
//							return states;
//						} catch (FileNotFoundException e) {
//							e.printStackTrace();
//						} catch (IOException e) {
//							e.printStackTrace();
//						} finally {
//							try {
//								if (bufferedReader != null) {
//									bufferedReader.close();
//								}
//								if (fileReader != null) {
//									fileReader.close();
//								}
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//
//					}
//				}
//			}
//		} else {
//			System.out.println("File path error！");
//			return null;
//		}
//		return null;
//	}

	/**
	 * Get the file path from SIMULATION.properties
	 * 
	 * @return
	 */
	public String getFilePath() {
		Properties properties = new Properties();
		Reader reader = null;
		try {
			reader = new FileReader("src\\cn\\edu\\tju\\simulation\\parameter\\SIMULATION.properties");
			properties.load(reader);
			return properties.getProperty("PATH");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				properties.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "D:\\EdgeCahing";
	}

	/**
	 * Analyze the CONTAINT.xml
	 * 
	 * @return
	 */
	public static List<SingleContent> XMLDocunmentContent() {
		List<SingleContent> mediaList = new ArrayList<SingleContent>();
		SingleContent mediaTemp = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse("data/CONTAINT.xml");
			NodeList containt = document.getChildNodes();

			for (int i = 0; i < containt.getLength(); i++) {
				Node media = containt.item(i);
				NodeList mediaInfo = media.getChildNodes();

				for (int j = 0; j < mediaInfo.getLength(); j++) {
					Node node = mediaInfo.item(j);
					if (node instanceof Element) {
						NodeList mediaMeta = node.getChildNodes();
						mediaTemp = new SingleContent();
						for (int k = 0; k < mediaMeta.getLength(); k++) {
							if (mediaMeta.item(k).getNodeName().equals("name")) {
								mediaTemp.setName(mediaMeta.item(k)
										.getTextContent());
							} else if (mediaMeta.item(k).getNodeName()
									.equals("amount")) {
								mediaTemp.setPopularity(Integer.parseInt(mediaMeta
										.item(k).getTextContent()));
							} else if (mediaMeta.item(k).getNodeName()
									.equals("size")) {
								mediaTemp.setSize(Integer.parseInt(mediaMeta
										.item(k).getTextContent()));
							}
						}
						mediaList.add(mediaTemp);
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
		return mediaList;

	}
}
