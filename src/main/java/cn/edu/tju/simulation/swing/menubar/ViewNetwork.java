package cn.edu.tju.simulation.swing.menubar;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.wirelessnetwork.BaseStation;
import cn.edu.tju.simulation.wirelessnetwork.SameTypeWirelessNetwork;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class ViewNetwork extends JFrame{
	private static final long serialVersionUID = 3277823728470892166L;
	private JScrollPane jScrollPane;
	private JButton add;
	private JTable jTable;
	private DefaultTableModel jTableModel;
	private JButton delete;
	private Vector<Vector<String>> tableColumn = new Vector<Vector<String>>();// Table元素向量
	private Vector<String> columnNames = new Vector<String>();// Table列名
	private JFrame rootFrame;
	private ViewNetwork viewNetwork;
	private Controller controller;
	private SameTypeWirelessNetwork BSs;
	
	/**
	 * Log
	 */
	private static Logger logger = Logger.getLogger(ViewNetwork.class);
	
	public ViewNetwork(Container frame,Controller controller){
		rootFrame = (JFrame)frame;
		this.controller = controller;
		this.BSs = controller.getWirelessNetworkGroup().BS;
        initial();
        addListener();
        tableUpdate();
	}
	
	public void tableUpdate() {
		tableColumn.removeAll(tableColumn);
		
		for (int i = 0; i < BSs.getAmount(); i++) {
			Vector<String> tableC = new Vector<String>();
			tableC.add(""+BSs.getNetwork(i).getNumber());
			tableC.add(""+BSs.getNetwork(i).getType());
			tableC.add("("+(int)BSs.getNetwork(i).getLocation().getX()+","+(int)BSs.getNetwork(i).getLocation().getY()+")");
			tableC.add(""+BSs.getNetwork(i).getRadius());
			tableC.add(""+ BSs.getNetwork(i).getCacheSize()/1024/1024/1024);
			tableColumn.add(tableC);
		}
		int row = jTableModel.getRowCount();
		jTableModel.fireTableRowsInserted(0, row + 1);
	}
	
	public void initial(){
		this.setTitle("WirelessNetwork");
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
					columnNames.add("Number");
					columnNames.add("Type");
					columnNames.add("Coordinate");
					columnNames.add("Radius");
					columnNames.add("Cache Size(GB)");
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
				add.setBounds(32, 54, 90, 30);
			}
			{
				delete = new JButton();
				getContentPane().add(delete);
				delete.setText("DELETE");
				delete.setBounds(32, 117, 90, 30);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class MyActionListener implements ActionListener, WindowListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add) {
				AddNetwork addNetwork = new AddNetwork(viewNetwork);
				addNetwork.setAddNetwork(addNetwork);
//				AddContent addContent = new AddContent(content,mediaList);
//				addContent.setAddContent(addContent);
			} else if (e.getSource() == delete) {
				deleteButtonClicked();
				tableUpdate();
			} 
		}
		
		//删除需要加重置关系
		public void windowClosing(WindowEvent e) {
			Iterator<Vector<String>> it = tableColumn.iterator();
			BSs.clear();
			while(it.hasNext()){
				Vector<String> vector = it.next();
				String cooridinate = vector.get(2);
				String [] mCooridinate = cooridinate.substring(cooridinate.indexOf("(")+1,cooridinate.indexOf(")")).split(",");
				double x = Double.parseDouble(mCooridinate[0]);
				double y = Double.parseDouble(mCooridinate[1]);
				BSs.addWirelessNetwork(new BaseStation(Integer.parseInt(vector.get(0)),new Point2D.Double(x,y),true,Long.parseLong(vector.get(4))*1024*1024*1024,Integer.parseInt(vector.get(3))));
			}
			if(controller.hasUsers()){
				controller.getUsers().getSimpleUsers().clear();
			}
		}
		public void windowOpened(WindowEvent e) {rootFrame.setEnabled(false);}
		public void windowClosed(WindowEvent e) {rootFrame.setEnabled(true);viewNetwork.dispose();}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
	}
	
	public void deleteButtonClicked(){
		if(jTable.getSelectedRow() != -1){
			//get the selected line number
			int select = jTable.getSelectedRow();
			logger.info("Deleted network "+BSs.getNetwork(select).getNumber()+",the current list length is ："+BSs.getAmount());
			//delete selected line
			BSs.removeNetwork(select);
			//reduce a row
			int row = jTable.getRowCount();
			if (row > 0) {
				jTableModel.fireTableRowsDeleted(0, row - 1);
			} else {
				jTableModel.fireTableRowsDeleted(0, 0);
			}	
		}
	}
	
	public void addListener(){
		MyActionListener myActionListener = new MyActionListener();
		this.addWindowListener(myActionListener);
		add.addActionListener(myActionListener);
		delete.addActionListener(myActionListener);
	}
	
	public JScrollPane getjScrollPane() {
		return jScrollPane;
	}

	public void setjScrollPane(JScrollPane jScrollPane) {
		this.jScrollPane = jScrollPane;
	}

	public JButton getAdd() {
		return add;
	}

	public void setAdd(JButton add) {
		this.add = add;
	}

	public JTable getjTable() {
		return jTable;
	}

	public void setjTable(JTable jTable) {
		this.jTable = jTable;
	}

	public DefaultTableModel getjTableModel() {
		return jTableModel;
	}

	public void setjTableModel(DefaultTableModel jTableModel) {
		this.jTableModel = jTableModel;
	}

	public JButton getDelete() {
		return delete;
	}

	public void setDelete(JButton delete) {
		this.delete = delete;
	}

	public Vector<Vector<String>> getTableColumn() {
		return tableColumn;
	}

	public void setTableColumn(Vector<Vector<String>> tableColumn) {
		this.tableColumn = tableColumn;
	}

	public Vector<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Vector<String> columnNames) {
		this.columnNames = columnNames;
	}

	public JFrame getRootFrame() {
		return rootFrame;
	}

	public void setRootFrame(JFrame rootFrame) {
		this.rootFrame = rootFrame;
	}

	public ViewNetwork getViewNetwork() {
		return viewNetwork;
	}

	public void setViewNetwork(ViewNetwork viewNetwork) {
		this.viewNetwork = viewNetwork;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	

}
