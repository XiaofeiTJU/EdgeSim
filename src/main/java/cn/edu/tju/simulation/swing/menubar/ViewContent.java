package cn.edu.tju.simulation.swing.menubar;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import cn.edu.tju.simulation.content.ContentService;
import cn.edu.tju.simulation.content.SingleContent;
import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.file.ContentWriter;
import cn.edu.tju.simulation.handler.Pretreatment;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class ViewContent extends JFrame{
	private static final long serialVersionUID = -2588537963980687689L;
	private JScrollPane jScrollPane;
	private JButton add;
	private JTable jTable;
	private JLabel sumOfSize;
	private DefaultTableModel jTableModel;
	private JButton reset;
	private JButton delete;
	private JButton save;
	private Vector<Vector<String>> tableColumn = new Vector<Vector<String>>();// Table元素向量
	private Vector<String> columnNames = new Vector<String>();// Table列名
	private List<SingleLocalHobby> mediaList;
	private ViewContent content;
	private JFrame rootFrame;
	private Controller controller;
	
	
	/**
	 * Log
	 */
	private static Logger logger = Logger.getLogger(ViewContent.class);
	
	public ViewContent(Container frame){
		rootFrame = (JFrame)frame;
		this.controller = Controller.getInstance();
        this.mediaList = controller.getOriginalContentList();
        initial();
        addListener();
        tableUpdate();
	}
	
	public void tableUpdate() {
		tableColumn.removeAll(tableColumn);
		
		for (int i = 0; i < mediaList.size(); i++) {
			Vector<String> tableC = new Vector<String>();
			tableC.add(mediaList.get(i).getName());
			tableC.add(""+mediaList.get(i).getSize());
			tableC.add(""+ mediaList.get(i).getSingleContent().getPopularity());
			tableC.add(""+mediaList.get(i).getTimeSlotNumber());
			tableColumn.add(tableC);
		}
		int row = jTableModel.getRowCount();
		jTableModel.fireTableRowsInserted(0, row + 1);
	}
	
	public void initial(){
		this.setTitle("Content");
        this.setLocation(300,200);
		this.setSize(600, 300);
        this.setVisible(true);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        try {
			{
				jScrollPane = new JScrollPane();
				getContentPane().add(jScrollPane, BorderLayout.CENTER);
				jScrollPane.setBounds(130, 27, 433, 199);
				{
					columnNames.add("Name");
					columnNames.add("Size(KB)");
					columnNames.add("Popularity");
					columnNames.add("Time Slot Number");
					jTableModel = new DefaultTableModel(tableColumn,columnNames);
					jTable = new JTable();
					jScrollPane.setViewportView(jTable);
					jTable.setModel(jTableModel);
				}
			}
			
			{
				add = new JButton();
				getContentPane().add(add);
				add.setText("ADD");
				add.setBounds(32, 45, 90, 30);
			}
			{
				delete = new JButton();
				getContentPane().add(delete);
				delete.setText("DELETE");
				delete.setBounds(32, 95, 90, 30);
			}
			{
				reset = new JButton();
				getContentPane().add(reset);
				reset.setText("RESET");
				reset.setBounds(32, 145, 90, 30);
			}
			{
				save = new JButton();
				getContentPane().add(save);
				save.setText("SAVE");
				save.setBounds(32, 195, 90, 30);
			}
			{
				long sum = 0 ;
				for (SingleLocalHobby singleContent : mediaList) {
					sum += singleContent.getSize();
				}
				sumOfSize = new JLabel("The sum of the file sizes of all content : " + ContentService.unitConversion(sum)+" GB");
				getContentPane().add(sumOfSize);
				sumOfSize.setBounds(155, 210, 380, 80);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class MyActionListener implements ActionListener, WindowListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add) {
				AddContent addContent = new AddContent(content,mediaList);
				addContent.setAddContent(addContent);
			} else if (e.getSource() == delete) {
				deleteButtonClicked();
				tableUpdate();
			} else if (e.getSource() == reset) {
				ContentService.initialPopularity(mediaList);
				tableUpdate();
			} else if(e.getSource() == save) {
				saveButtonClicked();
			}
		}
		public void windowClosing(WindowEvent e) {
			
			Iterator<Vector<String>> it = tableColumn.iterator();
			mediaList.clear();
			while(it.hasNext()){
				Vector<String> vector = it.next();
				mediaList.add(new SingleLocalHobby( new SingleContent(vector.get(0), Integer.parseInt(vector.get(2)),Integer.parseInt(vector.get(1)))));
			}
			if(controller.hasUsers()){
				Pretreatment.process();
			}
		}
		public void windowOpened(WindowEvent e) {rootFrame.setEnabled(false);}
		public void windowClosed(WindowEvent e) {rootFrame.setEnabled(true);content.dispose();}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
	}
	
	public void deleteButtonClicked(){
		if(jTable.getSelectedRow() != -1){
			//get the selected line number
			int select = jTable.getSelectedRow();
			logger.info("Deleted"+mediaList.get(select).getName()+",the current list length is ："+mediaList.size());
			//delete selected line
			mediaList.remove(select);
			//reduce a row
			int row = jTable.getRowCount();
			if (row > 0) {
				jTableModel.fireTableRowsDeleted(0, row - 1);
			} else {
				jTableModel.fireTableRowsDeleted(0, 0);
			}	
		}
	}
	
	public void saveButtonClicked(){
		new ContentWriter().saveContentXML(tableColumn);
		JOptionPane.showMessageDialog(null, "The storage is complete！！", "Prompt",JOptionPane.INFORMATION_MESSAGE);
	}

	public void addListener(){
		MyActionListener myActionListener = new MyActionListener();
		this.addWindowListener(myActionListener);
		add.addActionListener(myActionListener);
		delete.addActionListener(myActionListener);
		reset.addActionListener(myActionListener);
		save.addActionListener(myActionListener);
	}
	
	public ViewContent getContent() {
		return content;
	}

	public void setContent(ViewContent content) {
		this.content = content;
	}
}
